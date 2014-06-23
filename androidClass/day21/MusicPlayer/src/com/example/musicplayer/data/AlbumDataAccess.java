package com.example.musicplayer.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Albums;

import com.example.musicplayer.model.Album;

public class AlbumDataAccess {
	private Context mContext;

	public AlbumDataAccess(Context context) {
		mContext = context;
	}

	/**
	 * 获取设备中的所有专辑信息
	 * 
	 * @return
	 */
	public List<Album> getAllAlbumList() {
		// 指定数据类别的Uri,这里指定的是外存储器上的专辑数据
		Uri uri = Albums.EXTERNAL_CONTENT_URI;
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
			album.setNumberOfSongs(c.getInt(c
					.getColumnIndexOrThrow(Albums.NUMBER_OF_SONGS)));

			albumList.add(album);
		}
		c.close();

		return albumList;
	}
}
