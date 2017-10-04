package cl.mecolab.memeticame.models;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Andres Matte.
 */
public class Message implements Comparable<Message> {
    public final Integer mId;
    public final String mSender_phone;
    public final String mContent;
    public final Integer mChat_id;
//    public final JSONObject mAttachment_link;
//    public final Date created_at;

    private Message(Integer id, String sender_phone, String content, Integer chat_id) {
        mId = id;
        mSender_phone = sender_phone;
        mContent = content;
        mChat_id = chat_id;
//        mAttachment_link = attachment_link;
    }

    @Override
    public int compareTo(@NonNull Message message) {
        return mId - message.mId;
    }

    public static class Builder {
        private Integer mId;
        private String mSender_phone;
        private String mContent;
        private Integer mChat_id;
//        private JSONObject mAttachment_link;

        public Builder id(Integer id) {
            mId = id;
            return this;
        }

        public Builder sender_phone(String sender_phone) {
            mSender_phone = sender_phone;
            return this;
        }
        public Builder content(String content) {
            mContent = content;
            return this;
        }
        public Builder chat_id(Integer chat_id) {
            mChat_id = chat_id;
            return this;
        }
//        public Builder attachment_link(JSONObject attachment_link) {
//            mAttachment_link = attachment_link;
//            return this;
//        }

        public Message build() {
            return new Message(mId, mSender_phone, mContent, mChat_id);
        }
    }


}