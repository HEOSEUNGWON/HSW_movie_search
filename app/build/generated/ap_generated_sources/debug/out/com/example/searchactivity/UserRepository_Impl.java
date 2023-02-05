package com.example.searchactivity;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserRepository_Impl implements UserRepository {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SearchHistory> __insertionAdapterOfSearchHistory;

  private final EntityDeletionOrUpdateAdapter<SearchHistory> __deletionAdapterOfSearchHistory;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByUserId;

  public UserRepository_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSearchHistory = new EntityInsertionAdapter<SearchHistory>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `SearchHistory` (`id`,`movieName`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SearchHistory value) {
        stmt.bindLong(1, value.id);
        if (value.movieName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.movieName);
        }
      }
    };
    this.__deletionAdapterOfSearchHistory = new EntityDeletionOrUpdateAdapter<SearchHistory>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `SearchHistory` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SearchHistory value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__preparedStmtOfDeleteByUserId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM searchHistory WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final SearchHistory user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSearchHistory.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final SearchHistory user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSearchHistory.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteByUserId(final long id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByUserId.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByUserId.release(_stmt);
    }
  }

  @Override
  public List<SearchHistory> findAll() {
    final String _sql = "SELECT * FROM searchHistory";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfMovieName = CursorUtil.getColumnIndexOrThrow(_cursor, "movieName");
      final List<SearchHistory> _result = new ArrayList<SearchHistory>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SearchHistory _item;
        final String _tmpMovieName;
        if (_cursor.isNull(_cursorIndexOfMovieName)) {
          _tmpMovieName = null;
        } else {
          _tmpMovieName = _cursor.getString(_cursorIndexOfMovieName);
        }
        _item = new SearchHistory(_tmpMovieName);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public SearchHistory findById(final int id) {
    final String _sql = "SELECT * FROM searchHistory where id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfMovieName = CursorUtil.getColumnIndexOrThrow(_cursor, "movieName");
      final SearchHistory _result;
      if(_cursor.moveToFirst()) {
        final String _tmpMovieName;
        if (_cursor.isNull(_cursorIndexOfMovieName)) {
          _tmpMovieName = null;
        } else {
          _tmpMovieName = _cursor.getString(_cursorIndexOfMovieName);
        }
        _result = new SearchHistory(_tmpMovieName);
        _result.id = _cursor.getInt(_cursorIndexOfId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public SearchHistory findByName(final String movieName) {
    final String _sql = "SELECT * FROM searchHistory where movieName=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (movieName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, movieName);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfMovieName = CursorUtil.getColumnIndexOrThrow(_cursor, "movieName");
      final SearchHistory _result;
      if(_cursor.moveToFirst()) {
        final String _tmpMovieName;
        if (_cursor.isNull(_cursorIndexOfMovieName)) {
          _tmpMovieName = null;
        } else {
          _tmpMovieName = _cursor.getString(_cursorIndexOfMovieName);
        }
        _result = new SearchHistory(_tmpMovieName);
        _result.id = _cursor.getInt(_cursorIndexOfId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
