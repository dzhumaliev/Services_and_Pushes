package com.example.servicesandpushes.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.servicesandpushes.R;
import com.example.servicesandpushes.TrackingService;

public class NotificationHelper {

    private static final String CHANNEL = "CHANNEL";
    public static final String ACTION_CLOSE = "ACTION_CLOSE" ;

    public static void createNotification(Context context, String desc) {
        createNotificationChannel(context);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, createNotificationBuilder(context, desc));
    }

    public static Notification createNotificationBuilder(Context context, String desc) {


        Intent deleteIntent = new Intent(context, TrackingService.class);
        deleteIntent.setAction(ACTION_CLOSE);

        PendingIntent snoozePendingIntent =
                PendingIntent.getService(context, 0, deleteIntent, 0);



        createNotificationChannel(context);
        return new NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(R.drawable.ic_audiotrack_black_24dp)
                .setContentTitle("Test title money")
                .setContentText(desc)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_close_black_24dp, "CLOSE", snoozePendingIntent)
                .build();
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name_channel";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL, name, importance);
            channel.setDescription(description);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}