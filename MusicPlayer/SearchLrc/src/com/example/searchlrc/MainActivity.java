package com.example.searchlrc;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getLrcPath("Let It Go.mp3");
	}
	
	
	public String getLrcPath(String musicName){
		File file = new File("/sdcard/");
		String musicLrcName = musicName.substring(0, musicName.indexOf("."));
		Log.i("search", musicLrcName);
		searchFiles(file);
		for(String s : it){
			String mName = s.substring(s.lastIndexOf("/") + 1,s.lastIndexOf("."));
			Log.i("search", s + "\r\n" + mName);
			if(mName.equals(musicName)){
				return s;
			}
		}
		return null;
		
	}
	
	ArrayList<String> it = new ArrayList<String>();;
	private void searchFiles(File f) { // 搜索sdcard中的所有MP3文件
		File files[] = f.listFiles();
		for (File tempF : files) {
			if (tempF.isDirectory()) {
				searchFiles(tempF);
			} else {
				String path = tempF.getPath();
				String fpath = path.substring(path.lastIndexOf(".") + 1,
						path.length());
				if (fpath.equals("lrc")) {
					it.add(path);
				}
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
