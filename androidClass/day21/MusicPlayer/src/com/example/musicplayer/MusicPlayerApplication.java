package com.example.musicplayer;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.musicplayer.service.IPlayBackService;
import com.example.musicplayer.service.MusicPlayBackService;

public class MusicPlayerApplication extends Application {
	private IPlayBackService mPlayService;

	public IPlayBackService getPlayBackService() {
		return mPlayService;
	}

	@Override
	public void onCreate() {
		Intent intent = new Intent(this, MusicPlayBackService.class);
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
		super.onCreate();
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mPlayService = (IPlayBackService) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}
	};
}
