package edu.ranken.gmiller.notepad.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    private int id;

    @NonNull
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    private String title;

    @NonNull
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    private String date;

    @NonNull
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    private String body;

    public Note(@NonNull String title, @NonNull String date, @NonNull String body) {
        this.title = title;
        this.date = date;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getBody() {
        return body;
    }

    public void setBody(@NonNull String body) {
        this.body = body;
    }
}
