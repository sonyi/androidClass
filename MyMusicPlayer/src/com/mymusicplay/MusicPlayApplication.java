package com.mymusicplay;

import com.mymusicplay.server.IPlayBackService;
import com.mymusicplay.server.MusicPlayBackService;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MusicPlayApplication extends Application{
	private IPlayBackService mplayService;
	
	public IPlayBackService getPlayBackService(){
		return mplayService;
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
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mplayService = (IPlayBackService) service;
		}
	};
}
