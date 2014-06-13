package com.example.mybookstore.data;

import java.io.File;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.example.mybookstore.data.DataContract.BookCatagoryContract;
import com.example.mybookstore.data.DataContract.BookContract;

public class AddBooks {
	private String BOOKS_DIR = "booksStore";

	public void initialData(SQLiteDatabase db) {
		ContentValues g1 = new ContentValues();
		g1.put(BookCatagoryContract.CATAGORY_NAME, "名著");

		ContentValues g2 = new ContentValues();
		g2.put(BookCatagoryContract.CATAGORY_NAME, "科技");

		db.insert(BookCatagoryContract.TABLE_NAME, "", g1);
		db.insert(BookCatagoryContract.TABLE_NAME, "", g2);

		ContentValues b1 = new ContentValues();
		b1.put(BookContract.TITLE, "老人与海");
		b1.put(BookContract.AUTHOR, "海明威");
		b1.put(BookContract.CATAGORY_ID, 1);
		b1.put(BookContract.PRICE, 30);
		b1.put(BookContract.ART, getBookAbsolutePath("img_book_01.jpg"));
		b1.put(BookContract.DATE, "1988-08-15");
		b1.put(BookContract.PAGES,500);
		b1.put(BookContract.DESCRIPTION,"老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书，老人与海是一本很好看的书");

		ContentValues b2 = new ContentValues();
		b2.put(BookContract.TITLE, "悲惨世界");
		b2.put(BookContract.AUTHOR, "雨果");
		b2.put(BookContract.CATAGORY_ID, 1);
		b2.put(BookContract.PRICE, 30);
		b2.put(BookContract.ART, getBookAbsolutePath("img_book_03.jpg"));
		b2.put(BookContract.DATE, "1956-01-15");
		b2.put(BookContract.PAGES,700);
		b2.put(BookContract.DESCRIPTION,"悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书，悲惨世界是一本很好看的书");


		ContentValues b3 = new ContentValues();
		b3.put(BookContract.TITLE, "时间简史");
		b3.put(BookContract.AUTHOR, "霍金");
		b3.put(BookContract.CATAGORY_ID, 2);
		b3.put(BookContract.PRICE, 20);
		b3.put(BookContract.ART, getBookAbsolutePath("img_book_02.jpg"));
		b3.put(BookContract.DATE, "1999-08-13");
		b3.put(BookContract.PAGES,2000);
		b3.put(BookContract.DESCRIPTION,"时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书，时间简史是一本很好看的书。");


		db.insert(BookContract.TABLE_NAME, "", b1);
		db.insert(BookContract.TABLE_NAME, "", b2);
		db.insert(BookContract.TABLE_NAME, "", b3);

		//db.close();
	}

	private String getBookAbsolutePath(String fileName) {
		File dir = new File(Environment.getExternalStorageDirectory(),
				BOOKS_DIR);
		File file = new File(dir, fileName);
		return file.getAbsolutePath();
	}
}
