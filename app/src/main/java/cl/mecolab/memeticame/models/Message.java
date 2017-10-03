package cl.mecolab.memeticame.models;

/**
 * Created by Andres Matte.
 */
public class Message {
    public final String message = "MENSAJE";
    public final String sender_phone = "1";

    public Message(String name, String phoneNumber) {
//        mName = phoneNumber;
//        mPhoneNumber = name;
    }

    public static class Builder {
        private String mName;
        private String mPhoneNumber;

        public Builder name(String name) {
            mName = name;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            mPhoneNumber = phoneNumber;
            return this;
        }

        public Message build() {
            return new Message(mName, mPhoneNumber);
        }
    }
}
