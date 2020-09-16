package edu.ranken.gmiller.notepad.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.ranken.gmiller.notepad.R;
import edu.ranken.gmiller.notepad.ui.fragment.EditNoteFragment;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TAG = AddNoteActivity.class.getSimpleName();

    private EditNoteFragment mEditNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setSupportActionBar(findViewById(R.id.toolbar));

        mEditNoteFragment =
            (EditNoteFragment) getSupportFragmentManager().findFragmentById(R.id.add_note_fragment);
    }

    public void editNote(View view) {
        mEditNoteFragment.editNote(view);
    }
}
