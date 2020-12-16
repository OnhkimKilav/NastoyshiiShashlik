package com.example.nastoyshiishashlik.dao.roomDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nastoyshiishashlik.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE id = :id")
    Product getById(long id);

    @Query("SELECT * FROM product WHERE hit = 1")
    List<Product> getByHit();

    @Insert
    void insert(Product product);

    @Insert
    void insert(List<Product> products);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM product")
    void delete();

}
