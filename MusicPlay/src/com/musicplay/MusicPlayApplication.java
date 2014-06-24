package com.musicplay;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.musicplay.service.IPlayBackService;
import com.musicplay.service.MusicPlayBackService;

public class MusicPlayApplication extends Application {
	private IPlayBackService mPlayBackService;
	
	
	public IPlayBackService getPlayBackService() {
		return mPlayBackService;
	}
	@Override
	public void onCreate() {
		Intent intent = new Intent(this,MusicPlayBackService.class);
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
		super.onCreate();
	}
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mPlayBackService = (IPlayBackService)service;
		}
	};
}
