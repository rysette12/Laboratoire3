package com.example.maryse.laboratoire3;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageHelper {

    public static File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File image = File.createTempFile(
                "JPEG_" + timeStamp + "_",
                ".jpg",
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        );

        return image;
    }

    public static void copyFile(Context context, Uri src, File dst) throws IOException
    {
        InputStream is = context.getContentResolver().openInputStream(src);
        OutputStream os = new FileOutputStream(dst);
        byte[] buff = new byte[1024];
        int len;
        while((len = is.read(buff)) > 0){
            os.write(buff, 0, len);
        }
        is.close();
        os.close();
    }
}
