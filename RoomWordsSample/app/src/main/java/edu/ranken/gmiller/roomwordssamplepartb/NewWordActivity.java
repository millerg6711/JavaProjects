package edu.ranken.gmiller.roomwordssamplepartb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
        "edu.ranken.gmiller.roomwordssamplepartb.REPLY";

    private EditText mEditWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mEditWord = findViewById(R.id.edit_word);

    }

    public void save(View view) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWord.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String word = mEditWord.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
