package edu.ranken.gmiller.roomwordsamplelive.data;

import android.app.Application;

public class WordApp extends Application {

    private WordDatabase mDatabase;
    private WordRepository mRepo;

    @Override
    public void onCreate() {
        super.onCreate();

        mDatabase = WordDatabase.buildDatabase(this);
        mRepo = new WordRepository(this, mDatabase);
    }

    /*public WordDatabase getDatabase() {
        return mDatabase;
    }*/

    public WordRepository getRepo() {
        return mRepo;
    }
}
