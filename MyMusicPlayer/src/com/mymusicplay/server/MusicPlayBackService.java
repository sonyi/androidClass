package com.mymusicplay.server;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.mymusicplay.model.Music;
import com.mymusicplay.notification.MyNotification;
import com.mymusicplay.receiver.ReceiverAction;

public class MusicPlayBackService extends Service {
	private MediaPlayer mMediaPlayer;
	private List<Music> mPlayQuene;
	private int mPlayState = PlayStats.STATE_STOP;
	private int mCurrentPlayIndex = 0;

	@Override
	public void onCreate() {
		mPlayQuene = new ArrayList<Music>();
		initMediaPlay();

		// 注册来电广播接收器
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("android.intent.action.PHONE_STATE");
		registerReceiver(phoneReceiver, mIntentFilter);

		// 注册通知广播接收器
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_PLAY);
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_EXIT);
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_NEXT);
		registerReceiver(myReceiver, filter);

		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}

	private void initMediaPlay() {
		mMediaPlayer = new MediaPlayer();

		// 设置MediaPlay播放准备完成事件监听
		mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mMediaPlayer.start();
				mPlayState = PlayStats.STATE_PLAYING;

				// 发送广播
				Intent intent = new Intent(ReceiverAction.ACTION_REFRESH);
				sendBroadcast(intent);
				sendNotification();// 发送通知
			}
		});

		// 当某一首歌曲播放完的事件监听
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				next();
			}
		});
	}

	// 发送通知
	private void sendNotification() {
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		MyNotification.showNotification(MusicPlayBackService.this,
				getCurrentMusic(), nm, mPlayState);
	}

	public void play() {
		if (mPlayState == PlayStats.STATE_PAUSE) {
			mMediaPlayer.start();
			mPlayState = PlayStats.STATE_PLAYING;

			// 发送广播
			Intent intent = new Intent(ReceiverAction.ACTION_PLAY);
			sendBroadcast(intent);
			sendNotification();// 发送通知
		} else if (mPlayState == PlayStats.STATE_STOP) {
			playAtIndex(mCurrentPlayIndex);
		}
	}

	public void pause() {
		if (mPlayState == PlayStats.STATE_PLAYING) {
			mMediaPlayer.pause();
			mPlayState = PlayStats.STATE_PAUSE;

			// 发送广播
			Intent intent = new Intent(ReceiverAction.ACTION_PAUSE);
			sendBroadcast(intent);
			sendNotification();// 发送通知
		}
	}

	public void stop() {
		if (mPlayState != PlayStats.STATE_STOP) {
			mMediaPlayer.stop();
			mMediaPlayer.reset();// 把当前的播放信息清除掉(音频信息)
			mPlayState = PlayStats.STATE_STOP;
		}
	}

	public void next() {// 播放下一曲
		if (mPlayQuene.size() > 0) {
			stop();
			if ((mCurrentPlayIndex + 1) < mPlayQuene.size()) {
				playAtIndex(mCurrentPlayIndex + 1);
			} else {
				playAtIndex(0);
			}
		}
	}

	public void previouse() {// 播放上一曲
		if (mPlayQuene.size() > 0) {
			stop();
			if ((mCurrentPlayIndex - 1) >= 0) {
				playAtIndex(mCurrentPlayIndex - 1);
			} else {
				playAtIndex(mPlayQuene.size() - 1);
			}
		}
	}

	public void playAtIndex(int index) {// 播放指定位置的歌曲
		Music music = mPlayQuene.get(index);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mMediaPlayer.setDataSource(music.getData());
			mMediaPlayer.prepareAsync();// 异步加载文件
			mCurrentPlayIndex = index;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addToPlayQuene(List<Music> musicList) {// 添加所有歌曲到队列
		for (Music music : musicList) {
			mPlayQuene.add(music);
		}
	}

	public int addToPlayQuene(Music music) {// 添加单首歌曲到队列
		if (getCurrentMusicIndex(music) == -1) {
			mPlayQuene.add(music);
		}
		return getCurrentMusicIndex(music);
	}

	public int getCurrentPlayState() {// 返回当前播放状态
		return mPlayState;
	}

	public Music getCurrentMusic() {// 返回当前播放歌曲
		return mPlayQuene.get(mCurrentPlayIndex);
	}

	public int getCurrentMusicIndex(Music music) {// 获取歌曲在队列中的位置
		int index = mPlayQuene.indexOf(music);
		return index;
	}

	public void clearPlayQueue() {//清除播放队列中的歌曲
		mPlayQuene.clear();
	}

	public int getMusicPlayPosition() {//获取当前播放音乐播放的位置
		return mMediaPlayer.getCurrentPosition();
	}

	public void onDestroy() {
		if (mPlayState != PlayStats.STATE_STOP) {
			mMediaPlayer.stop();
		}
		mMediaPlayer.reset();
		mMediaPlayer.release();// 销毁对象

		unregisterReceiver(myReceiver);//注销广播接收器
		unregisterReceiver(phoneReceiver);
		super.onDestroy();
	}

	// 接收广播
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					ReceiverAction.ACTION_NOTIFICATION_PLAY)) {
				switch (mPlayState) {
				case PlayStats.STATE_PLAYING:
					pause();
					break;
				case PlayStats.STATE_PAUSE:
					play();
					break;
				}
			}

			if (intent.getAction().equals(
					ReceiverAction.ACTION_NOTIFICATION_NEXT)) {
				next();
			}

			if (intent.getAction().equals(
					ReceiverAction.ACTION_NOTIFICATION_EXIT)) {
				NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				nm.cancel(1);
			}
		}
	};

	// 接收电话广播
	boolean isListeningNow = false;

	private BroadcastReceiver phoneReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:// 响铃
				if ((mPlayState == PlayStats.STATE_PLAYING)
						&& (mMediaPlayer != null)) {
					pause();
					isListeningNow = true;
				}
				break;

			case TelephonyManager.CALL_STATE_OFFHOOK:// 通话
				if ((mPlayState == PlayStats.STATE_PLAYING)
						&& (mMediaPlayer != null)) {
					pause();
					isListeningNow = true;
				}
				break;

			case TelephonyManager.CALL_STATE_IDLE:// 通话结束
				if ((mMediaPlayer != null) && isListeningNow) {
					play();
					isListeningNow = false;
				}
				break;
			}
		}
	};

	private class ServiceBinder extends Binder implements IPlayBackService {
		@Override
		public void play() {
			MusicPlayBackService.this.play();
		}

		@Override
		public void pause() {
			MusicPlayBackService.this.pause();
		}

		@Override
		public void stop() {
			MusicPlayBackService.this.stop();
		}

		@Override
		public void next() {
			MusicPlayBackService.this.next();
		}

		@Override
		public void previouse() {
			MusicPlayBackService.this.previouse();
		}

		@Override
		public void playAtIndex(int index) {
			MusicPlayBackService.this.playAtIndex(index);
		}

		@Override
		public void addToPlayQueue(List<Music> musicList) {
			MusicPlayBackService.this.addToPlayQuene(musicList);
		}

		@Override
		public int addToPlayQuene(Music music) {
			return MusicPlayBackService.this.addToPlayQuene(music);
		}

		@Override
		public int getCurrentPlayState() {
			return MusicPlayBackService.this.getCurrentPlayState();
		}

		@Override
		public Music getCurrentMusic() {
			return MusicPlayBackService.this.getCurrentMusic();
		}

		@Override
		public int getCurrentMusicIndex(Music music) {
			return MusicPlayBackService.this.getCurrentMusicIndex(music);
		}

		@Override
		public void clearPlayQueue() {
			MusicPlayBackService.this.clearPlayQueue();
		}

		@Override
		public int getMusicPlayPosition() {
			return MusicPlayBackService.this.getMusicPlayPosition();
		}
	}
}
