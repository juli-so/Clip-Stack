package com.catchingnow.tinyclipboards;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by heruoxin on 15/1/19.
 */
public class SyncJobService extends JobService {
    private final static String PACKAGE_NAME = "com.catchingnow.tinyclipboards";
    private final static String STORAGE_DATE = "com.catchingnow.tinyclipboards.storageDate";
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    private Storage db;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        preference = getSharedPreferences(PACKAGE_NAME, MODE_PRIVATE);
        float days = preference.getFloat(STORAGE_DATE, 15);
        db = new Storage(this.getBaseContext());
        db.deleteClipHistoryBefore(days);
        Log.v(PACKAGE_NAME,"Start JobScheduler, the days is"+days);
        Intent i = new Intent(this, CBWatcherService.class);
        this.startService(i);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
