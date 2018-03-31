package com.ctse.automatedbirthdaywisher.background_task;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.ctse.automatedbirthdaywisher.R;
import com.ctse.automatedbirthdaywisher.SplashActivity;

/**
 * Created by Kasun on 3/30/2018.
 */

public class MsgJobSheduler extends JobService {

    private MsgJobExecuter jobExecuter;

    @Override
    public boolean onStartJob(final JobParameters params) {

        jobExecuter = new MsgJobExecuter(getApplicationContext()) {
            @Override
            protected void onPostExecute(String s) {
                if (!s.equals("")) {
                    showNotification(s);
                }
                jobFinished(params, false);
            }
        };
        jobExecuter.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobExecuter.cancel(true);
        return true;
    }

    public void showNotification(String names) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)//, "CHANNEL"
                .setSmallIcon(R.drawable.ic_blue_cake)
                .setContentTitle("Birthday Wisher")
                .setContentText("Your wish sent to " + names)
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, SplashActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(1, notification);
    }

    public void disapperIcon() {
        ((NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
    }

}
