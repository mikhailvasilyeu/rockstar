/*
 * Copyright (c) 2015, Xenero | Complete IT Solutions. All rights reserved.
 */

package com.elisoft.appstud.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.elisoft.appstud.app.App;
import com.elisoft.appstud.model.RockStar;
import com.elisoft.appstud.utils.SmartLog;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * @author phuc.tran
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "appstud.db";
    private static final String DATABASE_PATH = "/data/data/" + "com.elisoft.appstud" + "/databases/";
    private static final int DATABASE_VERSION = 1;

    private Dao<RockStar, Integer> rockStarDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_PATH + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RockStar.class);
        } catch (SQLException e) {
            SmartLog.error(DatabaseHelper.class, "Can't create database. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, RockStar.class, true);

            onCreate(db);
        } catch (SQLException e) {
            SmartLog.error(DatabaseHelper.class, "Can't drop databases. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Dao<RockStar, Integer> getRockStarDao() {
        if (rockStarDao == null) {
            try {
                rockStarDao = getDao(RockStar.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rockStarDao;
    }

    /**
     * Bookmark a rockstar
     * @param rockStar
     */
    public void bookmarkRockStar(RockStar rockStar) {
        try {
            getRockStarDao().create(rockStar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a rockstar from bookmark
     * @param rockStar
     */
    public void removeBookmarkedRockStar(RockStar rockStar) {
        try {
            getRockStarDao().delete(rockStar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isBookmarked(RockStar rockStar) {
        try {
            App.dbHelper.getRockStarDao()
                    .queryBuilder()
                    .where().eq("id", rockStar.id)
                    .queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() {
        super.close();
        rockStarDao = null;
    }
}