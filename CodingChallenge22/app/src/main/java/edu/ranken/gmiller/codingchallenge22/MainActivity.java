package edu.ranken.gmiller.codingchallenge22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final int ITEM_REQUEST = 1;
    private static final String SAVED_ITEMS = "edu.ranken.gmiller.codingchallenge22.SAVED_ITEMS";

    private EditText mEditTextLoc;
    private TextView[] items;
    private int count;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (count > 0) {
            String[] savedItems = new String[count];

            for (int i = 0; i < savedItems.length; i++) {
                savedItems[i] = String.format(Locale.ENGLISH,"item%d_text", i);
            }

            outState.putStringArray(SAVED_ITEMS, savedItems);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextLoc = findViewById(R.id.text_edit_loc);
        items = new TextView[] {
            findViewById(R.id.text_item_one),
            findViewById(R.id.text_item_two),
            findViewById(R.id.text_item_three),
            findViewById(R.id.text_item_four),
            findViewById(R.id.text_item_five),
            findViewById(R.id.text_item_six),
            findViewById(R.id.text_item_seven),
            findViewById(R.id.text_item_eight),
            findViewById(R.id.text_item_nine),
            findViewById(R.id.text_item_ten),
        };
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String[] savedItems = savedInstanceState.getStringArray(SAVED_ITEMS);

        if (savedItems != null) {
            for (int i = 0; i < savedItems.length; i++) {
                items[i].setText(savedItems[i]);
            }
        }
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, ITEM_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ITEM_REQUEST && resultCode == RESULT_OK) {
            if (count < items.length) {
                String reply = data.getStringExtra(SecondActivity.EXTRA_ITEMS);
                TextView editItem = items[count];
                editItem.setText(reply);
                editItem.setVisibility(View.VISIBLE);
                count++;
            }
        }
    }

    public void findStore(View view) {
        String location = mEditTextLoc.getText().toString();
        Uri addressUri = Uri.parse("geo:0,0?q=" + location);

        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        if(intent.resolveActivity(getPackageManager()) != null ){
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Can't handle location!");
        }
    }
}
