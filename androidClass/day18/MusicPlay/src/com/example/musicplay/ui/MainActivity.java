package com.example.musicplay.ui;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.musicplay.R;
import com.example.musicplay.data.AlbumDataAccess;
import com.example.musicplay.data.ContactsDataAccess;
import com.example.musicplay.data.MusicDataAccess;
import com.example.musicplay.model.Album;
import com.example.musicplay.model.Music;

public class MainActivity extends Activity {
	private ArrayList<Album> mAlbumArray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		mAlbumArray = (ArrayList<Album>) new AlbumDataAccess(this).getAllAlbumList();
//		for(Album al : mAlbumArray){
//			Log.i("music", al.toString());
//		}
		
//		ArrayList<Music> musicArray = (ArrayList<Music>) new MusicDataAccess(this).getAllMusic();
//		for(Music m : musicArray){
//			Log.i("music", m.toString());
//		}
		
		Map<String, String> contacts = new ContactsDataAccess().getAllCallRecords(this);
		Log.i("contact", contacts.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
