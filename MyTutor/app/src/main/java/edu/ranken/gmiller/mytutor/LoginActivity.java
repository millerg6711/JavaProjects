package edu.ranken.gmiller.mytutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmailAddressInput;
    private EditText mPasswordInput;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get widgets
        mEmailAddressInput = findViewById(R.id.edit_email);
        mPasswordInput = findViewById(R.id.edit_password);
        mLoginBtn = findViewById(R.id.button_login);

        // Use EditorActionListener to list for DONE key being pressed on soft keyboard
        mPasswordInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mLoginBtn.performClick();
                return true;
            }
            return false;
        });
    }

    public void login(View view) {
        // Get input
        String email = mEmailAddressInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        // Validate
        if (!getString(R.string.admin_email).equals(email) || !getString(R.string.admin_password).equals(password)) {
            Snackbar.make(view, R.string.invalid_email_or_password_error_msg, Snackbar.LENGTH_SHORT).show();
        } else {
            // Start Home Activity
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
