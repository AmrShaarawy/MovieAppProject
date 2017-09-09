package com.example.amr.movieappproject1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amr on 9/13/2016.
 */
public class DataBase extends SQLiteOpenHelper {
    private static final String databaseName = "MovieDataBase";
    private static final String table_name = "Movie_Table";
    private static final int database_version = 1;
    private static final String Title = "Title";
    private static final String Poster_path = "Poster_Path";
    private static final String Release_date = "Release_Date";
    private static final String Overview = "Overview";
    private static final String Vote_average = "Vote_Average";
    private static final String Id = "Id";
    private static final String res = "create table " + table_name + "(Id INTEGER PRIMARY KEY,Title text,Poster_Path text,Release_Date text,Overview text,Vote_Average text)";

    public DataBase(Context context) {
        super(context, databaseName, null, database_version);

    }

    @Override
    public void onCreate(SQLiteDatabase dp) {
        dp.execSQL(res);

    }

    @Override
    public void onUpgrade(SQLiteDatabase dp, int i, int i1) {
        dp.execSQL("DROP TABLE IF EXISTS" + table_name);
        onCreate(dp);
    }

    public boolean insertData(String id, String title, String poster_path, String release_date, String overview, String vote_average) {
        SQLiteDatabase dp = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Id, id);
        contentValues.put(Title, title);
        contentValues.put(Poster_path, poster_path);
        contentValues.put(Release_date, release_date);
        contentValues.put(Overview, overview);
        contentValues.put(Vote_average, vote_average);
        long result = dp.insert(table_name, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase dp = this.getWritableDatabase();
        Cursor res = dp.rawQuery("select * from " + table_name, null);
        return res;
    }
}


