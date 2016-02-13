package com.example.taobaoapp_1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity_Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		EditText user = (EditText) findViewById(R.id.login_edtId);
		EditText psw = (EditText) findViewById(R.id.login_edtPwd);

		final String username = user.getText().toString();
		final String password = psw.getText().toString();
		Button loginButton = (Button) findViewById(R.id.login_btnLogin);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				createData(username, password);

				Intent intent = new Intent();
				intent.setClass(MainActivity_Login.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

	
	public void createData(String username, String password) {

		SQLiteDatabase db = openOrCreateDatabase("taobao_USERS.db",
				Context.MODE_PRIVATE, null);
//		db.execSQL("DROP TABLE IF EXISTS person");

		db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, password VARCHAR)");
		System.out.println("new database is created");
		Person person = new Person();
		person.setUsername(username);
		person.setPassword(password);

		// 插入数据
		db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[] {
				person.getUsername(), person.getPassword() });

		System.out.println(username + " is inserted ");
		person.setUsername("david");
		person.setPassword("123");
		// ContentValues以键值对的形式存放数据
		ContentValues cv = new ContentValues();
		System.out.println("ContentValues is created");
		cv.put("username", person.getUsername());
		cv.put("password", person.getPassword());
		// 插入ContentValues中的数据
		db.insert("person", null, cv);

		cv = new ContentValues();
		cv.put("password", "aaa");
		// 更新数据
		db.update("person", cv, "username = ?", new String[] { "john" });

		System.out.println("update is success");
		Cursor c = db.rawQuery("SELECT * FROM person WHERE _id >= ?",
				new String[] { "1" });
		while (c.moveToNext()) {
			int _id = c.getInt(c.getColumnIndex("_id"));
			String name = c.getString(c.getColumnIndex("username"));
			String psw = c.getString(c.getColumnIndex("password"));
			System.out.println("查询：" + name + psw);
			Log.i("db", "_id=>" + _id + ", name=>" + name + ", psw=>" + psw);
		}

		System.out.println("查询完成");
		c.close();

		System.out.println("close");
		// 删除数据
		db.delete("person", "_id < ?", new String[] { "2" });
		System.out.println("delete id < 2");

		// 关闭当前数据库
		db.close();

		// 删除test.db数据库
		// deleteDatabase("test.db");

	}

}
