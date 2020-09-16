package edu.ranken.gmiller.notepad.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ranken.gmiller.notepad.R;
import edu.ranken.gmiller.notepad.data.Note;
import edu.ranken.gmiller.notepad.data.NoteApp;
import edu.ranken.gmiller.notepad.data.NoteRepository;
import edu.ranken.gmiller.notepad.ui.adapter.NotesListAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int REQUEST_ADD_NOTE = 0;
    public static final int REQUEST_EDIT_NOTE = 1;

    private NoteApp mApp;
    private NoteRepository mRepo;
    private RecyclerView mRecyclerView;
    private NotesListAdapter mAdapter;
    private LocalBroadcastManager mBroadcastManager;
    private BroadcastErrorReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        mRecyclerView = findViewById(R.id.recycler_view);

        mApp = (NoteApp) getApplication();
        mRepo = mApp.getRepo();

        mReceiver = new BroadcastErrorReceiver();
        mBroadcastManager = LocalBroadcastManager.getInstance(this);
        LiveData<List<Note>> notesData = mRepo.getAllNotes();

        mAdapter = new NotesListAdapter(this, notesData.getValue());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        notesData.observe(this, (List<Note> notes) -> {
            mAdapter.setNotes(notes);
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = mAdapter.getNoteAtPosition(position);
                displayToast(getString(R.string.toast_note_deleted));
                // Delete the note
                mRepo.deleteNote(note.getId());
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NoteRepository.ACTION_INSERT_NOTE_ERROR);
        intentFilter.addAction(NoteRepository.ACTION_GET_NOTE_ERROR);
        intentFilter.addAction(NoteRepository.ACTION_UPDATE_NOTE_ERROR);
        intentFilter.addAction(NoteRepository.ACTION_DELETE_NOTE_ERROR);
        intentFilter.addAction(NoteRepository.ACTION_DELETE_ALL_NOTES_ERROR);
        mBroadcastManager.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        mBroadcastManager.unregisterReceiver(mReceiver);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            new AlertDialog.Builder(this)
                .setMessage(R.string.alert_delete_all_messages)
                .setCancelable(true)
                .setNeutralButton(R.string.alert_option_no, (dialog, which) -> { /* do nothing */ })
                .setPositiveButton(R.string.alert_option_yes, (dialog, which) -> {
                    mRepo.deleteAllNotes();
                }).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, REQUEST_ADD_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (resultCode == RESULT_OK) {
                String title = data.getStringExtra(NoteApp.EXTRA_TITLE);
                String date = data.getStringExtra(NoteApp.EXTRA_DATE);
                String body = data.getStringExtra(NoteApp.EXTRA_BODY);
                if (title != null && date != null && body != null) {
                    switch (requestCode) {
                        case REQUEST_ADD_NOTE:
                            mRepo.insertNote(new Note(title, date, body));
                            break;
                        case REQUEST_EDIT_NOTE:
                            int id = data.getIntExtra(NoteApp.EXTRA_NOTE_ID, 0);
                            Note note = new Note(title, date, body);
                            note.setId(id);
                            mRepo.updateNote(note);
                            break;
                        default:
                            break;
                    }
                }
            } else if (resultCode == EditNoteActivity.RESULT_DELETE) {
                int id = data.getIntExtra(NoteApp.EXTRA_NOTE_ID, 0);
                mRepo.deleteNote(id);
                displayToast(getString(R.string.toast_note_deleted));
            }
        }

    }

    private void displayToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private class BroadcastErrorReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                String message;
                switch (action) {
                    case NoteRepository.ACTION_INSERT_NOTE_ERROR:
                        message = getString(R.string.toast_failed_to_add_note);
                        break;
                    case NoteRepository.ACTION_GET_NOTE_ERROR:
                        message = getString(R.string.toast_failed_to_get_note);
                        break;
                    case NoteRepository.ACTION_UPDATE_NOTE_ERROR:
                        message = getString(R.string.toast_failed_to_update_note);
                        break;
                    case NoteRepository.ACTION_DELETE_NOTE_ERROR:
                        message = getString(R.string.toast_failed_to_delete_note);
                        break;
                    case NoteRepository.ACTION_DELETE_ALL_NOTES_ERROR:
                        message = getString(R.string.toast_failed_to_delete_all_notes);
                        break;
                    default:
                        message = getString(R.string.toast_unknown_error);
                        break;
                }
                displayToast(message);
            }
        }
    }
}
