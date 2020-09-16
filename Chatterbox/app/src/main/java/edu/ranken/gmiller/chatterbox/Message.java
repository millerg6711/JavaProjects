package edu.ranken.gmiller.chatterbox;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Message implements Parcelable {
    private String user;
    private String msg;
    private Date time;

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public Message(String user, String msg) {
        this.user = user;
        this.msg = msg;
    }

    protected Message(Parcel in) {
        user = in.readString();
        msg = in.readString();
        time = (Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(msg);
        dest.writeSerializable(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUser() {
        return user;
    }

    public String getMsg() {
        return msg;
    }

    public Date getTime() {
        return time;
    }

    public String getDateStr() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(time);
    }
}
