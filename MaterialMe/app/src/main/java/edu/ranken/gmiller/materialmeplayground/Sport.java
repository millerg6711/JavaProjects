package edu.ranken.gmiller.materialmeplayground;

import android.os.Parcel;
import android.os.Parcelable;

class Sport implements Parcelable {
    private final int imageResource;
    private String title;
    private String info;
    private String details;

    public Sport(int imageResource, String title, String info, String details) {
        this.imageResource = imageResource;
        this.title = title;
        this.info = info;
        this.details = details;
    }

    protected Sport(Parcel in) {
        imageResource = in.readInt();
        title = in.readString();
        info = in.readString();
        details = in.readString();
    }

    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public String getDetails() {
        return details;
    }

    public int getImageResource() {
        return imageResource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResource);
        dest.writeString(title);
        dest.writeString(info);
        dest.writeString(details);
    }
}
