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

	// ����������SQL���
	private static final String CREATE_TABLE_GATAGORY = "CREATE TABLE "
			+ BookCatagoryContract.TABLE_NAME + " ( "
			+ BookCatagoryContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ BookCatagoryContract.CATAGORY_NAME + " VARCHAR NOT NULL )";

	// ����ͼ����Ϣ���SQL���
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
		// 1.�������������������
		db.beginTransaction();
		try {
			// �ȴ�����
			db.execSQL(CREATE_TABLE_GATAGORY);
			db.execSQL(CREATE_TABLE_BOOK);
			
			//��ʼ������
			new AddBooks().initialData(db);
			// �����ִ����֮��һ��Ҫ���ø÷����������ִ�гɹ�����ʱ���ύ����
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
