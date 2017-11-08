package com.jaychang.sa;


import android.os.Parcel;

public class TwitterUser extends SocialUser {

    public static final Creator<TwitterUser> CREATOR = new Creator<TwitterUser>() {
        @Override public TwitterUser createFromParcel(Parcel source) {
            return new TwitterUser(source);
        }

        @Override public TwitterUser[] newArray(int size) {
            return new TwitterUser[size];
        }
    };

    public String secret;
    private SocialUser socialUser;

    public TwitterUser() {
        super();
    }

    public TwitterUser(SocialUser socialUser, String secret) {
        super(socialUser);
        this.socialUser = socialUser;
        this.secret = secret;
    }

    protected TwitterUser(Parcel in) {
        super(in);
        this.socialUser = in.readParcelable(SocialUser.class.getClassLoader());
        this.secret = in.readString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.socialUser, flags);
        dest.writeString(this.secret);
    }
}
