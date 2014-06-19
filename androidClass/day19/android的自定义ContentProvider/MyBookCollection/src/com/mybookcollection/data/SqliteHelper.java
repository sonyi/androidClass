package com.mybookcollection.data;

import com.mybookcollection.provider.MetaDataContract.BookCatagoryContract;
import com.mybookcollection.provider.MetaDataContract.BookContract;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "BookCollection.db";
	private static final int DATABASE_VERSION = 1;

	// 创建分类表的SQL语句
	private static final String CREATE_TABLE_GATAGORY = "CREATE TABLE "
			+ BookCatagoryContract.TABLE_NAME + " ( "
			+ BookCatagoryContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ BookCatagoryContract.CATAGORY_NAME + " VARCHAR NOT NULL )";

	// 创建图书信息表的SQL语句
	private static final String CREATE_TABLE_BOOK = "CREATE TABLE "
			+ BookContract.TABLE_NAME + " ( " + BookContract._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + BookContract.TITLE
			+ " VARCHAR NOT NULL, " + BookContract.AUTHOR
			+ " VARCHAR NOT NULL, " + BookContract.CATAGORY_ID
			+ " INTEGER NOT NULL, " + BookContract.DATE + " VARCHAR NULL, "
			+ BookContract.PRICE + " REAL NOT NULL, " + BookContract.PAGES
			+ " INTEGER NULL, " + BookContract.ART + " VARCHAR NOT NULL,"
			+ BookContract.DESCRIPTION + " VARCHAR NULL )";

	public SqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 1.调用这个方法开启事务
		db.beginTransaction();
		try {
			// 先创建表
			db.execSQL(CREATE_TABLE_GATAGORY);
			db.execSQL(CREATE_TABLE_BOOK);
			// 添加一些测试数据
			initialData(db);
			
			// 最操作执行完之后一定要调用该方法标记事务执行成功，这时会提交更改
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	private void initialData(SQLiteDatabase db) {
		ContentValues g1 = new ContentValues();
		g1.put(BookCatagoryContract.CATAGORY_NAME, "名著");

		ContentValues g2 = new ContentValues();
		g2.put(BookCatagoryContract.CATAGORY_NAME, "科技");

		db.insert(BookCatagoryContract.TABLE_NAME, "", g1);
		db.insert(BookCatagoryContract.TABLE_NAME, "", g2);

		ContentValues b1 = new ContentValues();
		b1.put(BookContract.TITLE, "Lao Ren Yu Hai");
		b1.put(BookContract.AUTHOR, "海明威");
		b1.put(BookContract.CATAGORY_ID, 1);
		b1.put(BookContract.PRICE, 30);
		b1.put(BookContract.ART, "lryh.jpg");

		ContentValues b2 = new ContentValues();
		b2.put(BookContract.TITLE, "Bei Can Shi Jie");
		b2.put(BookContract.AUTHOR, "雨果");
		b2.put(BookContract.CATAGORY_ID, 1);
		b2.put(BookContract.PRICE, 30);
		b2.put(BookContract.ART, "bcsj.jpg");

		ContentValues b3 = new ContentValues();
		b3.put(BookContract.TITLE, "Shi Jian Jian Shi");
		b3.put(BookContract.AUTHOR, "霍金");
		b3.put(BookContract.CATAGORY_ID, 2);
		b3.put(BookContract.PRICE, 20);
		b3.put(BookContract.ART, "xwz.jpg");

		db.insert(BookContract.TABLE_NAME, "", b1);
		db.insert(BookContract.TABLE_NAME, "", b2);
		db.insert(BookContract.TABLE_NAME, "", b3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//
	}

}
