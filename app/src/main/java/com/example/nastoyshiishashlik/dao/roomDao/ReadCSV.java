package com.example.nastoyshiishashlik.dao.roomDao;

import android.util.Log;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.model.Dishes;
import com.example.nastoyshiishashlik.model.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {
    private final String TAG = ReadCSV.class.getName();

    public List<Product> readProducts() {
        List<Product> products = new ArrayList<>();
        InputStream is = App.getContext().getResources().openRawResource(R.raw.products);
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
                Product product = new Product();


                if(tokens[0].length()>0) {
                    int resId=App.getContext().getResources().getIdentifier(tokens[0], "drawable",
                            App.getContext().getPackageName());
                    product.setPoster(resId);
                }
                else product.setPoster(0);

                product.setName(tokens[1]);

                if(tokens[2].length()>0)
                    product.setWeight(Double.parseDouble(tokens[2]));
                else product.setWeight(0);

                if(tokens[3].length()>0)
                    product.setPrice(Integer.parseInt(tokens[3]));
                else product.setPrice(0);

                if(tokens[4].length()>0)
                    product.setMinWeightForOrder(Double.parseDouble(tokens[4]));
                else product.setMinWeightForOrder(0);

                if(tokens[5].length()>0)
                    product.setFinalPrice(Integer.parseInt(tokens[5]));
                else product.setFinalPrice(0);

                product.setDishes(Dishes.valueOf(tokens[6]));

                product.setHit(Boolean.parseBoolean(tokens[7]));

                product.setDescription(tokens[8]);

                products.add(product);

                Log.d(TAG, "Just created: "+product);
            }
        }catch (IOException e){
            Log.wtf(TAG, "Error reading data file on line "+line, e);
            e.printStackTrace();
        }

        return products;
    }
}
