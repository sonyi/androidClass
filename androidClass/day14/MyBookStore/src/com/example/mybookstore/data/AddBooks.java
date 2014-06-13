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
		g1.put(BookCatagoryContract.CATAGORY_NAME, "����");

		ContentValues g2 = new ContentValues();
		g2.put(BookCatagoryContract.CATAGORY_NAME, "�Ƽ�");

		db.insert(BookCatagoryContract.TABLE_NAME, "", g1);
		db.insert(BookCatagoryContract.TABLE_NAME, "", g2);

		ContentValues b1 = new ContentValues();
		b1.put(BookContract.TITLE, "�����뺣");
		b1.put(BookContract.AUTHOR, "������");
		b1.put(BookContract.CATAGORY_ID, 1);
		b1.put(BookContract.PRICE, 30);
		b1.put(BookContract.ART, getBookAbsolutePath("img_book_01.jpg"));
		b1.put(BookContract.DATE, "1988-08-15");
		b1.put(BookContract.PAGES,500);
		b1.put(BookContract.DESCRIPTION,"�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ����飬�����뺣��һ���ܺÿ�����");

		ContentValues b2 = new ContentValues();
		b2.put(BookContract.TITLE, "��������");
		b2.put(BookContract.AUTHOR, "���");
		b2.put(BookContract.CATAGORY_ID, 1);
		b2.put(BookContract.PRICE, 30);
		b2.put(BookContract.ART, getBookAbsolutePath("img_book_03.jpg"));
		b2.put(BookContract.DATE, "1956-01-15");
		b2.put(BookContract.PAGES,700);
		b2.put(BookContract.DESCRIPTION,"����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ����飬����������һ���ܺÿ�����");


		ContentValues b3 = new ContentValues();
		b3.put(BookContract.TITLE, "ʱ���ʷ");
		b3.put(BookContract.AUTHOR, "����");
		b3.put(BookContract.CATAGORY_ID, 2);
		b3.put(BookContract.PRICE, 20);
		b3.put(BookContract.ART, getBookAbsolutePath("img_book_02.jpg"));
		b3.put(BookContract.DATE, "1999-08-13");
		b3.put(BookContract.PAGES,2000);
		b3.put(BookContract.DESCRIPTION,"ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����飬ʱ���ʷ��һ���ܺÿ����顣");


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
