package edu.ranken.gmiller.notificationscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RadioGroup mNetworkOptions;
    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;
    private SeekBar mSeekBar;
    private TextView mSeekBarProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNetworkOptions = findViewById(R.id.radio_group_network_options);
        mDeviceIdleSwitch = findViewById(R.id.switch_idle);
        mDeviceChargingSwitch = findViewById(R.id.switch_charging);
        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBarProgress = findViewById(R.id.progress_seek_bar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0) {
                    mSeekBarProgress.setText(getString(R.string.format_deadline_in_seconds, progress));
                } else {
                    mSeekBarProgress.setText(R.string.deadline_not_set);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void scheduleJob(View view) {
        JobScheduler mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        int networkType;
        switch(mNetworkOptions.getCheckedRadioButtonId()) {
            default:
            case R.id.radio_no_network:
                networkType = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.radio_any_network:
                networkType = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.radio_wifi_network:
                networkType = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        boolean requiresIdle = mDeviceIdleSwitch.isChecked();
        boolean requiresCharging = mDeviceChargingSwitch.isChecked();
        int deadline = mSeekBar.getProgress();
        boolean deadlineSet = deadline > 0;

        if (networkType != JobInfo.NETWORK_TYPE_NONE || requiresIdle || requiresCharging || deadlineSet) {

            ComponentName serviceName =
                    new ComponentName(getPackageName(), NotificationJobService.class.getName());

            JobInfo.Builder jobBuilder =
                    new JobInfo.Builder(NotificationSchedulerApp.JOB_ID, serviceName);
            jobBuilder.setRequiredNetworkType(networkType);
            jobBuilder.setRequiresDeviceIdle(requiresIdle);
            jobBuilder.setRequiresCharging(requiresCharging);

            if (deadlineSet) {
                jobBuilder.setOverrideDeadline(deadline * 1000L);
            }

            try {
                mScheduler.schedule(jobBuilder.build());
                Toast.makeText(this, R.string.schedule_job_toast, Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Log.e(TAG, "Failed to schedule job", ex);
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.job_constraint_not_set_toast, Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelJobs(View view) {
        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancelAll();

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();

        Toast.makeText(this, R.string.cancel_job_toast, Toast.LENGTH_SHORT).show();
    }
}
