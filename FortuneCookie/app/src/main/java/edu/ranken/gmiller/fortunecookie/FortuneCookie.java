package edu.ranken.gmiller.fortunecookie;

import com.google.gson.annotations.SerializedName;

public class FortuneCookie {
    @SerializedName("Fortune")
    public String fortune;

    @SerializedName("LastUpdated")
    public String lastUpdated;
}
