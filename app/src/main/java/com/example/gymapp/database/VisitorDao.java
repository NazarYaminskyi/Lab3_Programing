package com.example.gymapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymapp.model.Visitor;

import java.util.List;

@Dao
public interface VisitorDao {

    @Insert
    void insert(Visitor visitor);

    @Update
    void update(Visitor visitor);

    @Delete
    void delete(Visitor visitor);

    @Query("SELECT * FROM visitors")
    List<Visitor> getAll();

    @Query("SELECT * FROM visitors WHERE id = :id")
    Visitor getById(long id);
}