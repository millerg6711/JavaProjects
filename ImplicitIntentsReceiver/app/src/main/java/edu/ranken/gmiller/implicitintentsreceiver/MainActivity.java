package edu.ranken.gmiller.implicitintentsreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text_uri_message);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            String uriString = "URI: " + uri.toString();
            textView = findViewById(R.id.text_uri_message);
            textView.setText(uriString);
        }
    }
}
