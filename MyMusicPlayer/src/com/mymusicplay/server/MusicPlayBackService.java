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
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.mymusicplay.model.Music;
import com.mymusicplay.notification.MyNotification;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.util.Const;

public class MusicPlayBackService extends Service {
	private MediaPlayer mMediaPlayer;
	private List<Music> mPlayQuene;
	private int mPlayState = PlayStats.STATE_STOP;
	private int mCurrentPlayIndex = 0;
	SensorManager mSensorManager;
	MySensorEventListener mSensorEventListener;
	NotificationManager nm;
	ReceiverForService mReceiverForService;

	@Override
	public void onCreate() {
		mPlayQuene = new ArrayList<Music>();
		initMediaPlay();// 初始化媒体播放器
		initIntentFilter();// 注册各类监听器
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}

	private void initIntentFilter() {
		mReceiverForService = new ReceiverForService(this);

		// 注册来电广播接收器
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("android.intent.action.PHONE_STATE");
		registerReceiver(mReceiverForService.phoneReceiver, mIntentFilter);

		// 注册通知广播接收器
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_PLAY);//通知栏
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_EXIT);
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_NEXT);
		filter.addAction(Intent.ACTION_HEADSET_PLUG);//耳机插拔
		registerReceiver(mReceiverForService.myReceiver, filter);

		// 注册重力感应监听器(摇一摇时切到下一首)
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		mSensorEventListener = new MySensorEventListener(this, vibrator);
		mSensorManager.registerListener(mSensorEventListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		 

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
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		MyNotification.showNotification(MusicPlayBackService.this,
				getCurrentMusic(), nm, mPlayState);

	}

	public void play() {
		if (mPlayQuene.size() > 0) {
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
		} else {
			Toast.makeText(this, "队列中木有歌曲，要先添哦", Toast.LENGTH_SHORT).show();
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

	public void clearPlayQueue() {// 清除播放队列中的歌曲
		mPlayQuene.clear();
	}

	public int getMusicPlayPosition() {// 获取当前播放音乐播放的位置
		return mMediaPlayer.getCurrentPosition();
	}

	public MediaPlayer getMediaPlayer() {
		return mMediaPlayer;
	}

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

		@Override
		public MediaPlayer getMediaPlayer() {
			//
			return MusicPlayBackService.this.getMediaPlayer();
		}

	}

	public void onDestroy() {
		if (mPlayState != PlayStats.STATE_STOP) {
			mMediaPlayer.stop();
		}
		mMediaPlayer.reset();
		mMediaPlayer.release();// 销毁对象

		unregisterReceiver(mReceiverForService.myReceiver);// 注销广播接收器
		unregisterReceiver(mReceiverForService.phoneReceiver);

		// 注销重力感应监听器
		mSensorManager.unregisterListener(mSensorEventListener);

		// 注销通知栏
		nm.cancel(Const.NOTIFICATION_ID);
		super.onDestroy();
	}
}
