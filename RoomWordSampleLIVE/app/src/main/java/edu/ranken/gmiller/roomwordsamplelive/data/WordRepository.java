package edu.ranken.gmiller.roomwordsamplelive.data;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;

import edu.ranken.gmiller.roomwordsamplelive.data.entity.Word;

public class WordRepository {

    private static final String TAG = WordRepository.class.getSimpleName();

    public static final String ACTION_ERROR = "edu.ranken.gmiller.roomwordsamplelive.ACTION_ERROR";
    public static final String EXTRA_MESSAGE = "message";

    private WordApp mApp;
    private WordDatabase mDatabase;
    private LiveData<List<Word>> mAllWords;
    private LocalBroadcastManager mBroadcastManager;

    public WordRepository(WordApp app, WordDatabase db) {
        mApp = app;
        mDatabase = db;
        mAllWords = db.getWordDao().getAllWords();
        mBroadcastManager =
            LocalBroadcastManager.getInstance(app);
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insertWord(Word word) {
        new InsertWordTask().execute(word);
    }

    private class InsertWordTask extends AsyncTask<Word, Void, Void> {

        @Override
        protected Void doInBackground(Word[] params) {
            try {
                Word word = params[0];
                mDatabase.getWordDao().insert(word);
            } catch (Exception ex) {
                Log.e(TAG, "doInBackground: Failed to Insert Word", ex);
                Intent broadcastIntent = new Intent(ACTION_ERROR);
                if (ex instanceof SQLiteConstraintException) { // if word exists
                    broadcastIntent.putExtra(EXTRA_MESSAGE, "Word already in database!");
                } else {
                    broadcastIntent.putExtra(EXTRA_MESSAGE, "Failed to insert new word.");
                }
                mBroadcastManager.sendBroadcast(broadcastIntent);
            }
            return null;
        }
    }
}
