package edu.ranken.gmiller.simpleasynctask;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Long, String> {
    private final static int MAX_NUM_BOUND = 10;

    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;

    public SimpleAsyncTask(TextView textView, ProgressBar progressBar) {
        mTextView = new WeakReference<>(textView);
        mProgressBar = new WeakReference<>(progressBar);
    }

    @Override
    protected void onPreExecute() {
        mProgressBar.get().setMax(100);
    }

    @Override
    protected String doInBackground(Void... voids) {

        final Random r = new Random();
        final int n = r.nextInt(MAX_NUM_BOUND + 1);
        final int duration = n * 200; //

        // Store the start time
        final long startTime = SystemClock.elapsedRealtime();
        final long endTime = startTime + duration;

        try {
            while (SystemClock.elapsedRealtime() < endTime) {
                Thread.sleep(16);
                publishProgress((SystemClock.elapsedRealtime() - startTime) * 100 / duration);
            }
            return String.format("Awake at last after sleeping for %s milliseconds", duration);
        } catch (InterruptedException ex) {
            return "Interrupted Task!";
        }
    }

    protected void onProgressUpdate(Long... progress) {
        setProgressPercent(progress[0]);
    }

    private void setProgressPercent(Long progress) {
        mProgressBar.get().setProgress(progress.intValue());
    }

    @Override
    protected void onPostExecute(String s) {
        mTextView.get().setText(s);
    }
}
