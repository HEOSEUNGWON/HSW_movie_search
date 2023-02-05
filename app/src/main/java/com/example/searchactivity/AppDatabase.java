package com.example.searchactivity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SearchHistory.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserRepository userRepository();

}