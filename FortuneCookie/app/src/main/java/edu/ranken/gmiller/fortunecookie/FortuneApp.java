package edu.ranken.gmiller.fortunecookie;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class FortuneApp extends Application {
    private static final String TAG = FortuneApp.class.getSimpleName();

    public static final String FORTUNE_COOKIE_ENDPOINT =
            "http://iwt.ranken.edu/ExampleWebServices/FortuneCookie/GetFortuneJSON";

    public static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    public static final int NOTIFICATION_ID = 0;
    public static final int JOB_ID = 0;

    public static final String NEW_FORTUNE_ACTION =
            "edu.ranken.gmiller.fortunecookie.NEW_FORTUNE_ACTION";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_LAST_UPDATED = "lastUpdated";

    private NotificationManager mNotifyManager;
    private JobScheduler mScheduler;
    private String mLastUpdated;

    @Override
    public void onCreate() {
        super.onCreate();

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        createNotificationChannel();
        scheduleJob();
        Log.i(TAG, "onCreate: App Started");
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel =
                    new NotificationChannel(PRIMARY_CHANNEL_ID,
                            getString(R.string.primary_channel_name),
                            NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(getString(R.string.primary_channel_desc));
            mNotifyManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(String message, String lastUpdated) {
        if (mLastUpdated != null && mLastUpdated.equals(lastUpdated)) {
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);

        builder.setSmallIcon(R.drawable.cookie);
        builder.setContentTitle(getString(R.string.new_fortune));
        builder.setContentText(message);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        Intent contentIntent =
                new Intent(this, MainActivity.class);
        PendingIntent pendingContentIntent =
                PendingIntent.getActivity(this, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingContentIntent);
        builder.setAutoCancel(true);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
        mLastUpdated = lastUpdated;
    }

    public void scheduleJob() {
        ComponentName serviceName =
                new ComponentName(getPackageName(), NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        /*builder.setMinimumLatency(3000);
        builder.setOverrideDeadline(5000);*/
        builder.setPeriodic(15 * 60 * 1000); // 15 mins
        builder.setPersisted(true);

        mScheduler.schedule(builder.build());
    }
}
