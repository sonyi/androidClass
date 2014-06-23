package com.example.musicplayer;

import android.content.Context;

import com.example.musicplayer.service.IPlayBackService;

public class PlayBackServiceManager {
	public static IPlayBackService getPlayBackService(Context context) {
		MusicPlayerApplication app = (MusicPlayerApplication) context
				.getApplicationContext();
		IPlayBackService service = app.getPlayBackService();
		return service;
	}
}
