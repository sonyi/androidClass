package com.notificationdemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String ACTION_NEXT = "com.notificationdemo.NEXT";

	private NextReceiver mNextReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnNotify = (Button) findViewById(R.id.btn_notify);
		btnNotify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNotification();
			}
		});
		
		mNextReceiver = new NextReceiver();
		IntentFilter filter = new IntentFilter(ACTION_NEXT);
		registerReceiver(mNextReceiver, filter);
	}

	// private int iNotifyId = 0;

	private void showNotification() {
		// ������������PendingIntent
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

		// ����֪ͨ����
		Notification notification = new Notification(R.drawable.ic_action_send,
				"����֪ͨ", 0);
		notification.contentIntent = pi;
		// ����contentView
		notification.contentView = getNotifiRemoteViews();

		// ��ȡ֪ͨ���� ��֪ͨ��������
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// ����֪ͨ
		nm.notify(1, notification);
	}

	// ����֪ͨ��ͼ
	private RemoteViews getNotifiRemoteViews() {
		RemoteViews remoteViews = new RemoteViews("com.notificationdemo",
				R.layout.view_notification);
		// ����remoteViews�еĿؼ�����
		remoteViews.setTextViewText(R.id.tv_title, "diamonds");
		remoteViews.setImageViewResource(R.id.img_art, R.drawable.img_03);
		
		// ���ð�ť�ĵ�����Ϊ�㲥��ͼ��װ
		Intent intent = new Intent(ACTION_NEXT);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.btn_next, pi);
		
		return remoteViews;
	}
	
	private class NextReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(MainActivity.this, "��һ��������Diamonds", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(mNextReceiver);
		super.onDestroy();
	}
}
