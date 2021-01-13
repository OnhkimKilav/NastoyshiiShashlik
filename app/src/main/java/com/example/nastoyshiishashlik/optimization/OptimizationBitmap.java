package com.example.nastoyshiishashlik.optimization;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.nastoyshiishashlik.App;

import io.reactivex.Single;

public class OptimizationBitmap {

    public Single<Bitmap> optimizationBitmap(int idImage, int weight, int height){
        return Single.create(emitter -> {
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(App.getContext().getResources(), idImage, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, weight,  height);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            emitter.onSuccess(BitmapFactory.decodeResource(App.getContext().getResources(), idImage, options));
        });
    }

    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
