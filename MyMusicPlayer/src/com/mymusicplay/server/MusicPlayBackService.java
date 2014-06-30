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
		initMediaPlay();// ��ʼ��ý�岥����
		initIntentFilter();// ע����������
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}

	private void initIntentFilter() {
		mReceiverForService = new ReceiverForService(this);

		// ע������㲥������
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("android.intent.action.PHONE_STATE");
		registerReceiver(mReceiverForService.phoneReceiver, mIntentFilter);

		// ע��֪ͨ�㲥������
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_PLAY);// ֪ͨ��
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_EXIT);
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_NEXT);
		filter.addAction(Intent.ACTION_HEADSET_PLUG);// �������
		registerReceiver(mReceiverForService.myReceiver, filter);

		// ע��������Ӧ������(ҡһҡʱ�е���һ��)
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		mSensorEventListener = new MySensorEventListener(this, vibrator);
		mSensorManager.registerListener(mSensorEventListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	private void initMediaPlay() {

		mMediaPlayer = new MediaPlayer();

		// ����MediaPlay����׼������¼�����
		mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mMediaPlayer.start();
				mPlayState = PlayStaticConst.STATE_PLAYING;
				mWhatPlayNext = PlayStaticConst.PLAY_AUTO_NEXT;

				// ���͹㲥
				Intent intent = new Intent(ReceiverAction.ACTION_REFRESH);
				sendBroadcast(intent);
				sendNotification();// ����֪ͨ
			}
		});

		// ��ĳһ�׸�����������¼�����
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				if (mWhatPlayNext == PlayStaticConst.PLAY_AUTO_NEXT) {
					next();
				}
			}
		});
	}

	// ����֪ͨ
	private void sendNotification() {
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		MyNotification.showNotification(MusicPlayBackService.this,
				getCurrentMusic(), nm, mPlayState);

	}

	public void play() {
		if (mPlayState == PlayStaticConst.STATE_PAUSE) {
			mMediaPlayer.start();
			mPlayState = PlayStaticConst.STATE_PLAYING;

			// ���͹㲥
			Intent intent = new Intent(ReceiverAction.ACTION_PLAY);
			sendBroadcast(intent);
			sendNotification();// ����֪ͨ
		}
	}

	public void pause() {
		if (mPlayState == PlayStaticConst.STATE_PLAYING) {
			mMediaPlayer.pause();
			mPlayState = PlayStaticConst.STATE_PAUSE;

			// ���͹㲥
			Intent intent = new Intent(ReceiverAction.ACTION_PAUSE);
			sendBroadcast(intent);
			sendNotification();// ����֪ͨ
		}
	}

	public void stop() {
		if (mPlayState != PlayStaticConst.STATE_STOP) {
			mPlayState = PlayStaticConst.STATE_STOP;
		}
	}

	public void next() {// ������һ��
		int nextIndex = getNextPlayIndex(getCurrentMusicIndex(getCurrentMusic()),
				PlayStaticConst.PLAY_NEXT, getCurrentPlayOrder());
		playAtIndex(nextIndex);
		
	}

	public void previouse() {// ������һ��
		int nextIndex = getNextPlayIndex(getCurrentMusicIndex(getCurrentMusic()),
				PlayStaticConst.PLAY_PREVIOUS, getCurrentPlayOrder());
		playAtIndex(nextIndex);
	}

	public void playAtIndex(int nextIndex) {// ����ָ��λ�õĸ���
		if(nextIndex >= 0){
			mWhatPlayNext = PlayStaticConst.PLAY_BY_INDEX;
			mMediaPlayer.stop();
			playAtIndexByService(nextIndex);
		}
	}

	// ��ȡҪ���ŵ���һ����index
	private int getNextPlayIndex(int currentIndex, int nextOrPrevious,
			int playOrder) {
		int nextIndex = -1;
		if (getCurrentMusicList().size() > 0) {
			switch (playOrder) {
			case PlayStaticConst.STATE_CYCLE:// ����ѭ��
				nextIndex = currentIndex;
				break;
			case PlayStaticConst.STATE_LOOP:// �б�ѭ��
				nextIndex = playNextOrPrevious(currentIndex, nextOrPrevious);
				break;
			case PlayStaticConst.STATE_RANDOM:// �������
				do {
					nextIndex = (int) (Math.random() * getCurrentMusicList().size());
				} while ((getCurrentMusicList().size() > 1)
						&& (nextIndex == getCurrentMusicIndex(getCurrentMusic())));
				break;
			}
		} else {
			Toast.makeText(this, "������û�и���", Toast.LENGTH_SHORT).show();
		}
		return nextIndex;
	}

	private int playNextOrPrevious(int currentIndex, int nextOrPrevious) {
		int nextIndex = -1;
		if (nextOrPrevious == PlayStaticConst.PLAY_NEXT) {// ������һ��
			if ((currentIndex + 1) < getCurrentMusicList().size()) {
				nextIndex = currentIndex + 1;
			} else {
				nextIndex = 0;
			}
		} else if (nextOrPrevious == PlayStaticConst.PLAY_PREVIOUS) {// ������һ��
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
			mMediaPlayer.prepareAsync();// �첽�����ļ�
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

	public void addToPlayQuene(List<Music> musicList) {// ������и���������
		for (Music music : musicList) {
			addToPlayQuene(music);
		}
	}

	public int addToPlayQuene(Music music) {// ��ӵ��׸���������
		if (getCurrentMusicIndex(music) == -1) {
			mPlayQuene.add(music);
		}
		return getCurrentMusicIndex(music);
	}

	public int getCurrentPlayState() {// ���ص�ǰ����״̬
		return mPlayState;
	}

	public Music getCurrentMusic() {// ���ص�ǰ���Ÿ���
		if (mPlayQuene != null && mPlayQuene.size() != 0) {
			return mPlayQuene.get(mCurrentPlayIndex);
		}
		return null;
	}

	public int getCurrentMusicIndex(Music music) {// ��ȡ�����ڶ����е�λ��
		int index = mPlayQuene.indexOf(music);
		return index;
	}

	public void clearPlayQueue() {// ������Ŷ����еĸ���
		mPlayQuene.clear();
	}

	public int getMusicPlayPosition() {// ��ȡ��ǰ�������ֲ��ŵ�λ��

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
		mMediaPlayer.release();// ���ٶ���

		unregisterReceiver(mReceiverForService.myReceiver);// ע���㲥������
		unregisterReceiver(mReceiverForService.phoneReceiver);

		// ע��������Ӧ������
		mSensorManager.unregisterListener(mSensorEventListener);

		// ע��֪ͨ��
		nm.cancel(Const.NOTIFICATION_ID);
		super.onDestroy();
	}
}
