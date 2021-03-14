package com.example.nastoyshiishashlik.dao.roomDao;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.models.Dishes;
import com.example.nastoyshiishashlik.models.ProductModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ReadCSV {
    private final String TAG = ReadCSV.class.getName();

    private static InputStream is;

    static {
        switch (App.getContext().getResources().getConfiguration().getLocales().get(0).getLanguage()){
            case "uk" :
                is = App.getContext().getResources().openRawResource(R.raw.products_ua);
                break;
            case "ru" :
                is = App.getContext().getResources().openRawResource(R.raw.products_ru);
                break;
        }
    }

    public List<ProductModel> readProducts() {
        List<ProductModel> productModels = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("windows-1251"))
        );

        String line = "";
        try {
            //Step over headers
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                //split by ';'
                String[] tokens = line.split(";");

                //Read the data
                ProductModel productModel = new ProductModel();


                if (tokens[0].length() > 0)
                    productModel.setID(Integer.parseInt(tokens[0]));
                else productModel.setID(0);

                if (tokens[1].length() > 0) {
                    int resId = App.getContext().getResources().getIdentifier(tokens[1], "drawable",
                            App.getContext().getPackageName());
                    productModel.setPoster(resId);
                } else productModel.setPoster(0);

                productModel.setName(tokens[2]);

                if (tokens[3].length() > 0)
                    productModel.setWeight(Double.parseDouble(tokens[3]));
                else productModel.setWeight(0);

                if (tokens[4].length() > 0)
                    productModel.setPrice(Integer.parseInt(tokens[4]));
                else productModel.setPrice(0);

                if (tokens[5].length() > 0)
                    productModel.setMinWeightForOrder(Double.parseDouble(tokens[5]));
                else productModel.setMinWeightForOrder(0);

                if (tokens[6].length() > 0)
                    productModel.setFinalPrice(Integer.parseInt(tokens[6]));
                else productModel.setFinalPrice(0);

                productModel.setDishes(Dishes.valueOf(tokens[7]));

                productModel.setHit(Boolean.parseBoolean(tokens[8]));

                productModel.setDescription(tokens[9]);

                productModel.setDishesName(tokens[10]);

                productModel.setSentences(tokens[11]);

                productModel.setQuantity(Integer.parseInt(tokens[12]));

                productModels.add(productModel);

                Log.d(TAG, "Just created: " + productModel);
            }
        } catch (IOException e) {
            Log.wtf(TAG, "Error reading data file on line " + line, e);
            e.printStackTrace();
        }

        return productModels;
    }

    public Single<List<String>> readStreets() {
        return Single.create(emitter -> {
            List<String> streetsList = new ArrayList<>();
            InputStream is = App.getContext().getResources().openRawResource(R.raw.streets);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("windows-1251"))
            );

            String line = "";
            try {
                //Step over headers
                reader.readLine();

                while ((line = reader.readLine()) != null) {
                    //split by ';'
                    String[] tokens = line.split("\n");

                    streetsList.add(tokens[0]);

                    Log.d(TAG, "Read " + tokens[0] + " from csv fail");
                }
            } catch (IOException e) {
                Log.wtf(TAG, "Error reading data file on line " + line, e);
                e.printStackTrace();
            }

            emitter.onSuccess(streetsList);

        });

    }
}
