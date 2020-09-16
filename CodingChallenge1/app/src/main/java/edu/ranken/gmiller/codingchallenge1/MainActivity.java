package edu.ranken.gmiller.codingchallenge1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "edu.ranken.gmiller.codingchallenge1.extra.TEXT";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);

        int passageResourceId;
        switch (view.getId()){
            case R.id.button_text_one:
                passageResourceId = R.string.passage_one;
                break;
            case R.id.button_text_two:
                passageResourceId = R.string.passage_two;
                break;
            case R.id.button_text_three:
                passageResourceId = R.string.passage_three;
                break;
            default:
                Log.e(LOG_TAG, "There are no passages for this button.");
                return;
        }

        intent.putExtra(EXTRA_MESSAGE, getString(passageResourceId));
        startActivity(intent);
    }
}
