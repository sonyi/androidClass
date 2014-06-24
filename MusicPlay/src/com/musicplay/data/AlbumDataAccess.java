package com.musicplay.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;

import com.musicplay.model.Album;

public class AlbumDataAccess {
	private static Context mContext;

	public AlbumDataAccess(Context context) {
		mContext = context;
	}

	/**
	 * 获取设备中的所有专辑信息
	 */
	public static List<Album> getAlbumList() {
		// 指定数据类别的uri，这里指定的是外存储器上的专辑数据
		Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		// 指定要查询哪些列
		String[] projection = new String[] { Albums._ID, Albums.ALBUM,
				Albums.ALBUM_ART, Albums.ALBUM_KEY, Albums.ARTIST,
				Albums.NUMBER_OF_SONGS };

		ContentResolver cr = mContext.getContentResolver();
		Cursor c = cr.query(uri, projection, null, null, null);

		List<Album> albumList = new ArrayList<Album>();
		while (c.moveToNext()) {
			Album album = new Album();
			album.setId(c.getLong(c.getColumnIndexOrThrow(Albums._ID)));
			album.setAlbum(c.getString(c.getColumnIndexOrThrow(Albums.ALBUM)));
			album.setAlbumArt(c.getString(c
					.getColumnIndexOrThrow(Albums.ALBUM_ART)));
			album.setAlbumKey(c.getString(c
					.getColumnIndexOrThrow(Albums.ALBUM_KEY)));
			album.setArtist(c.getString(c.getColumnIndexOrThrow(Albums.ARTIST)));
			album.setNumbweOfSongs(c.getInt(c
					.getColumnIndexOrThrow(Albums.NUMBER_OF_SONGS)));

			albumList.add(album);
		}
		c.close();

		return albumList;
	}

	/**
	 * 获取设备中的所有专辑信息
	 * @return
	 */
	public String getAlbumArtByAlbumId(long albumID){
		Uri uri = Albums.EXTERNAL_CONTENT_URI;
		String[] projection = new String[]{Albums.ALBUM_ART};
		String[] selectionArgs = new String[]{albumID + ""};
		ContentResolver cr = mContext.getContentResolver();
		Cursor c = cr.query(uri, projection, Albums._ID + "=?", selectionArgs, null);
		List<Album> albumList = new ArrayList<Album>();
		String result = null;
		while(c.moveToNext()){
			result = c.getString(c.getColumnIndexOrThrow(Albums.ALBUM_ART));
		}
		c.close();
		return result;
	}
}
