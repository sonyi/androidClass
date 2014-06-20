package com.example.mybookstore.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mybookstore.data.DataContract.BookCatagoryContract;
import com.example.mybookstore.data.DataContract.BookContract;
import com.example.mybookstore.model.Books;
import com.example.mybookstore.model.BooksBrief;
import com.example.mybookstore.model.Catagory;

public class DataAccess {
	private SQLiteHelper mSQLiteHelper;

	public DataAccess(Context context) {
		mSQLiteHelper = new SQLiteHelper(context);
	}

	/**
	 * 查询数据库中图书分类信息
	 * 
	 * @return 一个ArrayList 或者 null
	 */
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
		c.close();
		db.close();
		return data;
	}

	/**
	 * 查询数据库中book的简化信息
	 * 
	 * @param catagoryId
	 *            (图书分类ID)
	 * @return 一个ArrayList 或者 null
	 */
	public ArrayList<BooksBrief> queryBooksBrief(long catagoryId,
			String bookName) {
		SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
		String[] columns = { BookContract._ID, BookContract.TITLE,
				BookContract.AUTHOR, BookContract.PRICE, BookContract.ART,BookContract.CATAGORY_ID};

		Cursor c = null;
		String selection = null;
		String[] seleArgs = null;
		if (bookName == null) {
			selection = BookContract.CATAGORY_ID + "= ?";
			seleArgs = new String[] { catagoryId + "" };
		}else{
			selection = BookContract.CATAGORY_ID + "= ? and " + BookContract.TITLE + " like '" + bookName + "%'";
			seleArgs = new String[] { catagoryId + ""};
		}
		c = db.query(BookContract.TABLE_NAME, columns, selection, seleArgs,
				null, null, null);
		ArrayList<BooksBrief> data = new ArrayList<BooksBrief>();
		while (c.moveToNext()) {
			BooksBrief b = new BooksBrief();
			b.setBook_id(c.getLong(c.getColumnIndexOrThrow(BookContract._ID)));
			b.setBookTitle(c.getString(c
					.getColumnIndexOrThrow(BookContract.TITLE)));
			b.setBookAuthor(c.getString(c
					.getColumnIndexOrThrow(BookContract.AUTHOR)));
			b.setBookPrice(c.getString(c
					.getColumnIndexOrThrow(BookContract.PRICE)));
			b.setBookArt(c.getString(c.getColumnIndexOrThrow(BookContract.ART)));
			b.setBook_CatagoryId(c.getLong(c.getColumnIndexOrThrow(BookContract.CATAGORY_ID)));
			data.add(b);
		}
		c.close();
		db.close();
		return data;
	}

	/**
	 * 根据图书ID，查询数据中图书的详细信息
	 * 
	 * @param bookId
	 *            图书ID
	 * @return 一个books对象 或者 null
	 */
	public Books queryBooks(long bookId) {
		SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
		String[] columns = { BookContract._ID, BookContract.TITLE,
				BookContract.AUTHOR, BookContract.CATAGORY_ID,
				BookContract.DATE, BookContract.PRICE, BookContract.PAGES,
				BookContract.ART, BookContract.DESCRIPTION };
		String selection = BookContract._ID + "=?";
		String[] seleArgs = new String[] { String.valueOf(bookId) };
		Cursor c = db.query(BookContract.TABLE_NAME, columns, selection,
				seleArgs, null, null, null);
		Books book = null;
		while (c.moveToNext()) {
			book = new Books();
			book.setBook_id(c.getLong(c.getColumnIndexOrThrow(BookContract._ID)));
			book.setBookTitle(c.getString(c
					.getColumnIndexOrThrow(BookContract.TITLE)));
			book.setBookAuthor(c.getString(c
					.getColumnIndexOrThrow(BookContract.AUTHOR)));
			book.setBook_CatagoryId(c.getLong(c
					.getColumnIndexOrThrow(BookContract.CATAGORY_ID)));
			book.setBookDate(c.getString(c
					.getColumnIndexOrThrow(BookContract.DATE)));
			book.setBookPrice(c.getString(c
					.getColumnIndexOrThrow(BookContract.PRICE)));
			book.setBookPages(c.getLong(c
					.getColumnIndexOrThrow(BookContract.PAGES)));
			book.setBookArt(c.getString(c
					.getColumnIndexOrThrow(BookContract.ART)));
			book.setBookDescription(c.getString(c
					.getColumnIndexOrThrow(BookContract.DESCRIPTION)));
			// data.add(b);
		}
		c.close();
		db.close();
		return book;
	}

	/**
	 * 根据图书ID，移除数据库中该图书信息
	 * 
	 * @param bookId
	 *            图书ID
	 * @return 移除成功返回1；失败返回0
	 */
	public int removeBook(long bookId) {
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		String whereClause = BookContract._ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(bookId) };
		int count = db.delete(BookContract.TABLE_NAME, whereClause, whereArgs);
		db.close();
		return count;
	}

	/**
	 * 更新图书信息
	 * 
	 * @param bookId
	 *            需要更新的图书ID
	 * @param book
	 *            更新的图书信息
	 * @return 成功：更新的行号，失败：0
	 */
	public int updateBooks(long bookId, Books book) {
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		String whereClause = BookContract._ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(bookId) };
		ContentValues values = new ContentValues();
		values.put(BookContract.TITLE, book.getBookTitle());
		values.put(BookContract.AUTHOR, book.getBookAuthor());
		values.put(BookContract.DATE, book.getBookDate());
		values.put(BookContract.PAGES, book.getBookPages());
		values.put(BookContract.PRICE, book.getBookPrice());
		values.put(BookContract.DESCRIPTION, book.getBookDescription());
		int count = db.update(BookContract.TABLE_NAME, values, whereClause,
				whereArgs);
		db.close();
		return count;
	}
}
