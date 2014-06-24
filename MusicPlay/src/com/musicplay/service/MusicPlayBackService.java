package com.musicplay.service;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;

import com.musicplay.model.Music;

public class MusicPlayBackService extends Service {

	private MediaPlayer mMediaPlayer;
	private List<Music> mPlayQueue;
	private int mPlayState = PlayState.STATE_STOP;
	private int mCurrentPlayIndex = 0;
	public static final String ACTION_NOTIFY_MUSIC_CHANGE = "com.musicplay.service.CHANGE";

	@Override
	public void onCreate() {
		mPlayQueue = new ArrayList<Music>();
		InitMediaPlayer();
		super.onCreate();
	}

	/**
	 * 初始化MediaPlayer对象
	 * 
	 */
	private void InitMediaPlayer() {
		mMediaPlayer = new MediaPlayer();
		// 设置MediaPlayer播放准备完成事件监听
		mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mMediaPlayer.start();
				mPlayState = PlayState.STATE_PLAYING;

				Intent intent = new Intent(ACTION_NOTIFY_MUSIC_CHANGE);
				sendBroadcast(intent);
			}
		});
		// 设置当某一首曲目播放完毕时的事件监听
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				stop();
				next();
			}
		});
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
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
			MusicPlayBackService.this.addToPlayQueue(musicList);
		}

		@Override
		public int addToPlayQueue(Music music) {
			return MusicPlayBackService.this.addToPlayQueue(music);
		}

		@Override
		public int getCurrenPlayState() {
			return MusicPlayBackService.this.getCurrenPlayState();
		}

		@Override
		public Music getCurrenMusic() {
			return MusicPlayBackService.this.getCurrenMusic();
		}

		@Override
		public int getCurrentMusicIndex(Music music) {

			return MusicPlayBackService.this.getCurrentMusicIndex(music);
		}

	}

	public void play() {
		if (mPlayState == PlayState.STATE_PAUSE) {
			mMediaPlayer.start();
			mPlayState = PlayState.STATE_PLAYING;
		} else if (mPlayState == PlayState.STATE_STOP) {
			playAtIndex(mCurrentPlayIndex);
		}
	}

	public void pause() {
		if (mPlayState == PlayState.STATE_PLAYING) {
			mMediaPlayer.pause();
			mPlayState = PlayState.STATE_PAUSE;
		}
	}

	public void stop() {
		if (mPlayState != PlayState.STATE_STOP) {
			mMediaPlayer.stop();
			mMediaPlayer.reset();
			mPlayState = PlayState.STATE_STOP;
		}
	}

	public void next() {
		if (mCurrentPlayIndex >= mPlayQueue.size() - 1) {
			mCurrentPlayIndex = 0;
		} else {
			mCurrentPlayIndex++;
		}
		playAtIndex(mCurrentPlayIndex);
	}

	public void previouse() {
		// TODO Auto-generated method stub

	}

	public void playAtIndex(int index) {
		Music music = mPlayQueue.get(index);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mMediaPlayer.setDataSource(music.getData());
			mMediaPlayer.prepareAsync();
			mCurrentPlayIndex = index;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addToPlayQueue(List<Music> musicList) {
		for (Music music : musicList) {
			mPlayQueue.add(music);
		}
	}

	public int addToPlayQueue(Music music) {
		mPlayQueue.add(music);
		return getCurrentMusicIndex(music);
	}

	public int getCurrenPlayState() {
		return mPlayState;
	}

	public Music getCurrenMusic() {
		return mPlayQueue.get(mCurrentPlayIndex);
	}

	public int getCurrentMusicIndex(Music music) {
		int index = mPlayQueue.indexOf(music);
		return index;
	}

	@Override
	public void onDestroy() {
		if (mPlayState != PlayState.STATE_STOP) {
			mMediaPlayer.stop();
		}
		mMediaPlayer.reset();
		mMediaPlayer.release();
		super.onDestroy();
	}
}
