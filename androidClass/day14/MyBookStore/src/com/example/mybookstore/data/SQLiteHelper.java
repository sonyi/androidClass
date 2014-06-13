package com.example.mybookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mybookstore.data.DataContract.BookCatagoryContract;
import com.example.mybookstore.data.DataContract.BookContract;

public class SQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "BookCollection.db";
	private static final int DATABASE_VERSION = 1;
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

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



	@Override
	public void onCreate(SQLiteDatabase db) {
		// 1.调用这个方法开启事务
		db.beginTransaction();
		try {
			// 先创建表
			db.execSQL(CREATE_TABLE_GATAGORY);
			db.execSQL(CREATE_TABLE_BOOK);
			
			//初始化数据
			new AddBooks().initialData(db);
			// 最操作执行完之后一定要调用该方法标记事务执行成功，这时会提交更改
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		
		
	}

	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//
	}

}
