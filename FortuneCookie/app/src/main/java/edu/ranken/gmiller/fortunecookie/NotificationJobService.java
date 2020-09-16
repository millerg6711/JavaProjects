package edu.ranken.gmiller.fortunecookie;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class NotificationJobService extends JobService {
    private static final String TAG = NotificationJobService.class.getSimpleName();

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        StringRequest request = new StringRequest(
                Request.Method.GET, FortuneApp.FORTUNE_COOKIE_ENDPOINT,
                (String response) -> {
                    try {
                        Gson gson = new Gson();
                        FortuneCookie cookie = gson.fromJson(response, FortuneCookie.class);
                        Intent broadcastIntent = new Intent(FortuneApp.NEW_FORTUNE_ACTION);
                        broadcastIntent.putExtra(FortuneApp.EXTRA_MESSAGE, cookie.fortune);
                        broadcastIntent.putExtra(FortuneApp.EXTRA_LAST_UPDATED, cookie.lastUpdated);
                        LocalBroadcastManager
                                .getInstance(NotificationJobService.this)
                                .sendBroadcast(broadcastIntent);

                        FortuneApp app = (FortuneApp) getApplication();
                        app.sendNotification(cookie.fortune, cookie.lastUpdated);
                    } catch (Exception ex) {
                        Log.e(TAG, "onStartJob: Response Received", ex);
                    } finally {
                        jobFinished(params, false);
                    }
                }, (VolleyError error) -> {
                    Log.e(TAG, "onStartJob: Response Error", error.getCause());
                    jobFinished(params, false);
                });
        mRequestQueue.add(request);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mRequestQueue.stop();
        Log.e(TAG, "onStopJob: Job Stopped");
        return true;
    }
}
