package com.mymusicplay.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.RemoteViews;

import com.mymusicplay.R;
import com.mymusicplay.data.AlbumDataAccess;
import com.mymusicplay.model.Music;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.server.PlayStaticConst;
import com.mymusicplay.ui.MainActivity;
import com.mymusicplay.util.Const;

public class MyNotification {
	static RemoteViews remoteViews;
	public static void showNotification(Context context, Music music, NotificationManager manager,int playStats) {
		if(music != null){
			Intent intent = new Intent(context, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

			PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

			Notification notification = new Notification();
			notification.icon = R.drawable.ic_default_art;
			notification.tickerText = music.getTitle();
			
			notification.contentIntent = pi;
			notification.contentView = getNotifiRemoteViews(context,music,playStats);

			manager.notify(Const.NOTIFICATION_ID, notification);
		}
	}

	// 创建通知视图
	private static RemoteViews getNotifiRemoteViews(Context context,Music music,int playStats) {
		remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.notification_layout);
		remoteViews.setTextViewText(R.id.tv_notification_title, music.getTitle());
		
		Bitmap data = getBitmapByAlbumID(context, music.getAlbumId());
		if(data != null){
			remoteViews.setImageViewBitmap(R.id.iv_notification_cover, data);
		}else{
			remoteViews.setImageViewResource(R.id.iv_notification_cover, R.drawable.ic_default_art);
		}
		
		if(playStats == PlayStaticConst.STATE_PLAYING){
			remoteViews.setImageViewResource(R.id.iv_notification_pause, R.drawable.ic_pause);
		}else if(playStats == PlayStaticConst.STATE_PAUSE) {
			remoteViews.setImageViewResource(R.id.iv_notification_pause, R.drawable.ic_play);
		}
		
		// 设置单击行为广播意图包装	
		Intent intentNext = new Intent(ReceiverAction.ACTION_NOTIFICATION_NEXT);
		PendingIntent piNext = PendingIntent.getBroadcast(context, 0, intentNext, 0);
		remoteViews.setOnClickPendingIntent(R.id.iv_notification_next, piNext);
		
		Intent intentExit = new Intent(ReceiverAction.ACTION_NOTIFICATION_EXIT);
		PendingIntent piExit = PendingIntent.getBroadcast(context, 0, intentExit, 0);
		remoteViews.setOnClickPendingIntent(R.id.iv_notification_exit, piExit);
		
		Intent intentPause = new Intent(ReceiverAction.ACTION_NOTIFICATION_PLAY);
		PendingIntent piPause = PendingIntent.getBroadcast(context, 0, intentPause, 0);
		remoteViews.setOnClickPendingIntent(R.id.iv_notification_pause, piPause);
			
		return remoteViews;
	}
	
	private static Bitmap getBitmapByAlbumID(Context context, long albumID){
		String path = new AlbumDataAccess(context).getAlbumArtByAlbumId(albumID);
		if(path == null){
			return null;
		}
		Options opts = new Options();
		opts.inSampleSize = 2;
		return BitmapFactory.decodeFile(path, opts);
	}
}
