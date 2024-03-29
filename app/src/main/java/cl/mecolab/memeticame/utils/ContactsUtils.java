package cl.mecolab.memeticame.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;

import cl.mecolab.memeticame.models.User;

/**
 * Created by Andres Matte.
 */
public class ContactsUtils {
    public static void getContacts(Context context, ContactsProviderListener listener){
        new GetContactsTask(context, listener).execute();
    }

    public interface ContactsProviderListener{
        void contactsLoaded(ArrayList<User> users);
    }

    /**
     * AsyncTask enables proper and easy use of the UI thread. This class allows to perform
     * background operations and publish results on the UI thread without having to manipulate
     * threads and/or handlers.
     * See <a href="https://developer.android.com/reference/android/os/AsyncTask.html">AsyncTask</a>
     */
    private static class GetContactsTask extends AsyncTask<String, Void, ArrayList<User>> {

        private final Context context;
        private final ContactsProviderListener listener;
        private ContentResolver mResolver;

        public GetContactsTask(Context context, ContactsProviderListener listener){
            super();
            this.context = context;
            this.listener = listener;
            this.mResolver = context.getContentResolver();
        }

        @Override
        protected ArrayList<User> doInBackground(String... params) {
            return getPhoneContacts(context);
        }

        @Override
        protected void onPostExecute(ArrayList<User> users) {
            listener.contactsLoaded(users);
        }

        public ArrayList<User> getPhoneContacts(Context context) {
            if (mResolver == null) mResolver = context.getContentResolver();
            ArrayList<User> contacts = new ArrayList<>();
            Cursor cursor = mResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            if (cursor != null) {
                if(cursor.getCount() != 0 && cursor.moveToFirst()) {
                    do {
                        User contact = getContact(cursor);
                        if (contact != null) {
                            contacts.add(contact);
                        }
                    } while (cursor.moveToNext()) ;
                }
                cursor.close();
            }

            return contacts;
        }

        private User getContact(Cursor cursor) {
            User contact = null;
            String id = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts._ID));
            if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor c = mResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id},
                        null);
                if (c != null) {
                    if(c.getCount() != 0 && c.moveToFirst()) {
                        String phoneNumber = c.getString(c.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String name = c.getString(c.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        contact = new User.Builder().name(name).phoneNumber(phoneNumber).build();
                    }
                    c.close();
                }

            }
            return contact;
        }
    }
}

