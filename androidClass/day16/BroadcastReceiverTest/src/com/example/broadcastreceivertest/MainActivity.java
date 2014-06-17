package com.example.broadcastreceivertest;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String ACTION_NEXT = "com.example.broadcastreceivertest.NEXT";
	public static final String ACTION_RECEIVER = "com.example.broadcastreceivertest.BROAD";
	private FristReceiver mFristReceiver;
	private SecondReceiver mSecondReceiver;
	private NetworkStateChangeReceiver mNetworkStateChangeReceiver;
	private NextReceiver mNextReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ע��㲥������
		mFristReceiver = new FristReceiver();// 1.����ʵ��
		IntentFilter filter1 = new IntentFilter(ACTION_RECEIVER);
		filter1.setPriority(100);
		registerReceiver(mFristReceiver, filter1);

		mSecondReceiver = new SecondReceiver();
		IntentFilter filter2 = new IntentFilter(ACTION_RECEIVER);
		filter2.setPriority(101);
		registerReceiver(mSecondReceiver, filter2);

		Button btn = (Button) findViewById(R.id.btn_send_broad);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ACTION_RECEIVER);
				intent.putExtra("num", 5);
				sendOrderedBroadcast(intent, null);
				// sendBroadcast(intent);
			}
		});

		mNetworkStateChangeReceiver = new NetworkStateChangeReceiver();
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mNetworkStateChangeReceiver, filter);

		Button btnSendInfo = (Button) findViewById(R.id.btn_send_info);
		btnSendInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showNotification();
			}

		});
		
		mNextReceiver = new NextReceiver();
		IntentFilter filter3 = new IntentFilter(ACTION_NEXT);
		registerReceiver(mNextReceiver, filter3);
		
	}

	private void showNotification() {
		// TODO Auto-generated method stub
		// ������������pendingIntent
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

		RemoteViews remoteViews = getRemoteViews();
		
		
		Notification notification = new Notification(R.drawable.ic_launcher, "�����Զ���֪ͨ", 0);
		notification.contentIntent = pi;
		notification.contentView = remoteViews;
		
		
		// ����֪ͨ����
//		Notification notification = new NotificationCompat.Builder(this)
//				.setOngoing(false).setSmallIcon(R.drawable.ic_launcher)
//				.setTicker("�����Զ���֪ͨ").setContent(remoteViews)
//				.setContentIntent(pi).build();

		// ����֪ͨ����
		/*
		 * Notification notification = new NotificationCompat.Builder(this)
		 * .setOngoing(false).setSmallIcon(R.drawable.ic_launcher)
		 * .setTicker("�����Զ���֪ͨ").setContentTitle("֪ͨ--�Զ���")
		 * .setContentText("tongzhi").setContentIntent(pi).build();
		 */

		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(1, notification);
	}

	private RemoteViews getRemoteViews() {
		RemoteViews remoteViews = new RemoteViews(
				"com.example.broadcastreceivertest", R.layout.notification_item);
		remoteViews.setTextViewText(R.id.tv_noti_text, "֪ͨ-�Զ���");
		remoteViews.setImageViewResource(R.id.iv_noti_img, R.drawable.img_art);
		
		//���ð�ť�ĵ�����Ϊ�㲥
		Intent intent = new Intent(ACTION_NEXT);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.ib_noti_next, pi);
		
		return remoteViews;
	}
	
	private class NextReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "��һ��", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(mFristReceiver);
		unregisterReceiver(mSecondReceiver);
		unregisterReceiver(mNetworkStateChangeReceiver);
		unregisterReceiver(mNextReceiver);
		super.onDestroy();
	}

	private class NetworkStateChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm.getActiveNetworkInfo() == null) {
				Toast.makeText(MainActivity.this, "û����������", Toast.LENGTH_SHORT)
						.show();
			} else {

			}
		}
	}
}
