package edu.ranken.gmiller.nprtechnologynews;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

public class GsonRequest<T> extends Request<T> {

    private final Gson gson;
    private final Class<T> cls;
    private final Response.Listener<T> listener;

    public GsonRequest(String url, Class<T> cls, Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        this.cls = cls;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(json, cls), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
