package com.example.abid.securitysystem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageMaker {

    public ImageMaker() {
    }

    public Bitmap converter(String img1, String img2){
        try {
            byte[] b1= Base64.decode(img1,Base64.DEFAULT);
            byte[] b2= Base64.decode(img2,Base64.DEFAULT);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            byteArrayOutputStream.write(b1);
            byteArrayOutputStream.write(b2);

            byte [] image=byteArrayOutputStream.toByteArray();
            Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
            return bitmap;

        }
        catch (Exception e){
            return null;
        }
    }
} 
