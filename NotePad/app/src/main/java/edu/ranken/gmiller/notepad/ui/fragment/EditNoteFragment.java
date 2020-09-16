package edu.ranken.gmiller.notepad.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import edu.ranken.gmiller.notepad.R;
import edu.ranken.gmiller.notepad.data.Note;
import edu.ranken.gmiller.notepad.data.NoteApp;
import edu.ranken.gmiller.notepad.ui.utils.DateUtil;

/**
 * A simple {@link Fragment} subclass for editing and creating notes.
 */
public class EditNoteFragment extends Fragment implements TextView.OnEditorActionListener {

    private static final String TAG = EditNoteFragment.class.getSimpleName();

    private EditText mNoteTitleInput;
    private EditText mNoteDateInput;
    private ImageView mNoteDateImage;
    private EditText mNoteBodyInput;

    private Context mContext;
    private Note mNote;

    public EditNoteFragment() {
        mNote = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_edit_note, container, false);

        mNoteTitleInput = itemView.findViewById(R.id.edit_note_title);
        mNoteDateInput = itemView.findViewById(R.id.edit_note_date);
        mNoteBodyInput = itemView.findViewById(R.id.edit_note_body);
        mNoteDateImage = itemView.findViewById(R.id.image_note_date);

        mContext = getContext();

        if (mNote != null) {
            mNoteTitleInput.setText(mNote.getTitle());
            mNoteDateInput.setText(mNote.getDate());
            mNoteBodyInput.setText(mNote.getBody());
        } else {
            setDate();
        }

        mNoteTitleInput.setOnEditorActionListener(this);
        mNoteBodyInput.setOnEditorActionListener(this);
        mNoteDateImage.setOnClickListener(this::onPickDate);

        return itemView;
    }

    public void setNote(Note note) {
        mNote = note;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            editNote(v);
            return true;
        }
        return false;
    }

    public void editNote(View view) {
        String title = mNoteTitleInput.getText().toString();
        String date = mNoteDateInput.getText().toString();
        String body = mNoteBodyInput.getText().toString();

        hideSoftKeyboard(view);

        if (TextUtils.isEmpty(title) ||
            TextUtils.isEmpty(date) ||
            TextUtils.isEmpty(body)) {
            Toast.makeText(mContext, R.string.error_message_incomplete_data, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            DateUtil.parseDate(date);
        } catch (ParseException ex) {
            String msg = getString(R.string.error_message_parse_date);
            Log.e(TAG, msg, ex);
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(NoteApp.EXTRA_TITLE, title);
        intent.putExtra(NoteApp.EXTRA_DATE, date);
        intent.putExtra(NoteApp.EXTRA_BODY, body);
        if (mNote != null) {
            intent.putExtra(NoteApp.EXTRA_NOTE_ID, mNote.getId());
        }
        Activity activity = getActivity();
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

    private void setDate() {
        TimeZone tz = TimeZone.getDefault();
        Calendar parsedCal = Calendar.getInstance(tz);
        mNoteDateInput.setText(DateUtil.formatDate(parsedCal));
    }

    private void onPickDate(View view) {
        Calendar cal;
        try {
            String date = mNoteDateInput.getText().toString();
            cal = DateUtil.parseDate(date);
        } catch (Exception ex) {
            cal = Calendar.getInstance();
        }

        hideSoftKeyboard(mNoteDateInput);

        DatePickerDialog dlg = new DatePickerDialog(mContext,
            (view1, year, month, dayOfMonth) -> {
                Calendar newDate = new GregorianCalendar(year, month, dayOfMonth);
                mNoteDateInput.setText(DateUtil.formatDate(newDate));
                mNoteDateInput.requestFocus();
                mNoteDateInput.setSelection(0, mNoteDateInput.getText().length());
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        );
        dlg.show();
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager ime = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (ime != null) {
            ime.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
