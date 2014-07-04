package com.mymusicplay.lrc;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.util.Log;

public class GetLrcFromSdcardByMusicName {

	@SuppressLint("SdCardPath")
	public String getLrcPath(String musicName) {
		File file = new File("/sdcard/");
		searchFiles(file);
		for (String s : it) {
			String mName = s.substring(s.lastIndexOf("/") + 1,
					s.lastIndexOf("."));
			Log.i("search", s + "\r\n" + mName);
			if (mName.equals(musicName)) {
				return s;
			}
		}
		return null;

	}

	ArrayList<String> it = new ArrayList<String>();;

	private void searchFiles(File f) { // 搜索sdcard中的所有lrc文件
		if (f != null) {//耗时操作，应该在工作线程操作
			File files[] = f.listFiles();
			if (files != null && files.length > 0) {
				for (File tempF : files) {
					if (tempF.isDirectory()) {
						searchFiles(tempF);
					} else {
						String path = tempF.getPath();
						String fpath = path.substring(
								path.lastIndexOf(".") + 1, path.length());
						if (fpath.equals("lrc")) {
							it.add(path);
						}
					}
				}
			}

		}

	}
}
