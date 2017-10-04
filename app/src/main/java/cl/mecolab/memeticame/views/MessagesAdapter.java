package cl.mecolab.memeticame.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cl.mecolab.memeticame.R;
import cl.mecolab.memeticame.models.Message;
import cl.mecolab.memeticame.models.User;

/**
 * Custom Messages Adapter.
 */
public class MessagesAdapter extends ArrayAdapter<Message> {
    private final User mUser;
    private ArrayList<Message> mMessages;
    private final LayoutInflater mLayoutInflater;

    public MessagesAdapter(Context context, int resource, ArrayList<Message> messages, User user) {
        super(context, resource, messages);
        mUser = user;
        mMessages = messages;
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
            view = mLayoutInflater.inflate(R.layout.messages_list_item, parent, false);
        }
        Message message = mMessages.get(position);
        TextView messageView = (TextView) view.findViewById(R.id.message);

        Boolean isUser = mUser.mPhoneNumber.equals(message.mSender_phone);
        messageView.setGravity(isUser ? Gravity.START: Gravity.END);
        messageView.setTextColor(isUser ? ContextCompat.getColor(getContext(), R.color.colorPrimary) : ContextCompat.getColor(getContext(), R.color.colorAccent));
        messageView.setText(message.mContent);
        return view;
    }
}
