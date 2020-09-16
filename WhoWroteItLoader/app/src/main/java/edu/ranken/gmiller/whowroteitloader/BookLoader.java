package edu.ranken.gmiller.whowroteitloader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<AsyncTaskResult<Book>> {
    private static final String LOG_TAG = BookLoader.class.getSimpleName();

    private String mQueryString;

    public BookLoader(@NonNull Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading() {
        this.getClass().getSimpleName();
        forceLoad();
    }

    @Nullable
    @Override
    public AsyncTaskResult<Book> loadInBackground() {
        try {
            Book book = NetworkUtils.getBookInfo(mQueryString);
            return new AsyncTaskResult<>(book);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Failed to get book info", ex);
            return new AsyncTaskResult<>(ex);
        }
    }
}
