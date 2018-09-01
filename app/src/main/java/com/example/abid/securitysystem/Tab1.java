package com.example.abid.securitysystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Tab1 extends Fragment implements MyAdapter.AddPersonListener {
    String [] headlines ={"Person Detected","Person Detected","Person Detected","Person Detected","Person Detected","Person Detected","Person Detected"};
    String [] details={"Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected","Hello Sir, A person has been detected"};
    int[] IMAGES={R.drawable.t1,R.drawable.t2,R.drawable.t3,R.drawable.t4,R.drawable.t5,R.drawable.t6,R.drawable.t4};

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference DatabaseImageDataReference;
    DatabaseReference DatabaseInfoDataReference;
    Context context;
    private ProgressDialog mprogress;



    public List<String> imageList;
    public List<String> objectList;
    public String image2;

    int updatedlistsize=0;
    private List<String> timeList;

    int count=0;
    ImageMaker imageMaker=new ImageMaker();



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.tab1,container,false);
        recyclerView=(RecyclerView) rootview.findViewById(R.id.my_recycler_view);


        context=this.getActivity();
        mprogress=new ProgressDialog(context);
//


        layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
//        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);


        Log.i("value","before firebase");


        database= FirebaseDatabase.getInstance();
//        DatabaseImageDataReference = database.getReference("Images");
        DatabaseInfoDataReference = database.getReference("Human");


        imageList =new ArrayList<>();
        timeList=new ArrayList<>();
        objectList=new ArrayList<>();

        mAdapter = new MyAdapter(imageList,timeList,objectList,this);
        recyclerView.setAdapter(mAdapter);

        mprogress.setMessage("Loading....");
        mprogress.show();

        DatabaseInfoDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count=0;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                imageList.removeAll(timeList);
                int i=0;

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    count++;
                    if(count >= dataSnapshot.getChildrenCount()){
                        //stop progress bar here

                        mprogress.dismiss();
                    }


                    //getting the data object as a model which is used to send data
                    // using RegisterServiceData Model class
//                    DataModel dataModel = snapshot.getValue(DataModel.class);

//
                    String object=snapshot.getValue().toString();




                    String [] splitObject=object.split(",");

                    String objectName=splitObject[1];


                    String [] nameOfObject=objectName.split("=");
                    Log.i("check",nameOfObject[1]);

                    objectList.add(nameOfObject[1]);

                    String [] secondSplittime=splitObject[0].split("=");
                    String getTime=secondSplittime[1];
                    String [] secondSplitImages=splitObject[2].split("=");
                    String firstPart=secondSplitImages[1];
                    String secondPart=secondSplitImages[2];
                    String thirdPart=secondSplitImages[3];
                    String url=firstPart+"="+secondPart+"="+thirdPart;
                    timeList.add(getTime);
                    imageList.add(url);
                    Log.i("time",object);
                    Log.i("time123",url);
//                    dialog.dismiss();
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


    public void startDialog(int position)
    {

//        final EditText editText_name;
//        final EditText editText_id;
//        final String person_name;
//        final String person_id;
        final ImageView imageView;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_person, null);


        imageView=view.findViewById(R.id.imageView);
        String url=imageList.get(position);
        Glide.with(context).load(url).into(imageView);
//        imageView.set
//        editText_name = view.findViewById(R.id.add_person_name);
//        editText_id = view.findViewById(R.id.add_person_id);

//        final View view = inflater.inflate(R.layout.add_person,null);
        builder.setView(view)
//                .setTitle("Add")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.create();

        builder.show();

    }

    @Override
    public void abc(int position) {
        startDialog(position);
    }
}
