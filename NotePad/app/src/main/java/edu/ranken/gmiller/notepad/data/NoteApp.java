package edu.ranken.gmiller.notepad.data;

import android.app.Application;

public class NoteApp extends Application {

    private static final String TAG = NoteApp.class.getSimpleName();

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_BODY = "body";
    public static final String EXTRA_NOTE_ID = "noteId";

    private NoteDatabase mDatabase;
    private NoteRepository mRepo;

    @Override
    public void onCreate() {
        super.onCreate();

        mDatabase = NoteDatabase.build(this);
        mRepo = new NoteRepository(this, mDatabase);
    }

    public NoteRepository getRepo() {
        return mRepo;
    }
}
