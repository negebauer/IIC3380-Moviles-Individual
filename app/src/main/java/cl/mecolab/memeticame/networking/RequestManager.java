package cl.mecolab.memeticame.networking;

import android.content.Context;
import android.os.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cl.mecolab.memeticame.models.Chat;
import cl.mecolab.memeticame.models.Message;
import cl.mecolab.memeticame.models.User;

/**
 * Created by aamatte on 07-Sep-17.
 * Idea from here: http://slides.com/dmytrodanylyk/volley/fullscreen#/16
 */

public class RequestManager {
    private static String TOKEN = "HruwrszQbFqeiHLfeXqe3g";

    private static RequestManager sInstance;
    private RequestQueue mRequestQueue;

    public interface OnGetRegisteredUsers {
        void success(ArrayList<User> contacts);
        void error(String message);
    }

    public interface OnGetChat{
        void success(Chat chat);
        void error(String message);
    }

    private RequestManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized RequestManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RequestManager(context);
        }
        return sInstance;
    }

    public static synchronized RequestManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException
                    (RequestManager.class.getSimpleName() +
                            " is not initialized, call getInstance(..) method first.");
        }
        return sInstance;
    }

    public void getRegisteredUsers(ArrayList<User> localContacts, final OnGetRegisteredUsers listener) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("Authorization", "Token token=" + TOKEN);

        HashMap<String, JSONObject> body = new HashMap<>();
        HashMap<String, String> phone_numbers = new HashMap<>();

        for  (int i = 0; i < localContacts.size(); i++) {
            phone_numbers.put(String.valueOf(i), localContacts.get(i).mPhoneNumber);
        }

        body.put("phone_numbers", new JSONObject(phone_numbers));
        JSONObject jsonBody = new JSONObject(body);

        // This may seem a bit long for a request, but this can be wrapped in another class to
        // make it shorter!
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                "http://mcctrack3.ing.puc.cl/users",
                jsonBody,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<User> contacts = new ArrayList<>();
                        // Iterate over the users in the response and add them to the array list.
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonUser = response.getJSONObject(i);
                                contacts.add(
                                        new User.Builder()
                                                .id(jsonUser.getInt("id"))
                                                .name(jsonUser.getString("name"))
                                                .phoneNumber(jsonUser.getString("phone_number"))
                                                .build()
                                );
                            } catch (JSONException e) { }
                        }
                        listener.success(contacts);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        // The request is a bit slow, so the timeout time will be 5 secs
        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the queue to execute it
        mRequestQueue.add(request);
    }

    public void getChat(final User user, final OnGetChat listener) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("Authorization", "Token token=" + TOKEN);

        HashMap<String, JSONObject> body = new HashMap<>();
        HashMap<String, String> phone_numbers = new HashMap<>();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://mcctrack3.ing.puc.cl/chats",
                "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Message> msgs = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                    JSONObject jChat = response.getJSONObject(i);
                                    Chat chat = new Chat.Builder()
                                            .id(jChat.getInt("id"))
                                            .title(jChat.getString("title"))
                                            .group(jChat.getBoolean("group"))
                                            .users(jChat.getJSONArray("users"))
                                            .messages(jChat.getJSONArray("messages"))
                                            .build();

                                    if (!chat.mGroup && (chat.mUsers.get(0).mId == user.mId || chat.mUsers.get(1).mId == user.mId)) {
                                        listener.success(chat);
                                        return;
                                    }
                                } catch (JSONException e) { }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        // The request is a bit slow, so the timeout time will be 5 secs
        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the queue to execute it
        mRequestQueue.add(request);
    }
}
