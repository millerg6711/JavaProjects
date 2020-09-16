package edu.ranken.gmiller.roomwordsamplelive.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.ranken.gmiller.roomwordsamplelive.R;

public class NewWordActivity extends AppCompatActivity {

    /********************************************************************************
     *
     *
     *
     *
     *
     * SEE RoomWordsSamplePartB Project for 10.1B Tasks and Coding Challenge
     * I did 10.1B before the live stream happened to have more time on my final.
     *
     *
     *
     *
     * ******************************************************************************/

    public static final String EXTRA_WORD = "word";

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mEditText = findViewById(R.id.edit_word);
    }

    public void onSave(View view) {
        String word = mEditText.getText().toString();
        if (TextUtils.isEmpty(word)) {
            Toast.makeText(this, R.string.enter_word_error, Toast.LENGTH_LONG).show();
        } else {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_WORD, word);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}
