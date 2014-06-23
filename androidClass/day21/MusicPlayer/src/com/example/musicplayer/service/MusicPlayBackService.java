package com.example.musicplayer.service;

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

import com.example.musicplayer.model.Music;

public class MusicPlayBackService extends Service {
	private MediaPlayer mMediaPlayer;
	private List<Music> mPlayQueue;
	private int mPlayState = PlayState.STATE_STOP;
	private int mCurrentPlayIndex = 0;

	@Override
	public void onCreate() {
		mPlayQueue = new ArrayList<Music>();
		initialMediaPlayer();
		super.onCreate();
	}

	/**
	 * 初始化MediaPlayer对象
	 */
	private void initialMediaPlayer() {
		mMediaPlayer = new MediaPlayer();
		// 设置MediaPlayer播放准备完成时间监听
		mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mMediaPlayer.start();
				mPlayState = PlayState.STATE_PLAYING;
			}
		});

		// 设置当某一首曲目播放完毕时的事件监听
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				//
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
			if (mPlayState == PlayState.STATE_PAUSE) {
				mMediaPlayer.start();
				mPlayState = PlayState.STATE_PLAYING;
			} else if (mPlayState == PlayState.STATE_STOP) {
				playAtIndex(mCurrentPlayIndex);
			}
		}

		@Override
		public void pause() {
			if (mPlayState == PlayState.STATE_PLAYING) {
				mMediaPlayer.pause();
				mPlayState = PlayState.STATE_PAUSE;
			}
		}

		@Override
		public void stop() {
			if (mPlayState != PlayState.STATE_STOP) {
				mMediaPlayer.stop();
				mMediaPlayer.reset();
			}
		}

		@Override
		public void next() {
			// TODO Auto-generated method stub

		}

		@Override
		public void previouse() {
			// TODO Auto-generated method stub

		}

		@Override
		public void playAtIndex(int index) {
			Music music = mPlayQueue.get(index);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			try {
				mMediaPlayer.setDataSource(music.getData());
				mMediaPlayer.prepareAsync();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void addToPlayQueue(List<Music> musicList) {
			for (Music music : musicList) {
				mPlayQueue.add(music);
			}
		}

		@Override
		public void addToPlayQueue(Music music) {
			mPlayQueue.add(music);
		}

		@Override
		public int getCurrentPlayState() {
			return mPlayState;
		}

		@Override
		public Music getCurrentMusic() {
			return mPlayQueue.get(mCurrentPlayIndex);
		}
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
