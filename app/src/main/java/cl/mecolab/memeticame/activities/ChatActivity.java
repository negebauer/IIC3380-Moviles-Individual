package cl.mecolab.memeticame.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import cl.mecolab.memeticame.R;
import cl.mecolab.memeticame.fragments.ContactsFragment;
import cl.mecolab.memeticame.fragments.MessagesFragment;
import cl.mecolab.memeticame.models.User;

public class ChatActivity extends AppCompatActivity {
    private MessagesFragment messagesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Bundle data = getIntent().getExtras();
        User user= (User) data.getParcelable("user");

        // Put MessagesFragment in main_container.
        // If fragment is not added, add it.
        if (getSupportFragmentManager().findFragmentByTag(MessagesFragment.TAG) == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction = transaction.add(R.id.chat_container,
                    new MessagesFragment(user), MessagesFragment.TAG);
            transaction.commit();
        }
    }
}
