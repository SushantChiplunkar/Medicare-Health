package com.test.medicarehealth.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.test.medicarehealth.R;

import java.io.File;
import java.io.FileOutputStream;

public class ImageStorage {
    private static Context context;

    public ImageStorage(Context context) {
        this.context = context;
    }

    public static String saveToSdCard(Bitmap bitmap, String filename) {

        String stored = null;

        File sdcard = Environment.getExternalStorageDirectory();

        File folder = new File(sdcard.getAbsoluteFile(), ".images");
        //File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/Images/");//the dot makes this directory hidden to the user
        folder.mkdir();
        File file = new File(folder.getAbsoluteFile(), filename + ".jpg");
        if (file.exists())
            return stored;

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            stored = "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stored;
    }

    public static File getImage(String imagename) {
        File mediaImage = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

            mediaImage = new File(myDir.getPath() + "/.images/" + imagename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaImage;
    }

    /*public static File getImage(String imagename){
        File mediaImage = null;
        try {

            File myDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/Images/");
            if (!myDir.exists())
                return null;

            mediaImage = new File(myDir + imagename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediaImage;
    }*/

    public static boolean checkifImageExists(String imagename) {
        Bitmap b = null;
        File file = ImageStorage.getImage("/" + imagename + ".jpg");
        String path = file.getAbsolutePath();

        if (path != null)
            b = BitmapFactory.decodeFile(path);

        if (b == null || b.equals("")) {
            return false;
        }
        return true;
    }

    private String getImageFilePath() {
        File imageFolder = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/Images/");
        if (!imageFolder.mkdirs())
            imageFolder.mkdirs();
        return imageFolder.getAbsolutePath();
    }

}
