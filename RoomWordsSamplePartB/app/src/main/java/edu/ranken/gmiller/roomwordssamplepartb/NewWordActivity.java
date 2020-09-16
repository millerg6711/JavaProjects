package edu.ranken.gmiller.roomwordssamplepartb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
        "edu.ranken.gmiller.roomwordssamplepartb.REPLY";

    private EditText mEditWord;

    private Word mWord;
    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mEditWord = findViewById(R.id.edit_word);
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        mWord = getIntent().getParcelableExtra("word");
        if (mWord != null) {
            mEditWord.setText(mWord.getWord());
            mEditWord.setSelection(mWord.getWord().length());
        }
    }

    public void save(View view) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWord.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String word = mEditWord.getText().toString();
            if (mWord != null && !word.equals(mWord.getWord())) {
                mWordViewModel.update(new Word(mWord.getId(), word));
                setResult(RESULT_OK);
                finish();
                return;
            }
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}
