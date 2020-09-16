package edu.ranken.gmiller.chatterbox;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ChatterApp extends Application {
    private static final String TAG = ChatterApp.class.getSimpleName();

    public static final String API_BASE = "http://iwt.ranken.edu/ExampleWebServices";

    public static final String GET_MESSAGES_ENDPOINT = "/Chaterbox/GetMessages";
    public static final String POST_MESSAGE_ENDPOINT = "/Chaterbox/PostMessage";

    public static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    public static final int NOTIFICATION_ID = 0;

    public static final String MESSAGES_ACTION = "edu.ranken.gmiller.chatterbox.MESSAGES_ACTION";
    public static final String USERNAME_EXTRA = "edu.ranken.gmiller.chatterbox.USERNAME";
    public static final String MESSAGES_EXTRA = "edu.ranken.gmiller.chatterbox.MESSAGES";

    private NotificationManager mNotifyManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        createNotificationChannel();

        Log.i(TAG, "onCreate: App Started");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel =
                    new NotificationChannel(
                            PRIMARY_CHANNEL_ID,
                            getString(R.string.primary_channel),
                            NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(getString(R.string.primary_channel_desc));
            mNotifyManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(Message msg) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_headset);
        builder.setContentTitle(getString(R.string.new_message_format, msg.getUser()));
        builder.setContentText(msg.getMsg());
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setAutoCancel(true);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
    }
}
