package edu.ranken.gmiller.notepad.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import edu.ranken.gmiller.notepad.R;
import edu.ranken.gmiller.notepad.data.Note;
import edu.ranken.gmiller.notepad.data.NoteApp;
import edu.ranken.gmiller.notepad.data.NoteRepository;
import edu.ranken.gmiller.notepad.ui.fragment.EditNoteFragment;

public class EditNoteActivity extends AppCompatActivity {

    private static final String TAG = EditNoteActivity.class.getSimpleName();

    public static final int RESULT_DELETE = -2;

    private EditNoteFragment mEditNoteFragment;
    private Note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setSupportActionBar(findViewById(R.id.toolbar));

        Intent intent = getIntent();
        int noteId = intent.getIntExtra(NoteApp.EXTRA_NOTE_ID, 0);

        NoteApp app = (NoteApp) getApplication();
        NoteRepository repo = app.getRepo();
        mNote = repo.getNoteById(noteId);

        FragmentManager manager = getSupportFragmentManager();

        mEditNoteFragment = (EditNoteFragment) manager.findFragmentById(R.id.edit_note_fragment);
        if (mEditNoteFragment != null) {
            mEditNoteFragment.setNote(mNote);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            Intent intent = new Intent();
            intent.putExtra(NoteApp.EXTRA_NOTE_ID, mNote.getId());
            setResult(RESULT_DELETE, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editNote(View view) {
        mEditNoteFragment.editNote(view);
    }
}
