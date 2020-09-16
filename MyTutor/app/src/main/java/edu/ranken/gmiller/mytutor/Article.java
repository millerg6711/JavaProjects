package edu.ranken.gmiller.mytutor;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Article implements Parcelable {
    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    private String mTitle;
    private String mAuthor;
    private Calendar mPublishedDate;
    private String mTopic;
    private String mLink;

    public Article(String title, String author, Calendar publishedDate, String topic, String link) {
        mTitle = title;
        mAuthor = author;
        mPublishedDate = publishedDate;
        mTopic = topic;
        mLink = link;
    }

    protected Article(Parcel in) {
        mTitle = in.readString();
        mAuthor = in.readString();
        mTopic = in.readString();
        mLink = in.readString();
        mPublishedDate = (Calendar) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mAuthor);
        dest.writeString(mTopic);
        dest.writeString(mLink);
        dest.writeSerializable(mPublishedDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public Calendar getPublishedDate() {
        return mPublishedDate;
    }

    public String getTopic() {
        return mTopic;
    }

    public String getLink() {
        return mLink;
    }
}


