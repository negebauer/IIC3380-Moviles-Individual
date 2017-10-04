package cl.mecolab.memeticame.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cl.mecolab.memeticame.fragments.ContactsFragment;
import cl.mecolab.memeticame.fragments.MessagesFragment;
import cl.mecolab.memeticame.models.User;
import cl.mecolab.memeticame.R;

public class MainActivity extends AppCompatActivity implements
        ContactsFragment.OnContactSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Put ContactsFragment in main_container.
        // If fragment is not added, add it.
        if (getSupportFragmentManager().findFragmentByTag(ContactsFragment.TAG) == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction = transaction.add(R.id.main_container,
                    new ContactsFragment(), ContactsFragment.TAG);
            transaction.commit();
        }

        System.out.print("ASDADS");
        System.out.print("ASDADS");
        System.out.print("ASDADS");
        System.out.print("ASDADS");
        System.out.print("ASDADS");
        System.out.print("ASDADS");
    }

    /**
     * Called when contact is selected on ContactsFragment.
     * @param user Contact selected.
     */
    @Override
    public void onContactSelected(User user) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, new MessagesFragment(user), MessagesFragment.TAG)
                .hide(getSupportFragmentManager().findFragmentByTag(ContactsFragment.TAG))
                .commit();
    }
}
