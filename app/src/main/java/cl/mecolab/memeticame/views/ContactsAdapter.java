package cl.mecolab.memeticame.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cl.mecolab.memeticame.models.User;
import cl.mecolab.memeticame.R;

/**
 * Custom Contacts Adapter.
 */
public class ContactsAdapter extends ArrayAdapter<User> {
    private ArrayList<User> mContacts;
    private final LayoutInflater mLayoutInflater;

    public ContactsAdapter(Context context, int resource, ArrayList<User> contacts) {
        super(context, resource, contacts);
        mContacts = contacts;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Return the view of a row.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        // Recycle views. Inflate the view only if its not already inflated.
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.contact_list_item, parent, false);
        }
        User user = mContacts.get(position);

        TextView nameView = (TextView) view.findViewById(R.id.contact_name);
        TextView phoneView = (TextView) view.findViewById(R.id.contact_phone_number);

        nameView.setText(user.mName);
        phoneView.setText(user.mPhoneNumber);

        return view;
    }
}
