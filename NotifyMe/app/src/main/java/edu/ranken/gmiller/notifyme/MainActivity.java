package edu.ranken.gmiller.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private static final String ACTION_UPDATE_NOTIFICATION =
            "edu.ranken.gmiller.notifyme.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_SEND_NOTIFICATION =
            "edu.ranken.gmiller.notifyme.ACTION_SEND_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION =
            "edu.ranken.gmiller.notifyme.ACTION_CANCEL_NOTIFICATION";
    private static final int NOTIFICATION_ID = 0;

    private enum State {
        INITIAL, SENT, UPDATED
    };

    private Button mNotifyBtn;
    private Button mUpdateBtn;
    private Button mCancelBtn;
    private NotificationManager mNotifyManager;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotifyBtn = findViewById(R.id.button_notify);
        mUpdateBtn = findViewById(R.id.button_update);
        mCancelBtn = findViewById(R.id.button_cancel);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (ACTION_SEND_NOTIFICATION.equals(action)) {
                    sendNotification();
                } else if (ACTION_UPDATE_NOTIFICATION.equals(action)) {
                    updateNotification();
                } else if (ACTION_CANCEL_NOTIFICATION.equals(action)) {
                    cancelNotification();
                }
            }
        };

        mNotifyBtn.setOnClickListener((View view) -> sendNotification());
        mUpdateBtn.setOnClickListener((View view) -> updateNotification());
        mCancelBtn.setOnClickListener((View view) -> cancelNotification());

        createNotificationChannel();
        setNotificationButtonState(State.INITIAL);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SEND_NOTIFICATION);
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setNotificationButtonState(State.INITIAL);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
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

    private NotificationCompat.Builder getNotificationBuilder() {
        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);

        notifyBuilder.setSmallIcon(R.drawable.ic_android);
        notifyBuilder.setContentTitle(getString(R.string.notification_title));
        notifyBuilder.setContentText(getString(R.string.notification_text));
        notifyBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notifyBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent contentPendingIntent =
                PendingIntent.getActivity(this, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifyBuilder.setContentIntent(contentPendingIntent);
        notifyBuilder.setAutoCancel(true);

        Intent deleteIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent deletePendingIntent =
                PendingIntent.getBroadcast(this, NOTIFICATION_ID, deleteIntent, PendingIntent.FLAG_ONE_SHOT);
        notifyBuilder.setDeleteIntent(deletePendingIntent);

        return notifyBuilder;
    }

    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder =
                getNotificationBuilder();

        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent =
                PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        notifyBuilder.addAction(R.drawable.ic_update, getString(R.string.update), updatePendingIntent);

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(State.SENT);
    }

    public void updateNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        Bitmap androidImg = BitmapFactory
                .decodeResource(getResources(), R.drawable.mascot_1);

        notifyBuilder.setContentTitle(getString(R.string.notification_title_updated));
        notifyBuilder.setContentText(getString(R.string.notification_text_updated));
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidImg));

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(State.UPDATED);
    }

    public void cancelNotification() {
        mNotifyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(State.INITIAL);
    }

    private void setNotificationButtonState(State state) {
        switch (state) {
            default:
            case INITIAL:
                mNotifyBtn.setEnabled(true);
                mUpdateBtn.setEnabled(false);
                mCancelBtn.setEnabled(false);
                break;
            case SENT:
                mNotifyBtn.setEnabled(false);
                mUpdateBtn.setEnabled(true);
                mCancelBtn.setEnabled(true);
                break;
            case UPDATED:
                mNotifyBtn.setEnabled(false);
                mUpdateBtn.setEnabled(false);
                mCancelBtn.setEnabled(true);
                break;
        }
    }
}
