package com.acharya.habba2k18.Test;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWriteJsonFileUtils {
    Activity activity;
    Context context;

    public ReadWriteJsonFileUtils(Context context) {
        this.context = context;
    }

    public void createJsonFileData(String filename, String mJsonResponse) {
        try {
            File checkFile = new File(context.getApplicationInfo().dataDir + "/new_directory_name/");
            if (!checkFile.exists()) {
                checkFile.mkdir();
            }
            FileWriter file = new FileWriter(checkFile.getAbsolutePath() + "/" + filename);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readJsonFileData(String filename) {
        try {
            File f = new File(context.getApplicationInfo().dataDir + "/new_directory_name/" + filename);
            if (!f.exists()) {
             //   onNoResult();
                return null;
            }
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
     //   onNoResult();
        return null;
    }

    public void deleteFile() {
        File f = new File(context.getApplicationInfo().dataDir + "/new_directory_name/");
        File[] files = f.listFiles();
        for (File fInDir : files) {
            fInDir.delete();
        }
    }

    public void deleteFile(String fileName) {
        File f = new File(context.getApplicationInfo().dataDir + "/new_directory_name/" + fileName);
        if (f.exists()) {
            f.delete();
        }
    }
}