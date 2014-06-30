package com.mymusicplay.server;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.util.Const;

public class ReceiverForService {
	Context context;

	ReceiverForService(Context context) {
		this.context = context;
	}

	// 接收广播
	public BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			IPlayBackService mService = PlayBackServiceManager
					.getPlayBackService(context);
			if (intent.getAction().equals(
					ReceiverAction.ACTION_NOTIFICATION_PLAY)) {
				switch (mService.getCurrentPlayState()) {
				case PlayStaticConst.STATE_PLAYING:
					mService.pause();
					break;
				case PlayStaticConst.STATE_PAUSE:
					mService.play();
					break;
				}
			}

			if (intent.getAction().equals(
					ReceiverAction.ACTION_NOTIFICATION_NEXT)) {
				mService.next();
			}

			if (intent.getAction().equals(
					ReceiverAction.ACTION_NOTIFICATION_EXIT)) {
				NotificationManager nm = (NotificationManager) context
						.getSystemService(context.NOTIFICATION_SERVICE);
				nm.cancel(Const.NOTIFICATION_ID);
			}
		}
	};

	// 接收电话广播
	boolean isListeningNow = false;

	public BroadcastReceiver phoneReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			IPlayBackService mService = PlayBackServiceManager
					.getPlayBackService(context);
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:// 响铃
				if ((mService.getCurrentPlayState() == PlayStaticConst.STATE_PLAYING)
						&& (mService.getMediaPlayer() != null)) {
					mService.pause();
					isListeningNow = true;
				}
				break;

			case TelephonyManager.CALL_STATE_OFFHOOK:// 通话
				if ((mService.getCurrentPlayState() == PlayStaticConst.STATE_PLAYING)
						&& (mService.getMediaPlayer() != null)) {
					mService.pause();
					isListeningNow = true;
				}
				break;

			case TelephonyManager.CALL_STATE_IDLE:// 通话结束
				if ((mService.getMediaPlayer() != null) && isListeningNow) {
					mService.play();
					isListeningNow = false;
				}
				break;
			}
		}
	};

}
