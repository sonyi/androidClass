package com.mymusicplay.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mymusicplay.model.Lrc;
import com.mymusicplay.util.Const;

public class LrcDataAccess {
	private SqliteHelper mSQLiteHelper;

	public LrcDataAccess(Context context) {
		mSQLiteHelper = new SqliteHelper(context);
	}
	
	/**
	 * 添加一个Lrc信息到对应的数据库表
	 * 
	 * @param dept
	 * @return 返回-1表示插入失败或已经存在，否则表示新添加的行的id
	 */
	public long addLrc(Lrc lrc){
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
//		Lrc isExit = getLrcUrl(lrc.getMusicName());
//		if(isExit != null){
//			return -1;
//		}
		ContentValues cv = new ContentValues();
		cv.put(Const.MUSIC_NAME, lrc.getMusicName());
		cv.put(Const.LRC_URL,lrc.getLrcUrl());
		long rowId = db.insert(Const.LRC_TABLE, "", cv);
		db.close();
		//Log.i("row", rowId + "");
		return rowId;
	}
	
	/**
	 * 根据歌名获取Lrc实体类
	 * @param musicName
	 * @return
	 */
	public String getLrcUrl(String musicName) {
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();

		String[] columns = new String[] {Const.LRC_URL };
		String selection = Const.MUSIC_NAME + " = ?";
		String[] selectionArgs = new String[] {musicName};
		Cursor c = db.query(Const.LRC_TABLE, columns, selection, selectionArgs, null,
				null, null);
		String url = null;
		while (c.moveToNext()) {
			url = c.getString(c.getColumnIndexOrThrow(Const.LRC_URL));
			break;
		}
		c.close();
		db.close();
		return url;
	}
	
	/**
	 * 移除对应歌名的Lrc
	 * @param musicName
	 * @return
	 */
	public int removeLrc(String musicName){
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		String whereClause = Const.MUSIC_NAME + " = ?";
		String[] whereArgs = new String[] {musicName};
		int index = db.delete(Const.LRC_TABLE, whereClause, whereArgs);
		db.close();
		return index;
	}
}
