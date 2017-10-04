package cl.mecolab.memeticame.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Andres Matte.
 */
public class Chat {
    public final Integer mId;
    public final String mTitle;
    public final Boolean mGroup;
    public final ArrayList<User> mUsers;
    public final ArrayList<Message> mMessages;


    private Chat(Integer id, String title, Boolean group, ArrayList<User> users, ArrayList<Message> messages) {
        mId = id;
        mTitle = title;
        mGroup = group;
        mUsers = users;
        mMessages = messages;
    }

    public static class Builder {
        private Integer mId;
        private String mTitle;
        private Boolean mGroup;
        private ArrayList<User> mUsers;
        private ArrayList<Message> mMessages;

        public Builder id(Integer id) {
            mId = id;
            return this;
        }

        public Builder title(String title) {
            mTitle = title;
            return this;
        }
        public Builder group(Boolean group) {
            mGroup = group;
            return this;
        }
        public Builder users(JSONArray jsonUsers) {
            mUsers = new ArrayList<>();
            for (int i = 0; i < jsonUsers.length(); i++) {
                try {
                    JSONObject jsonUser = jsonUsers.getJSONObject(i);
                    User user = new User.Builder()
                            .id(jsonUser.getInt("id"))
                            .name(jsonUser.getString("name"))
                            .phoneNumber(jsonUser.getString("phone_number"))
                            .build();
                    mUsers.add(user);
                } catch (JSONException e) { }
            }
            return this;
        }
        public Builder messages(JSONArray jsonMessages) {
            mMessages = new ArrayList<>();
            for (int i = 0; i < jsonMessages.length(); i++) {
                try {
                    JSONObject jsonMessage = jsonMessages.getJSONObject(i);
                    Message message = new Message.Builder()
                            .id(jsonMessage.getInt("id"))
                            .sender_phone(jsonMessage.getString("sender_phone"))
                            .content(jsonMessage.getString("content"))
                            .chat_id(jsonMessage.getInt("chat_id"))
//                            .attachment_link(jsonMessage.getJSONObject("attachment_link"))
                            .build();
                    mMessages.add(message);
                } catch (JSONException e) { }
            }
            return this;
        }

        public Chat build() { return new Chat(mId, mTitle, mGroup, mUsers, mMessages); }
    }
}