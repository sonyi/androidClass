package com.mymusicplay;

import android.content.Context;

import com.mymusicplay.server.IPlayBackService;

public class PlayBackServiceManager {
	public static IPlayBackService getPlayBackService(Context context){
		MusicPlayApplication app = (MusicPlayApplication) context.getApplicationContext();
		IPlayBackService service = app.getPlayBackService();
		return service;
	}
}
