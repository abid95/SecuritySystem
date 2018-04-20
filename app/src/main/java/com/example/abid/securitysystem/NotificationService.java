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
    DatabaseReference DatabaseNotificationImagesReference;


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
//        DatabaseNotificationImagesReference=database.getReference("NotificationImages");
//
//
//        DatabaseNotificationImagesReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//
//                imageList.removeAll(imageList);
//                int i=0;
//
//                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    //getting the data object as a model which is used to send data
//                    // using RegisterServiceData Model class
////                    DataModel dataModel = snapshot.getValue(DataModel.class);
////
//                    String object=snapshot.getValue().toString();
//                    String [] splitObject=object.split("=");
//
//                    if(count==0){
//                        image1=splitObject[1];
//                        Log.i("image","Image 1 is "+image1);
//                        count++;
//                    }
//                    else if (count==1) {
//                        image2 = splitObject[1];
//                        Log.i("image", "Image 2 is " + image2);
//                        count = 0;
//                        imageList.add(imageMaker.converter(image1,image2));
////                        timeList.add("hello");
//                    }
//
//
////                    imageList.add(object);
////                    Log.i("object",object);
////                    addNotification();
////                    i++;
////                    Log.i("value", "List size is"+(Integer.toString(imageList.size())));
////                    RegisterServiceData service=snapshot.getValue(RegisterServiceData.class);
//
//                }
////                mAdapter.notifyDataSetChanged();
////                updatedlistsize= imageList.size();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.i("value", "Failed to read value.", error.toException());
//            }
//        });
//
//



        DatabaseNotificationReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addNotification();
//                DatabaseNotificationReference.getRef().setValue(null);

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

    public void addNotification() {

//        Intent intent=new Intent(this,Tab1.class);
//
//        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext())

                        .setSmallIcon(R.drawable.c1)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_stat_noti))
                        .setContentTitle("Human Detected")
//                        .setContentIntent(pendingIntent)
                        .setContentText("Sir, The system has detected a human");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}