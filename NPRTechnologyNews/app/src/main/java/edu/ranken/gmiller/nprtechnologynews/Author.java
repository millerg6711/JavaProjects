package edu.ranken.gmiller.nprtechnologynews;

import android.os.Parcel;
import android.os.Parcelable;

public class Author implements Parcelable {

    private String name;

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    public Author(String name) {
        this.name = name;
    }

    protected Author(Parcel in) {
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public String getName() {
        return name;
    }
}
