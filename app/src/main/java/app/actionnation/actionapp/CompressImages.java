package app.actionnation.actionapp;

import android.content.Context;
import android.util.Log;

import java.io.File;


public class CompressImages {

    //    if it is more than max file size we will compress.
    /*public static final int MAX_FILE_SIZE = 100;

    public static String getCompressedImage(String filePath, Context ctx) {
        File originalFile = new File(filePath);
        double originalFileSize = getFileSizeKb(originalFile);
        String newFilePath = "";
        if (originalFileSize > MAX_FILE_SIZE) {
            File newFile = CompressHelper.getDefault(ctx)
                    .compressToFile(originalFile);
            newFilePath = newFile.getAbsolutePath();
            Log.d("fileSize", "Original File Size:" + getFileSizeKb(originalFile) + " kb");
            Log.d("fileSize", "Converted File Size:" + getFileSizeKb(newFile) + " kb");
            Log.d("fileSize", "Original file path:" + originalFile.getAbsolutePath());
            Log.d("fileSize", "Converted file path:" + newFile.getAbsolutePath());
        } else {
            newFilePath = filePath;
            Log.d("fileSize", "conversion not required for file size:" + getFileSizeKb(originalFile) + " kb");
        }
        return newFilePath;
    }

    public static long getFileSize(File file) {
        long size = 0;
        if (!file.isDirectory()) {
            return file.length();
        }
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size = size + getFileSize(files[i]);
            } else {
                size = size + files[i].length();
            }
        }
        return size;
    }

    public static double getFileSizeKb(File file) {
        return (double) file.length() / 1024;
    }

    public static double getFileSizeMb(File file) {
        return (double) file.length() / (1024 * 1024);
    }

*/
}
