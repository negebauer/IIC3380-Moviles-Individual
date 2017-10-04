package cl.mecolab.memeticame.models;

/**
 * Created by Andres Matte.
 */
public class User {
    public final Integer mId;
    public final String mName;
    public final String mPhoneNumber;

    private User(Integer id, String name, String phoneNumber) {
        mId = id;
        mName = name;
        mPhoneNumber = phoneNumber;
    }

    public static class Builder {
        private Integer mId;
        private String mName;
        private String mPhoneNumber;

        public Builder id(Integer id) {
            mId = id;
            return this;
        }

        public Builder name(String name) {
            mName = name;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            mPhoneNumber = phoneNumber;
            return this;
        }

        public User build() {
            return new User(mId, mName, mPhoneNumber);
        }
    }
}