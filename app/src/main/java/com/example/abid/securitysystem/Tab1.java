package com.example.abid.securitysystem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Tab1 extends Fragment {
    String [] headlines ={"Person Detected","Person Detected","Person Detected","Person Detected","Person Detected","Person Detected","Person Detected"};
    String [] details={"Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected"};
    int[] IMAGES={R.drawable.t1,R.drawable.t2,R.drawable.t3,R.drawable.t4,R.drawable.t5,R.drawable.t6,R.drawable.t4};

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference DatabaseImageDataReference;
    DatabaseReference DatabaseInfoDataReference;


    public List<Bitmap> imageList;
    public String image1;
    public String image2;

    int updatedlistsize=0;
    private List<String> timeList;

    int count=0;
    ImageMaker imageMaker=new ImageMaker();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.tab1,container,false);
        recyclerView=(RecyclerView) rootview.findViewById(R.id.my_recycler_view);


//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle(textTitle)
//                .setContentText(textContent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//


        layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
//        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);


        Log.i("value","before firebase");


        database= FirebaseDatabase.getInstance();
        DatabaseImageDataReference = database.getReference("Images");
        DatabaseInfoDataReference = database.getReference("Human");


        imageList =new ArrayList<>();
        timeList=new ArrayList<>();

        mAdapter = new MyAdapter(imageList,timeList);
        recyclerView.setAdapter(mAdapter);



        DatabaseImageDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                imageList.removeAll(imageList);
                int i=0;

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    //getting the data object as a model which is used to send data
                    // using RegisterServiceData Model class
//                    DataModel dataModel = snapshot.getValue(DataModel.class);
//
                    String object=snapshot.getValue().toString();
                    String [] splitObject=object.split("=");

                    if(count==0){
                        image1=splitObject[1];
                        Log.i("image","Image 1 is "+image1);
                        count++;
                    }
                    else if (count==1) {
                        image2 = splitObject[1];
                        Log.i("image", "Image 2 is " + image2);
                        count = 0;
                        imageList.add(imageMaker.converter(image1,image2));
//                        timeList.add("hello");
                    }


//                    imageList.add(object);
//                    Log.i("object",object);
//                    addNotification();
//                    i++;
//                    Log.i("value", "List size is"+(Integer.toString(imageList.size())));
//                    RegisterServiceData service=snapshot.getValue(RegisterServiceData.class);

                }
                mAdapter.notifyDataSetChanged();
                updatedlistsize= imageList.size();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("value", "Failed to read value.", error.toException());
            }
        });


        DatabaseInfoDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                imageList.removeAll(timeList);
                int i=0;

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    //getting the data object as a model which is used to send data
                    // using RegisterServiceData Model class
//                    DataModel dataModel = snapshot.getValue(DataModel.class);
//
                    String object=snapshot.getValue().toString();
                    String [] splitObject=object.split("=");
                    String [] getTime=splitObject[1].split("\\}");
                    timeList.add(getTime[0]);
                    Log.i("time",getTime[0]);
                }
                mAdapter.notifyDataSetChanged();
                updatedlistsize= imageList.size();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("value", "Failed to read value.", error.toException());
            }
        });




        return rootview;
    }



}
