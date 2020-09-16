package edu.ranken.gmiller.roomwordssamplepartb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {

    private IWordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application app) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(app);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        new InsertAsyncTask(mWordDao).execute(word);
    }

    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {

        private IWordDao mAsyncTaskDao;

        public InsertAsyncTask(IWordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }
}
