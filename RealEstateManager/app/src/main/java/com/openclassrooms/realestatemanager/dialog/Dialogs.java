package com.openclassrooms.realestatemanager.dialog;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.ui.adapters.modify.PhotoListAdapter;

public class Dialogs {

    private NotificationManager notif;
    private Notification.Builder builder;

    public Dialogs() {
    }


    public void notificationUpload(Photo photo, Context context, int progress, int max){
        String message = "Adding " + photo.getDescription();
         builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.notif_icon)
                .setAutoCancel(false)
                .setOngoing(true)
                .setProgress(max, progress, false)
                .setContentTitle("Uploading ! ");
        builder.setStyle(new Notification.BigTextStyle()
                .bigText(message));
        notif = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notif.notify(1, notification);


    }

    public void updateNotificationUpload(String value){
        int progress = Integer.parseInt(value.split("/")[0]);
        int max =  Integer.parseInt(value.split("/")[1]);
        builder.setProgress(max, progress, false);
        Notification notification = builder.build();
        notif.notify(1, notification);
    }


    public void dismissUpload(){
        if (notif != null) {
            notif.cancel(1);
        }
    }

    public void sendNotification(House house, Context context){
        String message = "You added " + house.getName() + " with success";
        Notification.Builder builderSend = new Notification.Builder(context)
                .setSmallIcon(R.drawable.notif_icon)
                .setAutoCancel(true)
                .setContentTitle("Success ! ");
        builderSend.setStyle(new Notification.BigTextStyle()
                .bigText(message));

        NotificationManager notification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.notify(0, builderSend.build());
        if (PhotoListAdapter.getAllPhotos() != null) {
            PhotoListAdapter.getAllPhotos().clear();
        }
    }
}
