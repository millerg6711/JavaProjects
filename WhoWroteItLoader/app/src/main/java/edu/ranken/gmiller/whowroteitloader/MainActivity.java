package edu.ranken.gmiller.whowroteitloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, LoaderManager.LoaderCallbacks<AsyncTaskResult<Book>>  {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int BOOK_LOADER = 0;
    private static final String BOOK_TITLE = "bookTitle";
    private static final String BOOK_AUTHOR = "bookAuthor";
    private static final String QUERY_STRING ="queryString";

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private LoaderManager mLoaderMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);

        mBookInput.setOnEditorActionListener(this);

        if (savedInstanceState != null) {
            mTitleText.setText(savedInstanceState.getString(BOOK_TITLE));
            mAuthorText.setText(savedInstanceState.getString(BOOK_AUTHOR));
        }

        mLoaderMgr = LoaderManager.getInstance(this);
        if (mLoaderMgr.getLoader(BOOK_LOADER) != null) {
            mLoaderMgr.initLoader(BOOK_LOADER, null, this);
        }
    }

    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();
        hideKeyboard(mBookInput);

        if (TextUtils.isEmpty(queryString)) {
            mTitleText.setText(R.string.no_search_term);
            mAuthorText.setText("");
            mLoaderMgr.destroyLoader(BOOK_LOADER);
        } else if (!isNetworkConnected()) {
            mTitleText.setText(R.string.no_network);
            mAuthorText.setText("");
            mLoaderMgr.destroyLoader(BOOK_LOADER);
        } else {
            Log.d(LOG_TAG, "Fetching Book: " + queryString);
            Bundle queryBundle = new Bundle();
            queryBundle.putString(QUERY_STRING, queryString);
            mLoaderMgr.restartLoader(BOOK_LOADER, queryBundle, this);
        }
    }

    private void hideKeyboard(EditText view) {
        InputMethodManager inputManager =
            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr =
            (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        } else {
            return false;
        }
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        searchBooks(view);
        return true;
    }

    @NonNull
    @Override
    public Loader<AsyncTaskResult<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString(QUERY_STRING);
        }

        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<AsyncTaskResult<Book>> loader, AsyncTaskResult<Book> data) {
        if (data.getError() != null) {
            mTitleText.setText(data.getError().getMessage());
            mAuthorText.setText("");
        } else if (data.getResult() != null) {
            mTitleText.setText(data.getResult().getTitle());
            mAuthorText.setText(data.getResult().getAuthor());
        } else {
            mTitleText.setText(R.string.no_results);
            mAuthorText.setText("");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<AsyncTaskResult<Book>> loader) { }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(BOOK_TITLE, mTitleText.getText().toString());
        outState.putString(BOOK_AUTHOR, mAuthorText.getText().toString());
    }
}
