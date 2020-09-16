package edu.ranken.gmiller.nprtechnologynews;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class TechNewsService extends JobService {

    private static final String TAG = TechNewsService.class.getSimpleName();

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: Job Started");

        GsonRequest<Feed> request = new GsonRequest<>(TechNewsApp.API_URL, Feed.class,
            (Feed feed) -> {
                try {
                    Intent broadcastIntent = new Intent(TechNewsApp.MESSAGES_ACTION);

                    ArrayList<Article> articles = feed.getArticles();
                    broadcastIntent.putExtra(TechNewsApp.MESSAGES_EXTRA, articles);

                    LocalBroadcastManager
                            .getInstance(TechNewsService.this)
                            .sendBroadcast(broadcastIntent);

                    TechNewsApp app = (TechNewsApp) getApplication();

                    Article article = feed.getArticles().get(0);
                    app.sendNotification(article);
                } catch (Exception ex) {
                    Log.e(TAG, "onStartJob: Response Received", ex);
                } finally {
                    jobFinished(params, false);
                }
            }, (VolleyError error) -> {
                Log.e(TAG, "onStartJob: Response Error", error);
                jobFinished(params, false);
            });
        mRequestQueue.add(request);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mRequestQueue.stop();
        Log.i(TAG, "onStopJob: Job Stopped");
        return true;
    }
}
