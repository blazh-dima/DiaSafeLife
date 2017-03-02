package com.diasafenight.diasafenight.Helpers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.diasafenight.diasafenight.MainActivity;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementPeriod;
import com.diasafenight.diasafenight.R;

import org.joda.time.DateTime;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by D.Blazhevsky on 19.01.2017.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    Notification notification;


    @Override
    public void onReceive(Context context, Intent intent) {
        DbContext dbContext = new DbContext(context);
        if(intent.hasExtra("MeasurementPeriod"))
        {
            int mpId = intent.getIntExtra("MeasurementPeriod", 0);
            MeasurementPeriod p = dbContext.getMeasurementPeriodById(mpId);
            String title = context.getResources().getString(R.string.app_name);
            String ticker;
            String text = "";

            if(mpId == 1)
                text = context.getResources().getString(R.string.morning_notification);
            else if(mpId == 2)
                text = context.getResources().getString(R.string.midday_notification);
            else if(mpId == 3)
                text = context.getResources().getString(R.string.evening_notification);
            else if(mpId == 4)
                text = context.getResources().getString(R.string.beforeBed_notification);

            ticker = text;

            notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            Intent mIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            pendingIntent = PendingIntent.getActivity(context, mpId, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.logo_drop_72_transparent)
                    .setTicker(ticker)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentText(text)
                    .build();
            notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(mpId, notification);


            DateTime time = DateTime.now().plusDays(1).withTime(p.ReminderTime);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent = new Intent(context, AlarmReceiver.class); // AlarmReceiver = broadcast receiver
            alarmIntent.putExtra("MeasurementPeriod", p.Id);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(  context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);


            if (Build.VERSION.SDK_INT >= 23)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time.getMillis(), pendingIntent);

            else if (Build.VERSION.SDK_INT >= 19)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time.getMillis(), pendingIntent);

            else if (Build.VERSION.SDK_INT >= 16)
                alarmManager.set(AlarmManager.RTC_WAKEUP, time.getMillis(), pendingIntent);

        }


    }
}