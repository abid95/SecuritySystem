package com.example.abid.securitysystem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotificationService extends Service {


    FirebaseDatabase database;
    DatabaseReference DatabaseNotificationReference;


    public String image1;
    public String image2;

    public Bitmap bitmap;

    public List<Bitmap> imageList;
//    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.c1);

    ImageMaker imageMaker=new ImageMaker();
    Resources res;
    int height;
    int width;



    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    int count=0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        res = this.getResources();
//
//        height = (int) res.getDimension(android.R.dimen.notification_large_icon_height);
//        width = (int) res.getDimension(android.R.dimen.notification_large_icon_width);


        database= FirebaseDatabase.getInstance();

        DatabaseNotificationReference=database.getReference("Notification");

        DatabaseNotificationReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addNotification();
                DatabaseNotificationReference.getRef().setValue(null);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    /*
    Method for building Notifications
     */
    public void addNotification() {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext())

                        .setSmallIcon(R.drawable.c1)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.logo))
                        .setContentTitle("Human Detected")
                        .setAutoCancel(true)
                        .setContentText("Sir, The system has detected a human");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}