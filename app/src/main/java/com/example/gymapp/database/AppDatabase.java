package com.example.gymapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gymapp.model.Visitor;

@Database(
        entities = {
                Visitor.class,
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract VisitorDao visitorDao();
}