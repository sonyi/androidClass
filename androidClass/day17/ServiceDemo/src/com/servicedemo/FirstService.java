package com.servicedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FirstService extends Service {
	@Override
	public void onCreate() {
		Log.i("ServiceDemo", "onCreate()");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("ServiceDemo", "onStartCommand()" + " startId:" + startId);
		
		int oper = intent.getIntExtra("oper", 0);
		
		if (oper == 0) {
			doSth1(startId);
		}
		if (oper == 1) {
			doSth2(startId);
		}
		
		return super.onStartCommand(intent, flags, startId);
	}

	private void doSth1(int startId) {
		Notification notification = new Notification();
		notification.tickerText = "�ռ��˵���λ��";
		notification.icon = R.drawable.ic_action_send;
		
		Intent intent = new Intent(this,MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
		
		notification.setLatestEventInfo(this, "����", "�����ռ�λ����Ϣ", pi);
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// nm.notify(startId, notification);
		
		startForeground(startId, notification);
	}

	private void doSth2(int startId) {
		Log.i("ServiceDemo", "�ռ������������������ʷ��¼�����Ų�ˮ��");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		stopForeground(true);
		Log.i("ServiceDemo", "onDestroy()");
		super.onDestroy();
	}
}
