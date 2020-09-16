package edu.ranken.gmiller.counterhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewCount;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCount = findViewById(R.id.text_count);
        if (savedInstanceState != null){
            String cnt = savedInstanceState.getString("count");
            if (cnt != null) {
                mTextViewCount.setText(cnt);
                count = Integer.parseInt(cnt);
            }
        }
    }

    public void increment(View view) {
        ++count;
        mTextViewCount.setText(Integer.toString(count));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("count", mTextViewCount.getText().toString());
    }
}
