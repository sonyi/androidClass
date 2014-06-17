package com.servicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SecondService extends Service {
	@Override
	public void onCreate() {
		Log.i("ServiceDemo", "onCreate()");
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.i("ServiceDemo", "onBind()");
		return new SecondServiceBinder();
	}
	
	private class SecondServiceBinder extends Binder implements ISecondService {
		@Override
		public double[] getPlaceLocation() {
			Notification notification = new Notification();
			notification.tickerText = "收集了地理位置";
			notification.icon = R.drawable.ic_action_send;

			Intent intent = new Intent(SecondService.this, MainActivity.class);
			PendingIntent pi = PendingIntent.getActivity(SecondService.this, 0, intent, 0);

			notification.setLatestEventInfo(SecondService.this, "标题", "正在收集位置信息", pi);
			
			startForeground(1, notification);
			
			return new double[] {120,90};
		}
		
		@Override
		public String getYourBrowseHistory() {
			return "河蟹河蟹";
		}
	}
	
	@Override
	public void onDestroy() {
		Log.i("ServiceDemo", "onDestroy()");
		super.onDestroy();
	}
}
