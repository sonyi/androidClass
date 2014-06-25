package com.mymusicplay.server;

import java.util.ArrayList;
import java.util.List;

import com.mymusicplay.model.Music;
import com.mymusicplay.notification.MyNotification;
import com.mymusicplay.receiver.ReceiverAction;

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
import android.telephony.TelephonyManager;



public class MusicPlayBackService extends Service {
	private MediaPlayer mMediaPlayer;
	private List<Music> mPlayQuene;
	private int mPlayState = PlayStats.STATE_STOP;
	private int mCurrentPlayIndex = 0;

	
	
	@Override
	public void onCreate() {
		mPlayQuene = new ArrayList<Music>();
		initMediaPlay();

		//ע������㲥������
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("android.intent.action.PHONE_STATE");
		registerReceiver(new PhoneStatRec(), mIntentFilter);
		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}

	private void initMediaPlay() {
		mMediaPlayer = new MediaPlayer();

		// ����MediaPlay����׼������¼�����
		mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mMediaPlayer.start();
				mPlayState = PlayStats.STATE_PLAYING;
				
				//���͹㲥
				Intent intent = new Intent(ReceiverAction.ACTION_REFRESH);
				sendBroadcast(intent);

				NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				MyNotification.showNotification(MusicPlayBackService.this, nm);
				
			}
		});

		// ��ĳһ�׸�����������¼�����
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				next();
			}
		});
	}

	public class PhoneStatRec extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			TelephonyManager mTelManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			boolean isringpause = false;
			switch (mTelManager.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:// ����
				// if (mPlayer != null && mPlayer.isPlaying()) {
				// mPlayer.pause();
				// isringpause = true;
				// }
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// ͨ��
				// if (mPlayer != null && mPlayer.isPlaying()) {
				// mPlayer.pause();
				// isringpause = true;
				// }
				break;
			case TelephonyManager.CALL_STATE_IDLE:// ͨ������
				// if (mPlayer != null && isringpause == true) {
				// mPlayer.start();
				// isringpause = false;
				// }
				break;
			}
		}
	}

	public void play() {
		if (mPlayState == PlayStats.STATE_PAUSE) {
			mMediaPlayer.start();
			mPlayState = PlayStats.STATE_PLAYING;

			// ���͹㲥
			Intent intent = new Intent(ReceiverAction.ACTION_PLAY);
			sendBroadcast(intent);
		} else if (mPlayState == PlayStats.STATE_STOP) {
			playAtIndex(mCurrentPlayIndex);
		}
	}

	public void pause() {
		if (mPlayState == PlayStats.STATE_PLAYING) {
			mMediaPlayer.pause();
			mPlayState = PlayStats.STATE_PAUSE;

			// ���͹㲥
			Intent intent = new Intent(ReceiverAction.ACTION_PAUSE);
			sendBroadcast(intent);
		}
	}

	public void stop() {
		if (mPlayState != PlayStats.STATE_STOP) {
			mMediaPlayer.stop();
			mMediaPlayer.reset();// �ѵ�ǰ�Ĳ�����Ϣ�����(��Ƶ��Ϣ)
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
			mMediaPlayer.prepareAsync();// �첽�����ļ�
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
		mMediaPlayer.release();// ���ٶ���
		super.onDestroy();
	}

	public void clearPlayQueue() {
		mPlayQuene.clear();
	}

	public int getMusicPlayPosition() {
		return mMediaPlayer.getCurrentPosition();
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
	}
}
