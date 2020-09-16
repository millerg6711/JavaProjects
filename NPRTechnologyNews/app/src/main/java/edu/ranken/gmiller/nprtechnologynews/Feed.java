package edu.ranken.gmiller.nprtechnologynews;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Feed {

    private String description;
    @SerializedName("items")
    private ArrayList<Article> articles;

    public Feed(String description, ArrayList<Article> articles) {
        this.description = description;
        this.articles = articles;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }
}

