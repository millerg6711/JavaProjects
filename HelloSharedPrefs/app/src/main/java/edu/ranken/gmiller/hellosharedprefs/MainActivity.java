package edu.ranken.gmiller.hellosharedprefs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private int mCount = 0;
    private int mColor;

    private TextView mShowCount;
    private Button mResetButton;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Widgets
        mShowCount = findViewById(R.id.count);
        mResetButton = findViewById(R.id.button_reset);

        mPreferences = getSharedPreferences(SharedPrefsApp.SHARED_PREF_FILE, MODE_PRIVATE);

        mColor = ContextCompat.getColor(this,
                R.color.default_background);

        mCount = mPreferences.getInt(SharedPrefsApp.COUNT_KEY, 0);
        mShowCount.setText(String.valueOf(mCount));
        mColor = mPreferences.getInt(SharedPrefsApp.COLOR_KEY, mColor);
        mShowCount.setBackgroundColor(mColor);
    }

    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        mShowCount.setBackgroundColor(color);
        mColor = color;
    }

    public void countUp(View view) {
        mCount++;
        mShowCount.setText(String.valueOf(mCount));
    }

    public void reset(View view) {
        // Reset count
        mCount = 0;
        mShowCount.setText(String.valueOf(mCount));

        // Reset color
        mColor = ContextCompat.getColor(this,
                R.color.default_background);
        mShowCount.setBackgroundColor(mColor);

        // Clear preferences
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void launchSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, SharedPrefsApp.SETTINGS_REQUEST);
    }

   /* @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(SharedPrefsApp.COUNT_KEY, mCount);
        editor.putInt(SharedPrefsApp.COLOR_KEY, mColor);
        editor.apply();
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SharedPrefsApp.SETTINGS_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                SharedPreferences.Editor editor = mPreferences.edit();
                String color = data.getStringExtra(SharedPrefsApp.COLOR_KEY);
                if (color != null) {
                    int colorResId;
                    if (getString(R.string.red).equals(color)) {
                        colorResId = R.color.red_background;
                    } else if (getString(R.string.blue).equals(color)) {
                        colorResId = R.color.blue_background;
                    } else if (getString(R.string.green).equals(color)) {
                        colorResId = R.color.green_background;
                    } else if (getString(R.string.black).equals(color)) {
                        colorResId = R.color.black_background;
                    } else {
                        colorResId = R.color.default_background;
                    }
                    editor.putInt(SharedPrefsApp.COLOR_KEY, getResources().getColor(colorResId));
                    mShowCount.setBackgroundColor(getResources().getColor(colorResId));
                    if (data.getBooleanExtra(SharedPrefsApp.HAS_COUNT, false)) {
                        mCount = data.getIntExtra(SharedPrefsApp.COUNT_KEY, 0);
                        editor.putInt(SharedPrefsApp.COUNT_KEY, mCount);
                        mShowCount.setText(String.valueOf(mCount));
                    }
                    editor.apply();
                }
            } else if (resultCode != RESULT_CANCELED) {
                mResetButton.performClick();
            }
        }
    }
}
