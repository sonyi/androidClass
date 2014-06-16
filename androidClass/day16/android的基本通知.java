package com.notificationdemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

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
	}

	private void showNotification() {
		// ������������PendingIntent
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

		// ����֪ͨ����
		Notification notification = new NotificationCompat.Builder(this)
				.setOngoing(false).setSmallIcon(R.drawable.ic_action_send)
				.setTicker("�����Զ���֪ͨ").build();
		notification.setLatestEventInfo(this, "�����Զ���֪ͨ", "���������������ݣ��Ǻ�", pi);

		// ��ȡ֪ͨ���� ��֪ͨ��������
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// ����֪ͨ
		nm.notify(1, notification);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
