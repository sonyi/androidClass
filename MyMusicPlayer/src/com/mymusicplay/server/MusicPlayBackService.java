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
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_PLAY);//֪ͨ��
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_EXIT);
		filter.addAction(ReceiverAction.ACTION_NOTIFICATION_NEXT);
		filter.addAction(Intent.ACTION_HEADSET_PLUG);//�������
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
				mPlayState = PlayStats.STATE_PLAYING;

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
				next();
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
		if (mPlayQuene.size() > 0) {
			if (mPlayState == PlayStats.STATE_PAUSE) {
				mMediaPlayer.start();
				mPlayState = PlayStats.STATE_PLAYING;

				// ���͹㲥
				Intent intent = new Intent(ReceiverAction.ACTION_PLAY);
				sendBroadcast(intent);
				sendNotification();// ����֪ͨ
			} else if (mPlayState == PlayStats.STATE_STOP) {
				playAtIndex(mCurrentPlayIndex);
			}
		} else {
			Toast.makeText(this, "������ľ�и�����Ҫ����Ŷ", Toast.LENGTH_SHORT).show();
		}
	}

	public void pause() {
		if (mPlayState == PlayStats.STATE_PLAYING) {
			mMediaPlayer.pause();
			mPlayState = PlayStats.STATE_PAUSE;

			// ���͹㲥
			Intent intent = new Intent(ReceiverAction.ACTION_PAUSE);
			sendBroadcast(intent);
			sendNotification();// ����֪ͨ
		}
	}

	public void stop() {
		if (mPlayState != PlayStats.STATE_STOP) {
			mMediaPlayer.stop();
			mMediaPlayer.reset();// �ѵ�ǰ�Ĳ�����Ϣ�����(��Ƶ��Ϣ)
			mPlayState = PlayStats.STATE_STOP;
		}
	}

	public void next() {// ������һ��
		if (mPlayQuene.size() > 0) {
			stop();
			if ((mCurrentPlayIndex + 1) < mPlayQuene.size()) {
				playAtIndex(mCurrentPlayIndex + 1);
			} else {
				playAtIndex(0);
			}
		}
	}

	public void previouse() {// ������һ��
		if (mPlayQuene.size() > 0) {
			stop();
			if ((mCurrentPlayIndex - 1) >= 0) {
				playAtIndex(mCurrentPlayIndex - 1);
			} else {
				playAtIndex(mPlayQuene.size() - 1);
			}
		}
	}

	public void playAtIndex(int index) {// ����ָ��λ�õĸ���
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

	public void addToPlayQuene(List<Music> musicList) {// ������и���������
		for (Music music : musicList) {
			mPlayQuene.add(music);
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
		return mPlayQuene.get(mCurrentPlayIndex);
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
