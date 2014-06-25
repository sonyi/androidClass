package com.mymusicplay.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mymusicplay.R;
import com.mymusicplay.ui.MainActivity;

public class MyNotification {
	public static void showNotification(Context context, NotificationManager manager) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

		Notification notification = new Notification();
		notification.icon = R.drawable.ic_default_art;
		notification.tickerText = "֪ͨ";
		
		notification.contentIntent = pi;
		notification.contentView = getNotifiRemoteViews(context);

		manager.notify(1, notification);
	}

	// ����֪ͨ��ͼ
	private static RemoteViews getNotifiRemoteViews(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.remoteview);
		
		remoteViews.setTextViewText(R.id.textView1, "diamonds");
		
		// ���ð�ť�ĵ�����Ϊ�㲥��ͼ��װ
//		Intent intent = new Intent(ACTION_NEXT);
//		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
//		remoteViews.setOnClickPendingIntent(R.id.btn_next, pi);
		
		return remoteViews;
	}
}
