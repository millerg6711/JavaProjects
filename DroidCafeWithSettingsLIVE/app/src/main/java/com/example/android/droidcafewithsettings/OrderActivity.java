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

package com.example.android.droidcafewithsettings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity handles radio buttons for choosing a delivery method for an
 * order, and EditText input controls.
 */
public class OrderActivity extends AppCompatActivity {

    /**
     * Sets the content view to activity_order, and gets the intent and its
     * data.
     *
     * @param savedInstanceState Saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Get the intent and its data.
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.order_textview);
        textView.setText(message);

        DroidCafeApp app = (DroidCafeApp) getApplication();

        RadioButton radioBtn;
        switch (app.getDeliveryMethod()) {
            case "same_day":
                radioBtn = findViewById(R.id.sameday);
                radioBtn.setChecked(true);
                break;
            case "next_day":
                radioBtn = findViewById(R.id.nextday);
                radioBtn.setChecked(true);
            case "pick_up":
                radioBtn = findViewById(R.id.pickup);
                radioBtn.setChecked(true);
            default:
                // Do nothing
                break;
        }
    }

    /**
     * Checks which radio button was clicked and displays a toast message to
     * show the choice.
     *
     * @param view The radio button view.
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton)view).isChecked();
        if (checked) {
            // Check which radio button was clicked.
            switch (view.getId()) {
                case R.id.sameday:
                    // Same day service
                    displayToast(getString(R.string.same_day_messenger_service));
                    break;
                case R.id.nextday:
                    // Next day delivery
                    displayToast(getString(R.string.next_day_ground_delivery));
                    break;
                case R.id.pickup:
                    // Pick up
                    displayToast(getString(R.string.pick_up));
                    break;
                default:
                    // Do nothing
                    break;
            }
        }
    }

    /**
     * Displays the actual message in a toast message.
     *
     * @param message Message to display.
     */
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
