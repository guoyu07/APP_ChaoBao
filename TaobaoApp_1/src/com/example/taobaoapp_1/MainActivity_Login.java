package com.example.taobaoapp_1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity_Login extends Activity {

	SQLiteDatabase db;
	DbHelper dbHelper;

	private EditText mEmailView;
	private EditText mPasswordView;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// setup database
		dbHelper = new DbHelper(this);
		db = dbHelper.getWritableDatabase();
		mEmailView = (EditText) findViewById(R.id.login_edtId);
		mPasswordView = (EditText) findViewById(R.id.login_edtPwd);
		list = (ListView) findViewById(R.id.usersList);

		// map table values to views in list_row
		String[] from = { DbHelper.EMAIL, DbHelper.PASSWORD };
		String[] column = { DbHelper.C_ID, DbHelper.EMAIL, DbHelper.PASSWORD };

		int[] to = { R.id.email, R.id.password };
		Cursor cursor = db.query(DbHelper.TABLE_NAME, column, null, null, null,
				null, null);
		// Should be using CursorLoader here along with ContentProvider
		// map data to list_row
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.list_row, cursor, from, to);

		// tell listView to use adapter
		list.setAdapter(adapter);

	}

	public void addValues(View v) {

		// get values from user
		String email, password;
		email = mEmailView.getText().toString();
		password = mPasswordView.getText().toString();

		// insert values into the database
		// package values to be entered into row using ContentValues object
		ContentValues cv = new ContentValues();
		cv.put(DbHelper.EMAIL, email);
		cv.put(DbHelper.PASSWORD, password);
		db.insert(DbHelper.TABLE_NAME, null, cv);

		Toast.makeText(getApplicationContext(), email + " 注册完成!",
				Toast.LENGTH_LONG).show();

		Intent intent = new Intent();
		intent.setClass(MainActivity_Login.this, MainActivity.class);
		startActivity(intent);
		finish();

	}

	String email1, password1;
	String email2, password2;

	public void queryDB(View v) {

		// Intent intent =new Intent(MainActivity.class,);

		mEmailView = (EditText) findViewById(R.id.login_edtId);
		mPasswordView = (EditText) findViewById(R.id.login_edtPwd);
		email1 = mEmailView.getText().toString();
		password1 = mPasswordView.getText().toString();

		String[] columns = { DbHelper.EMAIL, DbHelper.PASSWORD };
		Cursor cursor = db.query(DbHelper.TABLE_NAME, columns, null, null,
				null, null, null);
		cursor.moveToFirst();

		while (cursor.moveToNext()) {
			email2 = cursor.getString(cursor.getColumnIndex(DbHelper.EMAIL));
			password2 = cursor.getString(cursor
					.getColumnIndex(DbHelper.PASSWORD));

			if (email2.equals(email1) && password2.equals(password1)) {
				Toast.makeText(getApplicationContext(),
						"Email: " + email2 + "__ Psw:" + password2,
						Toast.LENGTH_SHORT).show();

				Intent intent = new Intent();
				intent.setClass(MainActivity_Login.this, MainActivity.class);
				startActivity(intent);

				break;
			}

		}

		// if (flag) {
		// Toast.makeText(getApplicationContext(),
		// "Email: " + email1 + "登陆成功", Toast.LENGTH_SHORT).show();
		// } else {
		//
		// Toast.makeText(getApplicationContext(), "用户名或者密码错误",
		// Toast.LENGTH_SHORT).show();
		// }

		cursor.close();
	}

}
