package com.github.deskid.focusreader.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.github.deskid.focusreader.db.entity.WebContentEntity;

import java.util.List;

@Dao
public abstract class WebContentDao {

    @Query("select * from tuguas where url = :url")
    public abstract LiveData<List<WebContentEntity>> query(String url);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(WebContentEntity entity);
}
