package edu.ranken.gmiller.magic8ball;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private static final String EIGHT_BALL_ENDPOINT =
            "http://iwt.ranken.edu/ExampleWebServices/Magic8Ball";
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    private TextView mRandomPhrase;
    private RequestQueue mRequestQueue;
    private NotificationManager mNotifyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRandomPhrase = findViewById(R.id.random_phrase);
        mRequestQueue = Volley.newRequestQueue(this);
        createNotificationChannel();
    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            PRIMARY_CHANNEL_ID,
                            getString(R.string.notification_channel_name),
                            NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setDescription(getString(R.string.notification_channel_desc));
            mNotifyManager.createNotificationChannel(channel);
        }
    }

    public void rollEightBall(View view) {
        StringRequest request = new StringRequest(
                Request.Method.GET, EIGHT_BALL_ENDPOINT,
                response -> {
                    mRandomPhrase.setText(response);

                    NotificationCompat.Builder notifyBuilder =
                            new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                                    .setContentTitle(response)
                                    .setSmallIcon(R.drawable.ic_game);
                    mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
                },
                error -> mRandomPhrase.setText(R.string.failed_connection_error_msg)
        );
        mRequestQueue.add(request);
    }
}
