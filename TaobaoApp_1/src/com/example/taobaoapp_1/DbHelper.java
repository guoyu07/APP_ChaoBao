package com.example.taobaoapp_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "data";
	public static final String TABLE_NAME = "users_table";
	// unique identifier for each item
	public static final String C_ID = "_id";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final int VERSION = 1;

	// Create database using SQL statement
	private final String createDb = "create table if not exists " + TABLE_NAME + " ( " 
	+ C_ID + " integer primary key autoincrement, " 
    + EMAIL + " text, " 
	+ PASSWORD + " text); ";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create a new table in our database
		db.execSQL(createDb);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table" + TABLE_NAME);

	}

}
