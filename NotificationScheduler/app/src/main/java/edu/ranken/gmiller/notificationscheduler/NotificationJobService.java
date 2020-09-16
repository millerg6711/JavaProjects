package edu.ranken.gmiller.notificationscheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationJobService extends JobService {
    private MyAsyncTask task = new MyAsyncTask();

    @Override
    public boolean onStartJob(JobParameters params) {
        task.execute(params);

        return true; // work has been offloaded
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // cancel the task so there are no memory leaks
        if (task != null) {
            task.cancel(true);
        }
        Toast.makeText(this, R.string.job_failed_msg, Toast.LENGTH_SHORT).show();
        return true; // reschedule at a later time
    }

    private NotificationCompat.Builder createNotificationBuilder() {
        PendingIntent contentPendingIntent =
                PendingIntent.getActivity(
                        this,
                        NotificationSchedulerApp.NOTIFICATION_ID,
                        new Intent(this, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, NotificationSchedulerApp.PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_job_running)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
    }

    private class MyAsyncTask extends AsyncTask<JobParameters, Void, Void> {
        private JobParameters mJobParams;

        @Override
        protected Void doInBackground(JobParameters... params) {
            mJobParams = params[0];

            // do job
            try {
                Thread.sleep(5 * 1000);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder notificationBuilder = createNotificationBuilder();
                manager.notify(NotificationSchedulerApp.NOTIFICATION_ID, notificationBuilder.build());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            jobFinished(mJobParams, true);
        }
    }
}
