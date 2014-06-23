package com.example.musicplayer.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Audio.Media;

import com.example.musicplayer.model.Music;

public class MusicDataAccess {
	private Context mContext;

	public MusicDataAccess(Context context) {
		mContext = context;
	}

	/**
	 * 获取设备上的所有音乐信息
	 * 
	 * @return
	 */
	public List<Music> getAllMusic() {
		ContentResolver cr = mContext.getContentResolver();

		String[] projection = new String[] { Media._ID, Media.TITLE,
				Media.TITLE_KEY, Media.DISPLAY_NAME, Media.TRACK, Media.YEAR,
				Media.DATA, Media.DURATION, Media.BOOKMARK, Media.COMPOSER,
				Media.ALBUM_ID, Media.ALBUM, Media.ALBUM_KEY, Media.ARTIST_ID,
				Media.ARTIST, Media.ARTIST_KEY };
		String selection = Media.IS_MUSIC + "=?";
		String[] selectionArgs = new String[] { String.valueOf(1) };
		Cursor cursor = cr.query(Media.EXTERNAL_CONTENT_URI, projection,
				selection, selectionArgs, Media.DEFAULT_SORT_ORDER);

		List<Music> musicList = new ArrayList<Music>();
		while (cursor.moveToNext()) {
			Music music = new Music();
			music.setId(cursor.getLong(cursor.getColumnIndexOrThrow(Media._ID)));
			music.setTitle(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.TITLE)));
			music.setTitleKey(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.TITLE_KEY)));
			music.setDisplayName(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.DISPLAY_NAME)));
			music.setTrack(cursor.getInt(cursor
					.getColumnIndexOrThrow(Media.TRACK)));
			music.setYear(cursor.getInt(cursor
					.getColumnIndexOrThrow(Media.YEAR)));
			music.setData(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.DATA)));
			music.setDuration(cursor.getLong(cursor
					.getColumnIndexOrThrow(Media.DURATION)));
			music.setBookmark(cursor.getLong(cursor
					.getColumnIndexOrThrow(Media.BOOKMARK)));
			music.setComposer(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.COMPOSER)));
			music.setAlbumId(cursor.getInt(cursor
					.getColumnIndexOrThrow(Media.ALBUM_ID)));
			music.setAlbum(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.ALBUM)));
			music.setAlbumKey(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.ALBUM_KEY)));
			music.setArtistId(cursor.getInt(cursor
					.getColumnIndexOrThrow(Media.ARTIST_ID)));
			music.setArtist(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.ARTIST)));
			music.setArtistKey(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.ARTIST_KEY)));

			musicList.add(music);
		}
		cursor.close();
		return musicList;
	}

	/**
	 * 根据专辑id获取专辑里的所有曲目信息
	 * 
	 * @param albumId
	 * @return
	 */
	public List<Music> getMusicByAlbumId(long albumId) {
		ContentResolver cr = mContext.getContentResolver();

		String[] projection = new String[] { Media._ID, Media.TITLE,
				Media.TITLE_KEY, Media.DISPLAY_NAME, Media.TRACK, Media.YEAR,
				Media.DATA, Media.DURATION, Media.BOOKMARK, Media.COMPOSER,
				Media.ALBUM_ID, Media.ALBUM, Media.ALBUM_KEY, Media.ARTIST_ID,
				Media.ARTIST, Media.ARTIST_KEY };
		String selection = Media.IS_MUSIC + "=? and " + Media.ALBUM_ID + "=?";
		String[] selectionArgs = new String[] { String.valueOf(1),
				String.valueOf(albumId) };
		Cursor cursor = cr.query(Media.EXTERNAL_CONTENT_URI, projection,
				selection, selectionArgs, Media.TRACK);

		List<Music> musicList = new ArrayList<Music>();
		while (cursor.moveToNext()) {
			Music music = new Music();
			music.setId(cursor.getLong(cursor.getColumnIndexOrThrow(Media._ID)));
			music.setTitle(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.TITLE)));
			music.setTitleKey(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.TITLE_KEY)));
			music.setDisplayName(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.DISPLAY_NAME)));
			music.setTrack(cursor.getInt(cursor
					.getColumnIndexOrThrow(Media.TRACK)));
			music.setYear(cursor.getInt(cursor
					.getColumnIndexOrThrow(Media.TRACK)));
			music.setData(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.DATA)));
			music.setDuration(cursor.getLong(cursor
					.getColumnIndexOrThrow(Media.DURATION)));
			music.setBookmark(cursor.getLong(cursor
					.getColumnIndexOrThrow(Media.BOOKMARK)));
			music.setComposer(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.COMPOSER)));
			music.setAlbumId(cursor.getInt(cursor
					.getColumnIndexOrThrow(Media.ALBUM_ID)));
			music.setAlbum(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.ALBUM)));
			music.setAlbumKey(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.ALBUM_KEY)));
			music.setArtistId(cursor.getInt(cursor
					.getColumnIndexOrThrow(Media.ARTIST_ID)));
			music.setArtist(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.ARTIST)));
			music.setArtistKey(cursor.getString(cursor
					.getColumnIndexOrThrow(Media.ARTIST_KEY)));

			musicList.add(music);
		}
		cursor.close();
		return musicList;
	}
}
