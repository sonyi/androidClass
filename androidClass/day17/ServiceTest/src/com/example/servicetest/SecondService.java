package com.example.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SecondService extends Service {
	int i = 120, j = 90;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i("service", "onBind");
		return new SecondServiceBinder();
	}

	private class SecondServiceBinder extends Binder implements ISecondService{
		
		@Override
		public double[] getPlaceLocation() {
			Notification notification = new Notification();
			notification.tickerText = "收集了地理位置";
			notification.icon = R.drawable.ic_launcher;

			Intent intent = new Intent(SecondService.this, MainActivity.class);
			PendingIntent pi = PendingIntent.getActivity(SecondService.this, 0, intent, 0);

			notification.setLatestEventInfo(SecondService.this, "标题", "正在收集位置信息", pi);
			
			startForeground(1, notification);
			return new double[]{i++,j++};
		}

		@Override
		public String getYourBrowseHistory() {
			// TODO Auto-generated method stub
			return "和谐了";
		}
		
	}
}
