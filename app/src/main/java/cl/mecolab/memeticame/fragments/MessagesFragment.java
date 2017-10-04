package cl.mecolab.memeticame.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.mecolab.memeticame.R;
import cl.mecolab.memeticame.models.Chat;
import cl.mecolab.memeticame.models.Message;
import cl.mecolab.memeticame.models.User;
import cl.mecolab.memeticame.networking.RequestManager;
import cl.mecolab.memeticame.utils.ContactsUtils;
import cl.mecolab.memeticame.views.ContactsAdapter;
import cl.mecolab.memeticame.views.MessagesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    Handler h = new Handler();
    int delay = 10000; //15 seconds
    Runnable runnable;

    public static final String TAG = "messages_fragment";
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 101;

    private User mUser;
    private Chat mChat;
    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;
    private ListView mMessagesListView;

    public MessagesFragment(User user) {
        mUser = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_messages, container, false);

        mMessagesListView = (ListView) view.findViewById(R.id.messages_list_view);
        final EditText editText = (EditText) view.findViewById(R.id.message_send);
        final Button button = (Button) view.findViewById(R.id.button_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mChat == null) { return; }
                String message = editText.getText().toString();
                editText.setText("");
                RequestManager.getInstance().sendMessage(message, mChat);
                getMessages();
            }
        });

        getMessages();
        setHasOptionsMenu(true);
        startRunnable();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getMessages();
        startRunnable();
    }

    @Override
    public void onPause() {
        super.onPause();
        h.removeCallbacks(runnable);
    }

    private void startRunnable() {
        h.postDelayed(new Runnable() {
            public void run() {
                getMessages();
                runnable=this;
                h.postDelayed(runnable, delay);
            }
        }, delay);
    }

    /**
     * Checks if the app has permission to read phone contacts.
     * Only for SDK > 23.
     */
    private boolean hasReadContactsPermission() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private void getMessages() {
        syncWithServer();
    }

    public void syncWithServer() {
        RequestManager.getInstance().getChat(mUser, new RequestManager.OnGetChat() {
            @Override
            public void success(Chat chat) {
                if (chat.mMessages.size() == 0) {
                    Toast.makeText(getContext(), "No messages yet", Toast.LENGTH_SHORT).show();
                }
                mChat = chat;
                mMessages = chat.mMessages;
                mAdapter = new MessagesAdapter(getContext(), R.layout.messages_list_item, mMessages, mUser);
                mMessagesListView.setAdapter(mAdapter);
            }

            @Override
            public void error(String message) {
                Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.messages_fragment_menu, menu);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
