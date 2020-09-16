package edu.ranken.gmiller.nprtechnologynews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Article implements Parcelable {

    @SerializedName("image")
    private String imageUrl;
    @SerializedName("date_published")
    private Date datePublished;
    private Author author;
    private String title;
    private String summary;
    private String url;

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

    public Article(String imageUrl, Date datePublished, Author authorName, String title, String summary, String url) {
        this.imageUrl = imageUrl;
        this.datePublished = datePublished;
        this.author = authorName;
        this.title = title;
        this.summary = summary;
        this.url = url;
    }

    protected Article(Parcel in) {
        imageUrl = in.readString();
        title = in.readString();
        summary = in.readString();
        url = in.readString();
        datePublished = (Date) in.readSerializable();
        author = in.readParcelable(Author.class.getClassLoader());
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public Author getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(url);
        dest.writeSerializable(datePublished);
        dest.writeParcelable(author, flags);
    }
}
