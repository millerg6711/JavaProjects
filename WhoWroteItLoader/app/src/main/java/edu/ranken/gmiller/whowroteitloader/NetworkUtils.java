package edu.ranken.gmiller.whowroteitloader;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String QUERY_PARAM = "q";
    private static final String MAX_RESULTS = "maxResults";
    private static final String PRINT_TYPE = "printType";
    private static final String DOWNLOAD = "download";

    public static Book getBookInfo(String queryString) throws IOException, JSONException {
        HttpURLConnection conn = null;
        try {
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, "10")
                .appendQueryParameter(PRINT_TYPE, "books")
                .appendQueryParameter(DOWNLOAD, "epub")
                .build();

            URL requestUrl = new URL(builtURI.toString());

            Log.d(LOG_TAG, "Fetching URL: " + requestUrl);

            conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.connect();

            StringBuilder builder = new StringBuilder();
            try (InputStream inputStream = conn.getInputStream()) {
                try(InputStreamReader reader = new InputStreamReader(inputStream)) {
                    try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            builder.append(line).append("\n");
                        }
                    }
                }
            }

            if (builder.length() <= 0) {
                return null;
            }

            String jsonStr = builder.toString();

            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray itemsArr = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemsArr.length(); ++i) {
                try {
                    JSONObject volumeInfo =
                        itemsArr.getJSONObject(i)
                                .getJSONObject("volumeInfo");
                        String title = volumeInfo.getString("title");
                        JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                        StringBuilder authors = new StringBuilder();
                    for (int j = 0; j < authorsArray.length(); ++j) {
                        authors.append(authorsArray.getString(j)).append(", ");
                    }
                    if (authors.length() > 0) {
                        authors.setLength(authors.length() - 2);
                    }
                    if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(authors)) {
                        return new Book(title, authors.toString());
                    }

                } catch (Exception ex) {
                    Log.d(LOG_TAG, "Failed to parse book " + i, ex);
                }
            }
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
