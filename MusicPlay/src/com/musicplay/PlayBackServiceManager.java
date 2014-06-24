package com.musicplay;

import android.content.Context;

import com.musicplay.service.IPlayBackService;

public class PlayBackServiceManager {
	public static IPlayBackService getPlayBackService(Context context) {
		MusicPlayApplication app = (MusicPlayApplication) context.getApplicationContext();
		IPlayBackService service = app.getPlayBackService();
		
		return service;
	}
}
