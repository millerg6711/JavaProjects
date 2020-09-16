/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.phonenumberspinner;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app shows a mSpinner right next to a phone number field. The mSpinner lets
 * the user choose the type of phone number: Home, Work, Mobile, and Other.
 */
public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener,
                   TextView.OnEditorActionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Spinner mSpinner;
    private EditText mEditText;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the widgets
        mSpinner = findViewById(R.id.label_spinner);
        mEditText = findViewById(R.id.editText_main);
        mTextView = findViewById(R.id.text_phonelabel);

        // Listen for when the phone number changes
        mEditText.setOnEditorActionListener(this);

        // Listen for when an item is selected
        mSpinner.setOnItemSelectedListener(this);

        // Create ArrayAdapter using the string array and default
        // mSpinner layout.
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.labels_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the mSpinner.
        mSpinner.setAdapter(adapter);
    }

    /**
     * Retrieves the selected item in the mSpinner using getItemAtPosition,
     * and assigns it to mSpinnerLabel.
     *
     * @param adapterView   The adapter for the mSpinner, where the selection occurred.
     * @param view          The view within the adapterView that was clicked.
     * @param i             The position of the view in the adapter.
     * @param l             The row id of the item that is selected (not used here).
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "onItemSelected: " + i);
        showText();
    }

    /**
     * Logs the fact that nothing was selected in the mSpinner.
     *
     * @param adapterView   The adapter for the mSpinner, where the selection
     *                      should have occurred.
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG, "onNothingSelected: ");
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d(TAG, "onEditorAction: " + actionId);
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
            showText();
        }
        return false;
    }

    /**
     * Retrieves the text and mSpinner item and shows them in text_phonelabel.
     */
    public void showText() {
        // Assign to showString both the entered string and mSpinnerLabel.
        String message = (mEditText.getText().toString() + " - " + mSpinner.getSelectedItem());
        // Display a Toast message with showString
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        // Set the TextView to showString.
        mTextView.setText(message);
    }
}
