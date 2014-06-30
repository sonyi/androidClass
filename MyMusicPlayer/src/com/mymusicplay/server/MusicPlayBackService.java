package com.mymusicplay.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import android.util.Log;
import android.widget.Toast;

import com.mymusicplay.model.Music;
import com.mymusicplay.notification.MyNotification;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.util.Const;

public class MusicPlayBackService extends Service {
	private MediaPlayer mMediaPlayer;
	private List<Music> mPlayQuene;
	private int mPlayState = PlayStaticConst.STATE_STOP;
	private int mCurrentPlayIndex = 0;
	private SensorManager mSensorManager;
	private MySensorEventListener mSensorEventListener;
	private NotificationManager nm;
	private ReceiverForService mReceiverForService;
	private int mPlayOrder = PlayStaticConst.STATE_LOOP;
	private int mWhatPlayNext = PlayStaticConst.PLAY_AUTO_NEXT;

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
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_PLAY);// 通知栏
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_EXIT);
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_NEXT);
		filter.addAction(Intent.ACTION_HEADSET_PLUG);// 耳机插拔
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
				mPlayState = PlayStaticConst.STATE_PLAYING;
				mWhatPlayNext = PlayStaticConst.PLAY_AUTO_NEXT;

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
				if (mWhatPlayNext == PlayStaticConst.PLAY_AUTO_NEXT) {
					next();
				}
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
		if (mPlayState == PlayStaticConst.STATE_PAUSE) {
			mMediaPlayer.start();
			mPlayState = PlayStaticConst.STATE_PLAYING;

			// 发送广播
			Intent intent = new Intent(ReceiverAction.ACTION_PLAY);
			sendBroadcast(intent);
			sendNotification();// 发送通知
		}
	}

	public void pause() {
		if (mPlayState == PlayStaticConst.STATE_PLAYING) {
			mMediaPlayer.pause();
			mPlayState = PlayStaticConst.STATE_PAUSE;

			// 发送广播
			Intent intent = new Intent(ReceiverAction.ACTION_PAUSE);
			sendBroadcast(intent);
			sendNotification();// 发送通知
		}
	}

	public void stop() {
		if (mPlayState != PlayStaticConst.STATE_STOP) {
			mPlayState = PlayStaticConst.STATE_STOP;
		}
	}

	public void next() {// 播放下一曲
		int nextIndex = getNextPlayIndex(getCurrentMusicIndex(getCurrentMusic()),
				PlayStaticConst.PLAY_NEXT, getCurrentPlayOrder());
		playAtIndex(nextIndex);
		
	}

	public void previouse() {// 播放上一曲
		int nextIndex = getNextPlayIndex(getCurrentMusicIndex(getCurrentMusic()),
				PlayStaticConst.PLAY_PREVIOUS, getCurrentPlayOrder());
		playAtIndex(nextIndex);
	}

	public void playAtIndex(int nextIndex) {// 播放指定位置的歌曲
		if(nextIndex >= 0){
			mWhatPlayNext = PlayStaticConst.PLAY_BY_INDEX;
			mMediaPlayer.stop();
			playAtIndexByService(nextIndex);
		}
	}

	// 获取要播放的下一曲的index
	private int getNextPlayIndex(int currentIndex, int nextOrPrevious,
			int playOrder) {
		int nextIndex = -1;
		if (getCurrentMusicList().size() > 0) {
			switch (playOrder) {
			case PlayStaticConst.STATE_CYCLE:// 单曲循环
				nextIndex = currentIndex;
				break;
			case PlayStaticConst.STATE_LOOP:// 列表循环
				nextIndex = playNextOrPrevious(currentIndex, nextOrPrevious);
				break;
			case PlayStaticConst.STATE_RANDOM:// 随机播放
				do {
					nextIndex = (int) (Math.random() * getCurrentMusicList().size());
				} while ((getCurrentMusicList().size() > 1)
						&& (nextIndex == getCurrentMusicIndex(getCurrentMusic())));
				break;
			}
		} else {
			Toast.makeText(this, "队列中没有歌曲", Toast.LENGTH_SHORT).show();
		}
		return nextIndex;
	}

	private int playNextOrPrevious(int currentIndex, int nextOrPrevious) {
		int nextIndex = -1;
		if (nextOrPrevious == PlayStaticConst.PLAY_NEXT) {// 播放下一曲
			if ((currentIndex + 1) < getCurrentMusicList().size()) {
				nextIndex = currentIndex + 1;
			} else {
				nextIndex = 0;
			}
		} else if (nextOrPrevious == PlayStaticConst.PLAY_PREVIOUS) {// 播放上一曲
			if ((currentIndex - 1) >= 0) {
				nextIndex = currentIndex - 1;
			} else {
				nextIndex = getCurrentMusicList().size() - 1;
			}
		}
		return nextIndex;
	}

	private void playAtIndexByService(int index) {
		Music music = mPlayQuene.get(index);
		mMediaPlayer.reset();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mCurrentPlayIndex = index;
		try {
			mMediaPlayer.setDataSource(music.getData());
			mMediaPlayer.prepareAsync();// 异步加载文件
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}

	public void addToPlayQuene(List<Music> musicList) {// 添加所有歌曲到队列
		for (Music music : musicList) {
			addToPlayQuene(music);
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
		if (mPlayQuene != null && mPlayQuene.size() != 0) {
			return mPlayQuene.get(mCurrentPlayIndex);
		}
		return null;
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

	public List<Music> getCurrentMusicList() {
		return mPlayQuene;
	}

	public int getCurrentPlayOrder() {
		return mPlayOrder;
	}

	public void setCurrentPlayOrder(int playOrder) {
		mPlayOrder = playOrder;
	}

	public void setMusicPlaySeekTo(int toPosition) {
		mMediaPlayer.seekTo(toPosition);
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

		@Override
		public List<Music> getCurrentMusicList() {
			return MusicPlayBackService.this.getCurrentMusicList();
		}

		@Override
		public int getCurrentPlayOrder() {
			return MusicPlayBackService.this.getCurrentPlayOrder();
		}

		@Override
		public void setCurrentPlayOrder(int playOrder) {
			MusicPlayBackService.this.setCurrentPlayOrder(playOrder);
		}

		@Override
		public void setMusicPlaySeekTo(int toPosition) {
			MusicPlayBackService.this.setMusicPlaySeekTo(toPosition);
		}

	}

	public void onDestroy() {
		if (mPlayState != PlayStaticConst.STATE_STOP) {
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
