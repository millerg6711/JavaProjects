package edu.ranken.gmiller.hellosharedprefs;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private Spinner mSpinnerInput;
    private EditText mCountInput;

    private String mSelectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // get widgets
        mCountInput = findViewById(R.id.edit_number);
        mSpinnerInput = findViewById(R.id.spinner_color);

        // Create drop-down adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_dropdown_item);
        mSpinnerInput.setAdapter(adapter);
        mSpinnerInput.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectedColor = getResources().getStringArray(R.array.colors_array)[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mSelectedColor = getString(R.string.color_default);
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }

    public void saveSettings(View view) {
        Intent intent = new Intent();
        intent.putExtra(SharedPrefsApp.COLOR_KEY, mSelectedColor);

        if (!TextUtils.isEmpty(mCountInput.getText())){
            intent.putExtra(SharedPrefsApp.COUNT_KEY, Integer.parseInt(mCountInput.getText().toString()));
            intent.putExtra(SharedPrefsApp.HAS_COUNT, true);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    public void resetSettings(View view){
        setResult(RESULT_OK);
        finish();
    }
}
