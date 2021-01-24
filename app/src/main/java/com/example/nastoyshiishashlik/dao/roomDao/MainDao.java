package com.example.nastoyshiishashlik.dao.roomDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nastoyshiishashlik.model.Product;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(Product product);

    //Delete query
    @Delete
    void delete (Product product);

    //Delete all query
    @Delete
    void reset(List<Product> mainData);

    //Update query
    /*@Query("UPDATE products SET poster = :newPoster, name = :newName," +
            "weight = :newWeight, price = :newPrice, min_weight_for_order = :newMinWeightForOrder," +
            "final_price = :newFinalPrice, dishes = :newDishes, hit = :newHit WHERE _id = :sID")*/
    @Query("UPDATE products SET name = :newName WHERE _id = :sID")
    void update(int sID, String newName);

    //Get all data query
    @Query("SELECT * FROM products")
    Single<List<Product>> getAll();

    //Get product by hit
    @Query("SELECT * FROM products WHERE hit = :iHit")
    Single<List<Product>> getByHit(int iHit);

    //Get product by dishes
    @Query("SELECT * FROM products WHERE dishes = :sDishes")
    Single<List<Product>> getByDishes(String sDishes);

    //Get all product from db by _ID
    @Query("SELECT * FROM products WHERE _id = :iId")
    Single<Product> getById(int iId);
}
