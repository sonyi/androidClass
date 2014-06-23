package com.mymusicplay.server;

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

import com.mymusicplay.model.Music;

public class MusicPlayBackService extends Service{
	private MediaPlayer mMediaPlayer;
	private List<Music> mPlayQuene;
	private int mPlayState = PlayStats.STATE_STOP;
	private int mCurrentPlayIndex = 0;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mPlayQuene = new ArrayList<Music>();
		initMediaPlay();
		
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}
	
	private void initMediaPlay(){
		mMediaPlayer = new MediaPlayer();
		
		//设置MediaPlay播放准备完成事件监听
		mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mMediaPlayer.start();
				mPlayState = PlayStats.STATE_PLAYING;
			}
		});
		
		//当某一首歌曲播放完的事件监听
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	class ServiceBinder extends Binder implements IPlayBackService{

		@Override
		public void play() {
			if(mPlayState == PlayStats.STATE_PAUSE){
				mMediaPlayer.start();
				mPlayState = PlayStats.STATE_PLAYING;
			}else if(mPlayState == PlayStats.STATE_STOP){
				playAtIndex(mCurrentPlayIndex);
			}
		}

		@Override
		public void pause() {
			if(mPlayState == PlayStats.STATE_PLAYING){
				mMediaPlayer.pause();
				mPlayState = PlayStats.STATE_PAUSE;
			}
		}

		@Override
		public void stop() {
			if(mPlayState != PlayStats.STATE_STOP){
				mMediaPlayer.stop();
				mMediaPlayer.reset();//把当前的播放信息清除掉(音频信息)
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
			Music music = mPlayQuene.get(index);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			try {
				mMediaPlayer.setDataSource(music.getData());
				mMediaPlayer.prepareAsync();//异步加载文件
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		@Override
		public void addToPlayQuene(List<Music> musicList) {
			for(Music music : musicList){
				mPlayQuene.add(music);
			}
		}

		@Override
		public void addToPlayQuene(Music music) {
			mPlayQuene.add(music);
			
		}

		@Override
		public int getCurrentPlayState() {
			return mPlayState;
		}

		@Override
		public Music getCurrentMusic() {
			return mPlayQuene.get(mCurrentPlayIndex);
		}
	}
	
	@Override
	public void onDestroy() {
		if(mPlayState != PlayStats.STATE_STOP){
			mMediaPlayer.stop();
		}
		mMediaPlayer.reset();
		mMediaPlayer.release();//销毁对象
		super.onDestroy();
	}
}
