package com.example.mybookstore.data;

import java.util.ArrayList;
import java.util.List;

import com.example.mybookstore.data.DataContract.BookCatagoryContract;
import com.example.mybookstore.data.DataContract.BookContract;
import com.example.mybookstore.model.Books;
import com.example.mybookstore.model.BooksBrief;
import com.example.mybookstore.model.Catagory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataAccess {
	private SQLiteHelper mSQLiteHelper;

	public DataAccess(Context context) {
		mSQLiteHelper = new SQLiteHelper(context);
	}

	public ArrayList<Catagory> queryCatagory() {
		SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
		String[] columns = { BookCatagoryContract._ID,
				BookCatagoryContract.CATAGORY_NAME };
		Cursor c = db.query(BookCatagoryContract.TABLE_NAME, columns, null,
				null, null, null, null);
		ArrayList<Catagory> data = null;
		while (c.moveToNext()) {
			if (data == null) {
				data = new ArrayList<Catagory>();
			}
			Catagory cat = new Catagory();
			cat.setCatagory_id(c.getLong(c
					.getColumnIndexOrThrow(BookCatagoryContract._ID)));
			cat.setCatagoryName(c.getString(c
					.getColumnIndexOrThrow(BookCatagoryContract.CATAGORY_NAME)));
			data.add(cat);

		}
		return data;
	}
	
	public ArrayList<BooksBrief> queryBooksBrief(long catagoryId){
		
		SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
		String[] columns = {BookContract._ID,BookContract.TITLE,BookContract.AUTHOR,
				BookContract.PRICE,BookContract.ART};
		String selection = BookContract.CATAGORY_ID + "= ?";
		String[] seleArgs = new String[]{catagoryId+""};
		Cursor c = db.query(BookContract.TABLE_NAME, columns, selection, seleArgs, null, null, null);
		ArrayList<BooksBrief> data = null;
		while (c.moveToNext()) {
			if (data == null) {
				data = new ArrayList<BooksBrief>();
			}
			BooksBrief b = new BooksBrief();
			b.setBook_id(c.getLong(c.getColumnIndexOrThrow(BookContract._ID)));
			b.setBookTitle(c.getString(c.getColumnIndexOrThrow(BookContract.TITLE)));
			b.setBookAuthor(c.getString(c.getColumnIndexOrThrow(BookContract.AUTHOR)));
			b.setBookPrice(c.getString(c.getColumnIndexOrThrow(BookContract.PRICE)));
			b.setBookArt(c.getString(c.getColumnIndexOrThrow(BookContract.ART)));
			data.add(b);
		}
		return data;
	}
	
	
	public ArrayList<Books> queryBooks(long catagoryId,String[] seleArgs){
		SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
		String[] columns = {BookContract._ID,BookContract.TITLE,BookContract.AUTHOR,
				BookContract.CATAGORY_ID,BookContract.DATE,BookContract.PRICE,
				BookContract.PAGES,BookContract.ART,BookContract.DESCRIPTION};
		String selection = BookContract.CATAGORY_ID + "= ? " + BookContract.TITLE + "= ? " + 
				BookContract.AUTHOR + "= ? " + BookContract.PRICE + "= ? " + BookContract.ART + "= ? ";
		//String[] seleArgs = new String[]{catagoryId+""};
		Cursor c = db.query(BookContract.TABLE_NAME, columns, selection, seleArgs, null, null, null);
		ArrayList<Books> data = null;
		while (c.moveToNext()) {
			if (data == null) {
				data = new ArrayList<Books>();
			}
			Books b = new Books();
			b.setBook_id(c.getLong(c.getColumnIndexOrThrow(BookContract._ID)));
			b.setBookTitle(c.getString(c.getColumnIndexOrThrow(BookContract.TITLE)));
			b.setBookAuthor(c.getString(c.getColumnIndexOrThrow(BookContract.AUTHOR)));
			b.setBook_CatagoryId(c.getLong(c.getColumnIndexOrThrow(BookContract.CATAGORY_ID)));
			b.setBookDate(c.getString(c.getColumnIndexOrThrow(BookContract.DATE)));
			b.setBookPrice(c.getString(c.getColumnIndexOrThrow(BookContract.PRICE)));
			b.setBookPages(c.getLong(c.getColumnIndexOrThrow(BookContract.PAGES)));
			b.setBookArt(c.getString(c.getColumnIndexOrThrow(BookContract.ART)));
			b.setBookDescription(c.getString(c.getColumnIndexOrThrow(BookContract.DESCRIPTION)));
			data.add(b);
		}
		
		return data;
		
	}

	
}
