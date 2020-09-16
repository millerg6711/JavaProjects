package edu.ranken.gmiller.nprtechnologynews;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Date;

public class TechNewsApp extends Application {

    private static final String TAG = TechNewsApp.class.getSimpleName();
    public static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    public static final int NOTIFICATION_ID = 0;
    public static final int JOB_ID = 0;
    public static final String MESSAGES_ACTION = "edu.ranken.gmiller.nprtechnologynews.MESSAGES_ACTION";
    public static final String MESSAGES_EXTRA = "messages";

    public static final String API_URL = "https://www.npr.org/feeds/1019/feed.json";

    private NotificationManager mNotifyManager;
    private JobScheduler mScheduler;
    private Date mLastUpdated;

    @Override
    public void onCreate() {
        super.onCreate();

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        createNotificationChannel();
        scheduleJob();

        Log.i(TAG, "onCreate: App Started");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel =
                    new NotificationChannel(
                            PRIMARY_CHANNEL_ID,
                            getString(R.string.primary_channel_name),
                            NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription(getString(R.string.primary_channel_desc));
            mNotifyManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(Article article) {
        Log.d(TAG, "Sending Notification");
        if (mLastUpdated != null && mLastUpdated.equals(article.getDatePublished())) {
            Log.d(TAG, "sendNotification: " + article.getDatePublished());
            return;
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_npr_logo);
        builder.setContentTitle(getString(R.string.new_npr_tech_news));
        builder.setContentText(article.getTitle());
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(article.getSummary()));
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        Uri uri = Uri.parse(article.getUrl());
        Intent contentIntent =
                new Intent(Intent.ACTION_VIEW, uri);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
        mLastUpdated = article.getDatePublished();
    }

    public void scheduleJob() {
        ComponentName serviceName =
                new ComponentName(getPackageName(), TechNewsService.class.getName());

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPeriodic(15 * 60 * 1000); // 15 mins
        mScheduler.schedule(builder.build());
        Log.d(TAG, "scheduleJob: Scheduling Job");
    }
}
