package edu.ranken.gmiller.mytutor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddArticleActivity extends AppCompatActivity {
    private static final String LOG_TAG = AddArticleActivity.class.getSimpleName();

    public static final String ARTICLE_EXTRA = "edu.ranken.gmiller.mytutor.extra.ARTICLE";

    private Toolbar mToolbar;
    private EditText mTitleInput;
    private EditText mEditAuthorInput;
    private EditText mEditDateInput;
    private Spinner mSpinnerTopics;
    private EditText mLinkInput;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        // Get Widgets
        mTitleInput = findViewById(R.id.edit_article_title);
        mEditAuthorInput = findViewById(R.id.edit_article_author);
        mEditDateInput = findViewById(R.id.edit_date_published);
        mSpinnerTopics = findViewById(R.id.spinner_article_topics);
        mLinkInput = findViewById(R.id.edit_article_link);
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);

        setSupportActionBar(mToolbar);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.topics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTopics.setAdapter(adapter);

        // Setup Listeners
        //mEditDateInput.setOnEditorActionListener((v, actionId, event) -> actionId == EditorInfo.IME_ACTION_DONE);
        mLinkInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mFab.performClick();
                return true;
            }
            return false;
        });
        mToolbar.setNavigationOnClickListener((v -> {
            finish();
        }));

    }

    public void createArticle(View view) {
        if (isEmpty(mTitleInput) || isEmpty(mEditAuthorInput) || isEmpty(mEditDateInput) || isEmpty(mLinkInput)) {
            showAlert(getString(R.string.alert_dialog_error_message));
        } else {
            // Create Date Format
            SimpleDateFormat fm = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());

            // Get Calendar
            Calendar calendar = Calendar.getInstance();
            try {
                // Parse the text input
                Date date = fm.parse(mEditDateInput.getText().toString());
                if (date != null) {
                    // Set the calendar date
                    calendar.setTime(date);

                    // Create new Article
                    Article article = new Article(
                        mTitleInput.getText().toString(),
                        mEditAuthorInput.getText().toString(),
                        calendar,
                        mSpinnerTopics.getSelectedItem().toString(),
                        mLinkInput.getText().toString()
                    );

                    // Create new Intent, add the Article and return the result back to HomeActivity
                    Intent newIntent = new Intent();
                    newIntent.putExtra(ARTICLE_EXTRA, article);
                    setResult(RESULT_OK, newIntent);
                    finish();
                } else {
                    showAlert(getString(R.string.alert_dialog_invalid_date_message));
                }
            } catch (ParseException e) {
                Log.e(LOG_TAG, "ParseException", e);
                showAlert(getString(R.string.alert_dialog_invalid_date_message));
            }
        }
    }

    public void chooseDate(View view) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog mDatePicker = new DatePickerDialog(this,
                (DatePicker picker, int year, int month, int dayOfMonth) -> {
                    String dateMsg = getString(R.string.edit_published_date_format_string, month + 1, dayOfMonth, year);
                    mEditDateInput.setText(dateMsg);
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        mDatePicker.show();
    }

    private boolean isEmpty(EditText text) {
        return TextUtils.isEmpty(text.getText().toString());
    }

    private void showAlert(String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddArticleActivity.this);
        alertBuilder.setTitle(getString(R.string.alert_dialog_error_title_text));
        alertBuilder.setMessage(message);
        alertBuilder.show();
    }
}
