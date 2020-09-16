package edu.ranken.gmiller.whowroteit;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, AsyncTaskResult<Book>> {
    private static final String LOG_TAG = FetchBook.class.getSimpleName();

    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;

    public FetchBook(TextView titleText, TextView authorText) {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
    }

    @Override
    protected void onPreExecute() {
        mTitleText.get().setText(R.string.loading);
        mAuthorText.get().setText("");
    }

    @Override
    protected AsyncTaskResult<Book> doInBackground(String... params)
    {
        try {
            Book book = NetworkUtils.getBookInfo(params[0]);
            return new AsyncTaskResult<>(book);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Failed to get book info", ex);
            return new AsyncTaskResult<>(ex);
        }
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Book> result) {
        if (result.getError() != null) {
            mTitleText.get().setText(result.getError().getMessage());
            mAuthorText.get().setText("");
        } else if (result.getResult() != null) {
            mTitleText.get().setText(result.getResult().getTitle());
            mAuthorText.get().setText(result.getResult().getAuthor());
        } else {
            mTitleText.get().setText(R.string.no_results);
            mAuthorText.get().setText("");
        }
    }
}
