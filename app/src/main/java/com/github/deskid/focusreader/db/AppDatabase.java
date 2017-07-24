package com.github.deskid.focusreader.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.github.deskid.focusreader.db.dao.ArticleDao;
import com.github.deskid.focusreader.db.dao.WebContentDao;
import com.github.deskid.focusreader.db.dao.YituDao;
import com.github.deskid.focusreader.db.entity.ArticleEntity;
import com.github.deskid.focusreader.db.entity.WebContentEntity;
import com.github.deskid.focusreader.db.entity.YituEntity;

@Database(entities = {ArticleEntity.class, WebContentEntity.class, YituEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "read_main_db";

    public abstract ArticleDao articleDao();

    public abstract WebContentDao webContentDao();

    public abstract YituDao yituDao();
}