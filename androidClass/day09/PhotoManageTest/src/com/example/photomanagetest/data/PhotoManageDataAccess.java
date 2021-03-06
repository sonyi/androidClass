package com.example.photomanagetest.data;

import java.util.ArrayList;
import java.util.List;

import com.example.photomanagetest.data.DataContract.PhoteDataContract;
import com.example.photomanagetest.model.PhotoInformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PhotoManageDataAccess {
	private SQLiteHelper mSQLiteHelper;
	public PhotoManageDataAccess(Context context){
		mSQLiteHelper = new SQLiteHelper(context);
	}
	
	public long insertPhoto(PhotoInformation phoInfo){
		ContentValues cv = new ContentValues();
		cv.put(PhoteDataContract.PHOTO_ID, phoInfo.getId());
		cv.put(PhoteDataContract.PHOTO_TITLE, phoInfo.getImgTitle());
		cv.put(PhoteDataContract.PHOTO_PATH, phoInfo.getImgResPath());
		cv.put(PhoteDataContract.PHOTO_TIME, phoInfo.getImgTime());
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		long rowId = db.insert(PhoteDataContract.TABLE_NAME, "", cv);
		db.close();
		return rowId;
	}
	
	public List<PhotoInformation> queryPhoto(){
		SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
		String[] columns = {PhoteDataContract.PHOTO_ID,PhoteDataContract.PHOTO_TITLE,
				PhoteDataContract.PHOTO_PATH,PhoteDataContract.PHOTO_TIME};
		Cursor c = db.query(PhoteDataContract.TABLE_NAME, columns, null, null, null, null, null);
		List<PhotoInformation> data = new ArrayList<PhotoInformation>();
		while(c.moveToNext()){
			PhotoInformation ph = new PhotoInformation();
			ph.setId(c.getInt(c.getColumnIndexOrThrow(PhoteDataContract.PHOTO_ID)));
			ph.setImgTitle(c.getString(c.getColumnIndexOrThrow(PhoteDataContract.PHOTO_TITLE)));
			ph.setImgResPath(c.getString(c.getColumnIndexOrThrow(PhoteDataContract.PHOTO_PATH)));
			ph.setImgTime(c.getString(c.getColumnIndexOrThrow(PhoteDataContract.PHOTO_TIME)));
			data.add(ph);
		}
		return data;
	}
}
