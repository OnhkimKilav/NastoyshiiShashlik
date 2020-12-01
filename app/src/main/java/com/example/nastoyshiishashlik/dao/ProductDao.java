package com.example.nastoyshiishashlik.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nastoyshiishashlik.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert (Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE id = :productId")
    Product getById(long productId);
}
