package edu.ranken.gmiller.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WordListDatabase mDatabase;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);
        mDatabase = new WordListDatabase(this);

        List<Word> words = mDatabase.getAllWords();
        StringBuilder output = new StringBuilder();
        for (Word word : words) {
            output.append(getString(R.string.word_format, word.getId(), word.getWord(), word.getDefinition()));
        }

        Word word2 = mDatabase.getWordById(2);
        if (word2 != null) {
            output.append("\nFound Word:\n");
            output.append(getString(R.string.word_format, word2.getId(), word2.getWord(), word2.getDefinition()));
        } else {
            output.append("\nWord Not Found\n");
        }

        mTextView.setText(output);
    }
}
