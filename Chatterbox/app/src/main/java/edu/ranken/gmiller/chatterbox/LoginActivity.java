package edu.ranken.gmiller.chatterbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    private EditText mUsernameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameInput = findViewById(R.id.edit_username);
        mUsernameInput.setOnEditorActionListener(this);
    }

    public void onEnterChatroom(View view) {
        if (TextUtils.isEmpty(mUsernameInput.getText().toString().trim())) {
            Snackbar.make(view, getString(R.string.error_message_empty_username), Snackbar.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ChatroomActivity.class);
        intent.putExtra(ChatterApp.USERNAME_EXTRA, mUsernameInput.getText().toString());
        startActivity(intent);
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            onEnterChatroom(view);
            return true;
        }
        return false;
    }
}
