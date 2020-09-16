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

    public void update(Word word) {
        new UpdateOneWordAsyncTask(mWordDao).execute(word);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(mWordDao).execute();
    }

    public void deleteWord(Word word) {
        new DeleteOneWordAsyncTask(mWordDao).execute(word);
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

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private IWordDao mAsyncTaskDao;

        public DeleteAllAsyncTask(IWordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class DeleteOneWordAsyncTask extends AsyncTask<Word, Void, Void> {

        private IWordDao mAsyncTaskDao;

        public DeleteOneWordAsyncTask(IWordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncTaskDao.deleteWord(words[0]);
            return null;
        }
    }

    private static class UpdateOneWordAsyncTask extends AsyncTask<Word, Void, Void> {

        private IWordDao mAsyncTaskDao;

        public UpdateOneWordAsyncTask(IWordDao mWordDao) {
            mAsyncTaskDao = mWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncTaskDao.update(words[0]);
            return null;
        }
    }
}
