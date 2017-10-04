package cl.mecolab.memeticame.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andres Matte.
 */
public class User implements Parcelable {
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

    protected User(Parcel in) {
        mId = in.readByte() == 0x00 ? null : in.readInt();
        mName = in.readString();
        mPhoneNumber = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(mId);
        }
        dest.writeString(mName);
        dest.writeString(mPhoneNumber);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}