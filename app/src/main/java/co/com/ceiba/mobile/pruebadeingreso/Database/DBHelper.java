package co.com.ceiba.mobile.pruebadeingreso.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.Models.User;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;

/**
 * Created by MONO on 02/01/2021.
 */

public class DBHelper {

    private Context context;
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public DBHelper(Context c) {
        this.context = c;
    }

    public DBHelper open() throws SQLException {
        this.dbHelper = new SQLiteHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insert(ContentValues contentValue, String table) {
        this.database.insert(table, null, contentValue);
    }


    public Cursor get(String table) {
        Cursor cursor = this.database.query(table, new String[]{}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getById(String table, String column, String value) {
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + table + " WHERE " + column + " = '" + value + "'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getByLike(String table, String column, String value) {
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + table + " WHERE " + column + " LIKE '%" + value + "%'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
