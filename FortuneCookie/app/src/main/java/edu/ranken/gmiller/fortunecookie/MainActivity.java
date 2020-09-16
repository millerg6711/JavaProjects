package edu.ranken.gmiller.fortunecookie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private TextView mCurrentFortune;
    private TextView mLastUpdated;
    private RequestQueue mRequestQueue;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (FortuneApp.NEW_FORTUNE_ACTION.equals(action)) {
                try {
                    String message = intent.getStringExtra(FortuneApp.EXTRA_MESSAGE);
                    String lastUpdated = intent.getStringExtra(FortuneApp.EXTRA_LAST_UPDATED);
                    showFortune(message, lastUpdated);
                } catch (Exception e) {
                    mCurrentFortune.setText(e.getMessage());
                    mLastUpdated.setText(R.string.updated_just_now);
                    Log.e(TAG, "onReceive: New Fortune Broadcast", e);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentFortune = findViewById(R.id.current_fortune);
        mLastUpdated = findViewById(R.id.last_updated);
        mToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);

        mRequestQueue = Volley.newRequestQueue(this);

        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(mReceiver, new IntentFilter(FortuneApp.NEW_FORTUNE_ACTION));

    }

    public void refreshFortune(View view) {
        StringRequest request = new StringRequest(
                Request.Method.GET, FortuneApp.FORTUNE_COOKIE_ENDPOINT,
                (String response) -> {
                    try {
                        Gson gson = new Gson();
                        FortuneCookie cookie = gson.fromJson(response, FortuneCookie.class);
                        showFortune(cookie.fortune, cookie.lastUpdated);
                    } catch (Exception e) {
                        mCurrentFortune.setText(e.getMessage());
                        mLastUpdated.setText(R.string.updated_just_now);
                        Log.e(TAG, "Response Received: ", e);
                    }
                },
                (VolleyError error) -> {
                    mCurrentFortune.setText(error.getMessage());
                    mLastUpdated.setText(R.string.updated_just_now);
                    Toast.makeText(MainActivity.this, R.string.toast_error, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Response Received: ", error.getCause());
                });
        mRequestQueue.add(request);
    }

    public void showFortune(String message, String lastUpdated) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.US); // 2020-03-26 18:34:41 UTC
        Date lastUpdatedDate = format.parse(lastUpdated);
        long lastUpdatedMillis = lastUpdatedDate.getTime();
        long currentMillis = System.currentTimeMillis();
        long elapsedSeconds = (currentMillis - lastUpdatedMillis) / 1000L;

        mCurrentFortune.setText(message);
        mLastUpdated.setText(getString(R.string.last_updated_format, elapsedSeconds));
        Toast.makeText(MainActivity.this, R.string.toast_refreshed, Toast.LENGTH_SHORT).show();
    }
}
