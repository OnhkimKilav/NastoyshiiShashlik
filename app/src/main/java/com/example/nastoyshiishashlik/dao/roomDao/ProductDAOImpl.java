package com.example.nastoyshiishashlik.dao.roomDao;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

public class ProductDAOImpl {
    private final AppDatabase db = App.getContext().getDatabase();
    private final ProductDao productDao = db.productDao();
    private final String TAG = ProductDAOImpl.class.getCanonicalName();

    public void insertProducts(){
        List<Product> products = readProducts();
        productDao.insert(products);

        for (Product product : products)
            Log.d(TAG, "insertProducts: " + product.toString());
    }

    public void deleteAll(){
        productDao.delete();
        Log.d(TAG, "deleteAll: delete all data from table: product");
    }

    public List<Product> getAll(){
        List<Product> products = productDao.getAll();
        //products.forEach(product -> Log.d(TAG, "getAll: " + product.toString()));
        for (Product product : products)
            Log.d(TAG, "getAll: " + product.toString());

        return products;
    }

    public Product getById(long id){
        Product product = productDao.getById(id);
        Log.d(TAG, "getById: " + product.toString());

        return product;
    }

    public List<Product> getByHit(){
        List<Product> products = productDao.getByHit();
        for (Product product : products)
            Log.d(TAG, "getByHit: " + product.toString());

        return products;
    }

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE product ADD COLUMN hit INTEGER DEFAULT 0 NOT NULL");
        }
    };

    private List<Product> readProducts() {
        List<Product> products = new ArrayList<>();
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

                product.setHit(Boolean.parseBoolean(tokens[8]));

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
