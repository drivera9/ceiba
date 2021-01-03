package co.com.ceiba.mobile.pruebadeingreso.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MONO on 02/01/2021.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_USERS = "create table users (  id TEXT, name TEXT, username TEXT, email TEXT, address TEXT, phone TEXT, website TEXT, company TEXT );";
    private static final String CREATE_TABLE_POSTS = "create table posts (  id TEXT, userId TEXT, title TEXT, body TEXT );";
    private static final String DB_NAME = "TEST.DB";
    private static final int DB_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_POSTS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STUDENTS");
        onCreate(db);
    }
}
