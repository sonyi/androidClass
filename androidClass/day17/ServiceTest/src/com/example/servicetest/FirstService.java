package com.example.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FirstService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i("service", "onBind");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.i("service", "onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("service", "onStartCommand-----startId:" + startId + "===flags" + flags);
		doSth(startId);
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void doSth(int startId){
		Notification notification = new Notification();
		notification.tickerText = "������ʾ��";
		notification.icon = R.drawable.ic_launcher;
		
		Intent intent = new Intent(this,MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
		notification.setLatestEventInfo(this, "����", "������ʾ��Ϣ", pi);
		
		
//		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//		nm.notify(startId, notification);//��Ϊid��һ������ʾ���notification
		
		startForeground(startId, notification);//ֻ��ʾһ��notification
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("service", "onDestroy");
		
		super.onDestroy();
	}
}
