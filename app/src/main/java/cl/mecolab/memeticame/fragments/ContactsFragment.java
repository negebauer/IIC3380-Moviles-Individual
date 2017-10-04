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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.mecolab.memeticame.R;
import cl.mecolab.memeticame.models.User;
import cl.mecolab.memeticame.networking.RequestManager;
import cl.mecolab.memeticame.utils.ContactsUtils;
import cl.mecolab.memeticame.views.ContactsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    Handler h = new Handler();
    int delay = 10000; //15 seconds
    Runnable runnable;

    public static final String TAG = "contacts_fragment";
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 101;

    private ArrayList<User> mContacts;
    private OnContactSelected mListener;
    private ContactsAdapter mAdapter;
    private ListView mContactsListView;

    public interface OnContactSelected {
        void onContactSelected(User user);
    }

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        mContactsListView = (ListView) view.findViewById(R.id.contacts_list_view);
        mContactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onContactSelected(mContacts.get(position));
                }
            }
        });

        getContacts();
        setHasOptionsMenu(true);
        startRunnable();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getContacts();
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
                getContacts();
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

    private void getContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasReadContactsPermission()) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            // After this point you wait for callback in
            // onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            ContactsUtils.getContacts(getContext(), new ContactsUtils.ContactsProviderListener() {
                @Override
                public void contactsLoaded(ArrayList<User> contacts) {
                    syncWithServer(contacts);
                }
            });
        }
    }

    public void syncWithServer(ArrayList<User> contacts) {
        RequestManager.getInstance().getRegisteredUsers(contacts, new RequestManager.OnGetRegisteredUsers() {
            @Override
            public void success(ArrayList<User> contacts) {
                if (contacts.size() == 0) {
                    Toast.makeText(getContext(), "Any of your contacts has an account!", Toast.LENGTH_SHORT).show();
                }
                mContacts = contacts;
                mAdapter = new ContactsAdapter(getContext(), R.layout.contact_list_item, mContacts);
                mContactsListView.setAdapter(mAdapter);
            }

            @Override
            public void error(String message) {
                Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contacts_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add_contact){
            Intent intent = new Intent(Intent.ACTION_INSERT,ContactsContract.Contacts.CONTENT_URI);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnContactSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onViewSelected");
        }
    }
}
