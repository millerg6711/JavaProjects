package edu.ranken.gmiller.getwebpagesourcecode;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Spinner mSpinnerProtocols;
    private EditText mUrlInput;
    private TextView mHtmlPageSourceOutput;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);

        mSpinnerProtocols = findViewById(R.id.spinner_protocol_type);
        mUrlInput = findViewById(R.id.edit_url);
        mHtmlPageSourceOutput = findViewById(R.id.text_html_page_source);

        ArrayAdapter<CharSequence> adapter =
            ArrayAdapter.createFromResource(this, R.array.protocols, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerProtocols.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mQueue != null) {
            mQueue.stop();
        }
    }

    public void getPageSource(View view) {
        String url = mUrlInput.getText().toString();
        String protocol = mSpinnerProtocols.getSelectedItem().toString();

        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(protocol)) {
            Toast.makeText(this, R.string.enter_url_error_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        String uri = protocol + url;
        Log.d(LOG_TAG, "URI: " + uri);

        mQueue.add(
            new StringRequest(Request.Method.GET, uri,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE: " + response);
                    mHtmlPageSourceOutput.setText(response);
                },
                error -> {
                    mHtmlPageSourceOutput.setText(error.getMessage());
                })
        );
    }
}
