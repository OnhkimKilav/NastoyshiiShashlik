package com.example.nastoyshiishashlik.dao;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class ProductDao {
    private List<Product> products = new ArrayList<>();

    public void createAndWorkDB(){
        SQLiteDatabase db = App.getContext().openOrCreateDatabase("app.db", App.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS products (id INTEGER, poster INTEGER, name TEXT, weight INTEGER, " +
                "price INTEGER, min_weight_for_order INTEGER, final_price INTEGER, dishes TEXT)");

        db.execSQL("DELETE FROM products");

        for(Product product:readProducts()){
            db.execSQL("INSERT INTO products " +
                    "(id, poster, name, weight, price, min_weight_for_order, final_price, dishes)" +
                    " VALUES " +
                    "("+product.getId()+", "+product.getPoster()+", '"+product.getName()+"', " +
                    ""+product.getWeight()+", "+product.getPrice()+", "+product.getMinWeightForOrder()+", " +
                    ""+product.getFinalPrice()+", '"+product.getDishes().getTitle()+"')");

            Log.d("Main activity", "createAndWorkDB: "+product.toString());
        }

        Cursor cursor = db.rawQuery("SELECT * FROM products;", null);
        ArrayList<String> array_list = new ArrayList<String>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            array_list.add(cursor.getString(cursor.getColumnIndex("id")));
            cursor.moveToNext();
        }
        Log.d("Main activity", "createAndWorkDB: " + array_list);
    }

    private List<Product> readProducts() {
        InputStream is = App.getContext().getResources().openRawResource(R.raw.product_db);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
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
                product.setId(Integer.parseInt(tokens[0]));

                if(tokens[1].length()>0)
                    product.setPoster(Integer.parseInt(tokens[1]));
                else product.setPoster(0);

                product.setName(tokens[2]);

                if(tokens[3].length()>0)
                    product.setWeight(Integer.parseInt(tokens[3]));
                else product.setWeight(0);

                if(tokens[4].length()>0)
                    product.setPrice(Integer.parseInt(tokens[4]));
                else product.setPrice(0);

                if(tokens[5].length()>0)
                    product.setMinWeightForOrder(Integer.parseInt(tokens[5]));
                else product.setMinWeightForOrder(0);

                if(tokens[6].length()>0)
                    product.setFinalPrice(Integer.parseInt(tokens[6]));
                else product.setFinalPrice(0);

                product.setDishes(Dishes.valueOf(tokens[7]));
                products.add(product);

                Log.d("MyActivity", "Just created: "+product);
            }
        }catch (IOException e){
            Log.wtf("MyActivity", "Error reading data file on line "+line, e);
            e.printStackTrace();
        }

        return products;
    }
}
