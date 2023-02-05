package com.example.searchactivity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="movieName")
    public String movieName;


    public SearchHistory(String movieName){
        this.movieName = movieName;
    }

    public int getUid() {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }
}
