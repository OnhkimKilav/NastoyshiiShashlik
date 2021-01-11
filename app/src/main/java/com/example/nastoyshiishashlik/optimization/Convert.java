package com.example.nastoyshiishashlik.optimization;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Convert {

    public static String fromBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return str;
    }

    public static byte[] fromBitmapToArrayByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static Bitmap fromStringToBitmap(String string){
        byte[] byteArray = Base64.decode(string, Base64.DEFAULT);
        Bitmap bmp1 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return Bitmap.createScaledBitmap(bmp1, 200, 100, false);
    }
}
