package com.mymusicplay.lrc;

import java.io.File;
import java.util.ArrayList;

import android.util.Log;

public class GetLrcFromSdcardByMusicName {
	
	public String getLrcPath(String musicName){
		File file = new File("/sdcard/");
		searchFiles(file);
		for(String s : it){
			String mName = s.substring(s.lastIndexOf("/") + 1,s.length());
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
}
