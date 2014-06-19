package com.mybookcollection.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mybookcollection.model.BookBrief;
import com.mybookcollection.model.BookCatagory;
import com.mybookcollection.model.BookDetail;
import com.mybookcollection.provider.MetaDataContract.BookCatagoryContract;
import com.mybookcollection.provider.MetaDataContract.BookContract;

public class DataUtil {
	private Context mContext;

	public DataUtil(Context context) {
		mContext = context;
	}

	/**
	 * ��ȡͼ�������Ϣ
	 * 
	 * @return
	 */
	public List<BookCatagory> getBookCatagories() {
		SQLiteDatabase db = new SqliteHelper(mContext).getWritableDatabase();

		String[] columns = new String[] { BookCatagoryContract._ID,
				BookCatagoryContract.CATAGORY_NAME };

		List<BookCatagory> bookCatagoryList = new ArrayList<BookCatagory>();
		Cursor c = db.query(BookCatagoryContract.TABLE_NAME, columns, null,
				null, null, null, null);
		while (c.moveToNext()) {
			BookCatagory catagory = new BookCatagory();
			catagory.setId(c.getLong(c
					.getColumnIndexOrThrow(BookCatagoryContract._ID)));
			catagory.setCatagoryName(c.getString(c
					.getColumnIndexOrThrow(BookCatagoryContract.CATAGORY_NAME)));
			bookCatagoryList.add(catagory);
		}
		// �ر�Cursor�����SQLiteDatabase����
		c.close();
		db.close();

		return bookCatagoryList;
	}

	public List<BookBrief> getAllBookList() {
		SQLiteDatabase db = new SqliteHelper(mContext).getWritableDatabase();

		String[] columns = new String[] { BookContract._ID, BookContract.TITLE,
				BookContract.ART, BookContract.AUTHOR };

		Cursor c = db.query(BookContract.TABLE_NAME, columns, null, null, null,
				null, null);
		List<BookBrief> list = new ArrayList<BookBrief>();
		while (c.moveToNext()) {
			BookBrief book = new BookBrief();
			book.setId(c.getLong(c.getColumnIndexOrThrow(BookContract._ID)));
			book.setTitle(c.getString(c
					.getColumnIndexOrThrow(BookContract.TITLE)));
			book.setArt(c.getString(c.getColumnIndexOrThrow(BookContract.ART)));
			book.setAuthor(c.getString(c
					.getColumnIndexOrThrow(BookContract.AUTHOR)));
			list.add(book);
		}

		c.close();
		db.close();

		return list;
	}

	/**
	 * ����ͼ������ģ����ѯ
	 * 
	 * @return
	 */
	public List<BookBrief> searchBookListByTitle(String key) {
		SQLiteDatabase db = new SqliteHelper(mContext).getWritableDatabase();

		String[] columns = new String[] { BookContract._ID, BookContract.TITLE,
				BookContract.ART, BookContract.AUTHOR };
		String selection = BookContract.TITLE + "like ?%";
		String[] selectionArgs = new String[] { key };

		Cursor c = db.query(BookContract.TABLE_NAME, columns, selection,
				selectionArgs, null, null, null);
		List<BookBrief> list = new ArrayList<BookBrief>();
		while (c.moveToNext()) {
			BookBrief book = new BookBrief();
			book.setId(c.getLong(c.getColumnIndexOrThrow(BookContract._ID)));
			book.setTitle(c.getString(c
					.getColumnIndexOrThrow(BookContract.TITLE)));
			book.setArt(c.getString(c.getColumnIndexOrThrow(BookContract.ART)));
			book.setAuthor(c.getString(c
					.getColumnIndexOrThrow(BookContract.AUTHOR)));
			list.add(book);
		}

		c.close();
		db.close();

		return list;
	}

	/**
	 * ���ݷ���id��ȡ�÷�������е�ͼ����Ϣ����
	 * 
	 * @param cid
	 *            ͼ������id
	 * @return
	 */
	public List<BookBrief> getBookListByCatagoryId(long cid) {
		SQLiteDatabase db = new SqliteHelper(mContext).getWritableDatabase();

		String[] columns = new String[] { BookContract._ID, BookContract.TITLE,
				BookContract.ART, BookContract.AUTHOR };
		String selection = BookContract.CATAGORY_ID + "=?";
		String[] selectionArgs = new String[] { String.valueOf(cid) };

		Cursor c = db.query(BookContract.TABLE_NAME, columns, selection,
				selectionArgs, null, null, null);
		List<BookBrief> list = new ArrayList<BookBrief>();
		while (c.moveToNext()) {
			BookBrief book = new BookBrief();
			book.setId(c.getLong(c.getColumnIndexOrThrow(BookContract._ID)));
			book.setTitle(c.getString(c
					.getColumnIndexOrThrow(BookContract.TITLE)));
			book.setArt(c.getString(c.getColumnIndexOrThrow(BookContract.ART)));
			book.setAuthor(c.getString(c
					.getColumnIndexOrThrow(BookContract.AUTHOR)));
			list.add(book);
		}

		c.close();
		db.close();

		return list;
	}

	public BookDetail getBookDetailById(long bookId) {
		return null;
	}

	public int updateBook(BookDetail bookDetail) {
		return 0;
	}

	/**
	 * ����ͼ���idɾ��ͼ����Ϣ
	 * 
	 * @param mContext
	 * @param id
	 * @return
	 */
	public int removeBookById(long id) {
		SQLiteDatabase db = new SqliteHelper(mContext).getWritableDatabase();

		String whereClause = BookContract._ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(id) };
		int count = db.delete(BookContract.TABLE_NAME, whereClause, whereArgs);
		db.close();
		return count;
	}
}
