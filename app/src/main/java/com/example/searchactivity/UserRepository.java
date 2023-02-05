package com.example.searchactivity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//select 할때만 쿼리 생성, 다른 DML은 어노테이션 걸기.

@Dao
public interface UserRepository {

    @Query("SELECT * FROM searchHistory")
    List<SearchHistory> findAll();

    @Query("SELECT * FROM searchHistory where id=:id")
    SearchHistory findById(int id);

    @Query("SELECT * FROM searchHistory where movieName=:movieName")
    SearchHistory findByName(String movieName);

    @Insert
    void insert(SearchHistory user);

    @Delete
    void delete(SearchHistory user); //내부에 값을 넣어서 삭제 가능(오버로딩)

    @Query("DELETE FROM searchHistory WHERE id = :id")
    abstract void deleteByUserId(long id);
}
