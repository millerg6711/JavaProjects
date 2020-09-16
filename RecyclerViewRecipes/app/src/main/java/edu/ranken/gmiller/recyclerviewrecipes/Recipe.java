package edu.ranken.gmiller.recyclerviewrecipes;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    private String title;
    private String descripition;
    private byte[] imageBytes;
    private String[] ingredients;
    private String[] procedures;

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public Recipe(String title, String descripition, String[] ingredients, String[] procedures) {
        this.title = title;
        this.descripition = descripition;
        this.ingredients = ingredients;
        this.procedures = procedures;
    }

    private Recipe(Parcel in) {
        title = in.readString();
        descripition = in.readString();
        imageBytes = in.createByteArray();
        ingredients = in.createStringArray();
        procedures = in.createStringArray();
    }

    public String getTitle() {
        return title;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String[] getProcedures() {
        return procedures;
    }

    public String getDescripition() {
        return descripition;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(descripition);
        dest.writeByteArray(imageBytes);
        dest.writeStringArray(ingredients);
        dest.writeStringArray(procedures);
    }
}
