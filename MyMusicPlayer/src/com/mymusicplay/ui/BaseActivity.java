package com.mymusicplay.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.model.Music;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.server.IPlayBackService;
import com.mymusicplay.server.PlayStats;
import com.mymusicplay.util.BitmapWorker;

public abstract class BaseActivity extends ActionBarActivity implements OnClickListener {
	ImageView mPause, mNext, mPrevious, mMusicCover;
	TextView mMusicTitle, mMusicSinger;
	SeekBar mSeekBar;
	View view;
	public static boolean playflag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialWidgets();
		intiBaseActivity();
	}

	//������󷽷������������ò���һ��Ҫ��setContentView�����ز����ļ�
	protected abstract void initialWidgets();
	
	private void intiBaseActivity() {
		view = findViewById(R.id.base_activity_layout);
		//view = getLayoutInflater().inflate(R.layout.activity_base, null);
		mNext = (ImageView) findViewById(R.id.iv_base_next);
		mPause = (ImageView) findViewById(R.id.iv_base_pause);
		mPrevious = (ImageView) findViewById(R.id.iv_base_previous);
		mMusicCover = (ImageView) findViewById(R.id.iv_base_cover);
		mMusicTitle = (TextView) findViewById(R.id.tv_base_song);
		mMusicSinger = (TextView) findViewById(R.id.tv_base_singer);
		mSeekBar = (SeekBar) findViewById(R.id.seekbar_base_progress);

		mNext.setOnClickListener(this);
		mPause.setOnClickListener(this);
		mPrevious.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(mySeekBarChangeListener);
		view.setOnClickListener(this);

		// ע��㲥������
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverAction.ACTION_REFRESH);
		filter.addAction(ReceiverAction.ACTION_PAUSE);
		filter.addAction(ReceiverAction.ACTION_PLAY);
		registerReceiver(myReceiver, filter);
	}

	@Override
	public void onClick(View v) {
		IPlayBackService myService = PlayBackServiceManager
				.getPlayBackService(BaseActivity.this);
		if (v.getId() == R.id.iv_base_next) {
			myService.next();
		}

		if (v.getId() == R.id.iv_base_pause) {

			switch (myService.getCurrentPlayState()) {
			case PlayStats.STATE_PAUSE:
				myService.play();
				break;
			case PlayStats.STATE_PLAYING:
				myService.pause();
				break;
			}
		}

		if (v.getId() == R.id.iv_base_previous) {
			myService.previouse();
		}
		
		if(v == view){
			//Toast.makeText(BaseActivity.this, "ahaha", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this,PlayDetailActivity.class);
			startActivity(intent);
		}
		
	}

	// ���չ㲥
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {
		IPlayBackService myService;

		@Override
		public void onReceive(Context context, Intent intent) {
			// ����ҳ��ˢ��
			if (intent.getAction().equals(ReceiverAction.ACTION_REFRESH)) {
				myService = PlayBackServiceManager
						.getPlayBackService(BaseActivity.this);
				Music music = myService.getCurrentMusic();
				new BitmapWorker(BaseActivity.this).fetch(music.getAlbumId(),
						mMusicCover);// ���÷���
				mMusicTitle.setText(music.getTitle());
				mMusicSinger.setText(music.getArtist());
				mSeekBar.setMax((int) music.getDuration());// ���ý��������ֵΪ���ֲ���ʱ��
				// Log.i("time", music.getDuration() + "");
				mPause.setImageResource(R.drawable.ic_pause);
				setProgress();
			}

			// ��������
			if (intent.getAction().equals(ReceiverAction.ACTION_PLAY)) {
				mPause.setImageResource(R.drawable.ic_pause);
			}

			// ������ͣ
			if (intent.getAction().equals(ReceiverAction.ACTION_PAUSE)) {
				mPause.setImageResource(R.drawable.ic_play);
			}
		}

		public void setProgress() {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					mSeekBar.setProgress(myService.getMusicPlayPosition());
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, 1, 1);
		}
	};
	
	private OnSeekBarChangeListener mySeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onDestroy() {
		unregisterReceiver(myReceiver);
		super.onDestroy();
	}
}
