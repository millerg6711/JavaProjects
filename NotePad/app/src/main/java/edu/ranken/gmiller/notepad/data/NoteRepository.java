package edu.ranken.gmiller.notepad.data;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;

public class NoteRepository {

    private static final String TAG = NoteRepository.class.getSimpleName();

    public static final String ACTION_INSERT_NOTE_ERROR = "edu.ranken.gmiller.notepad.INSERT_NOTE_ERROR";
    public static final String ACTION_UPDATE_NOTE_ERROR = "edu.ranken.gmiller.notepad.UPDATE_NOTE_ERROR";
    public static final String ACTION_DELETE_NOTE_ERROR = "edu.ranken.gmiller.notepad.DELETE_NOTE_ERROR";
    public static final String ACTION_DELETE_ALL_NOTES_ERROR = "edu.ranken.gmiller.notepad.DELETE_ALL_NOTES_ERROR";
    public static final String ACTION_GET_NOTE_ERROR = "edu.ranken.gmiller.notepad.GET_NOTE_ERROR";

    private NoteDatabase mDatabase;
    private LiveData<List<Note>> mNotes;
    private LocalBroadcastManager mBroadcastManager;

    public NoteRepository(NoteApp app, NoteDatabase db) {
        mDatabase = db;
        mNotes = mDatabase.getNoteDao().getAllNotes();
        mBroadcastManager = LocalBroadcastManager.getInstance(app);
    }

    public void sendBroadcast(String action) {
        Intent intent = new Intent(action);
        mBroadcastManager.sendBroadcast(intent);
    }

    public LiveData<List<Note>> getAllNotes() {
        return mNotes;
    }

    public void insertNote(Note note) {
        new Thread() {
            @Override
            public void run() {
                try {
                    NoteDao dao = mDatabase.getNoteDao();
                    dao.insertNote(note);
                } catch (Exception ex) {
                    Log.e(TAG, "insertNote: Failed to Insert Note", ex);
                    sendBroadcast(ACTION_INSERT_NOTE_ERROR);
                }
            }
        }.start();
    }

    public void updateNote(Note note) {
        new Thread() {
            @Override
            public void run() {
                try {
                    NoteDao dao = mDatabase.getNoteDao();
                    dao.updateNote(note);
                } catch (Exception ex) {
                    Log.e(TAG, "updateNote: Failed to Update Note", ex);
                    sendBroadcast(ACTION_UPDATE_NOTE_ERROR);
                }
            }
        }.start();
    }

    public void deleteNote(int id) {
        new Thread() {
            @Override
            public void run() {
                try {
                    NoteDao dao = mDatabase.getNoteDao();
                    dao.deleteNote(id);
                } catch (Exception ex) {
                    Log.e(TAG, "deleteNote: Failed to Delete Note", ex);
                    sendBroadcast(ACTION_DELETE_NOTE_ERROR);
                }
            }
        }.start();
    }

    public void deleteAllNotes() {
        new Thread() {
            @Override
            public void run() {
                try {
                    NoteDao dao = mDatabase.getNoteDao();
                    dao.deleteAllNotes();
                } catch (Exception ex) {
                    Log.e(TAG, "deleteAllNotes: Failed to Delete All Notes", ex);
                    sendBroadcast(ACTION_DELETE_ALL_NOTES_ERROR);
                }
            }
        }.start();
    }

    public Note getNoteById(int id) {
        // I chose this way instead of using an AsyncTask to retrieve the result so I could
        // be consistent with only using threads like the other methods. (See below)
        GetNoteByIdRunnable runnable = new GetNoteByIdRunnable(id);
        try {
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
            return runnable.getValue();
        } catch (Exception ex) {
            Log.e(TAG, "getNoteById: Failed to Get Note", ex);
            sendBroadcast(ACTION_GET_NOTE_ERROR);
        }
        return null;
    }

    public class GetNoteByIdRunnable implements Runnable {

        private volatile Note mValue;
        private int mId;

        public GetNoteByIdRunnable(int id) {
            mId = id;
        }

        @Override
        public void run() {
            try {
                NoteDao dao = mDatabase.getNoteDao();
                mValue = dao.getNoteById(mId);
            } catch (Exception ex) {
                Log.e(TAG, "run: Failed to Get Note", ex);
                sendBroadcast(ACTION_GET_NOTE_ERROR);
            }
        }

        public Note getValue() {
            return mValue;
        }
    }

    /*
    public Note getNoteById(int id) {
        try {
            return new NoteRepository.GetNoteByIdAsyncTask().execute(id).get();
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private class GetNoteByIdAsyncTask extends AsyncTask<Integer, Void, Note> {
        @Override
        protected Note doInBackground(Integer... integers) {
            return mDatabase.getNoteDao().getNoteById(integers[0]);
        }
    }
    */
}
