package com.example.lr7.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageManager {

    private static final String TAG = "ImageManager";

    public static String saveImage(Context context, Bitmap bitmap) {
        String fileName = generateUniqueFileName();
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return fileName;
        } catch (IOException e) {
            Log.e(TAG, "Error saving an image file: " + e.getMessage());
            return null;
        }
    }

    public static String generateUniqueFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return "image_" + timeStamp + ".jpg";
    }

    public static Bitmap getImage(Context context, String fileName) {
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            return bitmap;
        } catch (IOException e) {
            Log.e(TAG, "Error loading the image file: " + e.getMessage());
            return null;
        }
    }
}
