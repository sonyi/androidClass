package com.mymusicplay.server;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.mymusicplay.R;
import com.mymusicplay.model.Music;
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

				Intent intent = new Intent(ReceiverAction.ACTION_REFRESH);
				sendBroadcast(intent);
				
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

	
	
	class ServiceBinder extends Binder implements IPlayBackService {
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

	public void play() {
		if (mPlayState == PlayStats.STATE_PAUSE) {
			mMediaPlayer.start();
			mPlayState = PlayStats.STATE_PLAYING;
		} else if (mPlayState == PlayStats.STATE_STOP) {
			playAtIndex(mCurrentPlayIndex);
		}
	}

	public void pause() {
		if (mPlayState == PlayStats.STATE_PLAYING) {
			mMediaPlayer.pause();
			mPlayState = PlayStats.STATE_PAUSE;
			
			//发送广播
			Intent intent = new Intent(ReceiverAction.ACTION_PAUSE);
			sendBroadcast(intent);
		}
	}

	public void stop() {
		if (mPlayState != PlayStats.STATE_STOP) {
			mMediaPlayer.stop();
			mMediaPlayer.reset();// 把当前的播放信息清除掉(音频信息)
			mPlayState = PlayStats.STATE_STOP;
		}
	}

	public void next() {
		if (mPlayQuene.size() > 0) {
			stop();
			if ((mCurrentPlayIndex + 1) < mPlayQuene.size()) {
				playAtIndex(mCurrentPlayIndex + 1);
			} else {
				playAtIndex(0);
			}
		}
	}

	public void previouse() {
		if (mPlayQuene.size() > 0) {
			stop();
			if ((mCurrentPlayIndex - 1) >= 0) {
				playAtIndex(mCurrentPlayIndex - 1);
			} else {
				playAtIndex(mPlayQuene.size() - 1);
			}
		}
	}

	public void playAtIndex(int index) {
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

	public void addToPlayQuene(List<Music> musicList) {
		for (Music music : musicList) {
			mPlayQuene.add(music);
		}
	}

	public int addToPlayQuene(Music music) {
		if (getCurrentMusicIndex(music) == -1) {
			mPlayQuene.add(music);
		}

		return getCurrentMusicIndex(music);
	}

	public int getCurrentPlayState() {
		return mPlayState;
	}

	public Music getCurrentMusic() {
		return mPlayQuene.get(mCurrentPlayIndex);
	}

	public int getCurrentMusicIndex(Music music) {
		int index = mPlayQuene.indexOf(music);
		return index;
	}

	public void onDestroy() {
		if (mPlayState != PlayStats.STATE_STOP) {
			mMediaPlayer.stop();
		}
		mMediaPlayer.reset();
		mMediaPlayer.release();// 销毁对象
		super.onDestroy();
	}
	
	public void clearPlayQueue() {
		mPlayQuene.clear();
	}
	
	public int getMusicPlayPosition() {
		return mMediaPlayer.getCurrentPosition();
	}
}
