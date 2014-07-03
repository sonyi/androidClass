package com.mymusicplay.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.data.AlbumDataAccess;
import com.mymusicplay.model.Music;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.server.IPlayBackService;
import com.mymusicplay.server.PlayStaticConst;
import com.mymusicplay.util.BitmapWorker;
import com.mymusicplay.util.Const;

public abstract class BaseActivity extends ActionBarActivity implements
		OnClickListener {
	private ImageView mPause, mNext, mPrevious, mMusicCover;
	private TextView mMusicTitle, mMusicSinger;
	private SeekBar mSeekBar;
	private View view;
	Thread threadForSeekbar = null;

	public static boolean playflag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialWidgets();
		intiBaseActivity();

	}

	@Override
	protected void onResume() {
		super.onResume();
		IPlayBackService myService = PlayBackServiceManager
				.getPlayBackService(BaseActivity.this);
		Music music = null;
		if (myService != null) {
			music = myService.getCurrentMusic();
		}

		if (music != null && myService != null) {
			refreshBaseActivity(myService, music);
		}

		

	}

	// 定义抽象方法，子类必须调用并第一句要是setContentView，加载布局文件
	protected abstract void initialWidgets();

	private void intiBaseActivity() {
		view = findViewById(R.id.base_activity_layout);
		// view = getLayoutInflater().inflate(R.layout.activity_base, null);
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

		// 注册广播接收器
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
			case PlayStaticConst.STATE_PAUSE:
				myService.play();
				break;
			case PlayStaticConst.STATE_PLAYING:
				myService.pause();
				break;
			case PlayStaticConst.STATE_STOP:
				Toast.makeText(BaseActivity.this, "先选一下要播放的歌曲啦",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

		if (v.getId() == R.id.iv_base_previous) {
			myService.previouse();
		}

		if (v == view) {
			// Toast.makeText(BaseActivity.this, "ahaha",
			// Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, PlayDetailActivity.class);
			startActivity(intent);
		}

	}

	// 接收广播
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 歌曲页面刷新
			if (intent.getAction().equals(ReceiverAction.ACTION_REFRESH)) {
				IPlayBackService myService = PlayBackServiceManager
						.getPlayBackService(BaseActivity.this);
				Music music = myService.getCurrentMusic();
				if (music != null) {
					refreshBaseActivity(myService, music);
				}
			}

			// 歌曲播放
			if (intent.getAction().equals(ReceiverAction.ACTION_PLAY)) {
				mPause.setImageResource(R.drawable.ic_pause);
			}

			// 歌曲暂停
			if (intent.getAction().equals(ReceiverAction.ACTION_PAUSE)) {
				mPause.setImageResource(R.drawable.ic_play);
			}
		}
	};

	private void refreshBaseActivity(IPlayBackService myService, Music music) {
		String path = new AlbumDataAccess(this).getAlbumArtByAlbumId(music
				.getAlbumId());
		if (path != null && !path.equals("")) {
			new BitmapWorker(BaseActivity.this).fetch(path, mMusicCover);// 设置封面
		} else {
			mMusicCover.setImageResource(R.drawable.ic_default_art);
		}

		mMusicTitle.setText(music.getTitle());
		mMusicSinger.setText(music.getArtist());
		mSeekBar.setMax((int) music.getDuration());// 设置进度条最大值为音乐播放时间
		// Log.i("time", music.getDuration() + "");

		if (myService.getCurrentPlayState() == PlayStaticConst.STATE_PAUSE) {
			mPause.setImageResource(R.drawable.ic_play);
		} else if (myService.getCurrentPlayState() == PlayStaticConst.STATE_PLAYING) {
			mPause.setImageResource(R.drawable.ic_pause);
		}

		if (threadForSeekbar == null) {
			isrunable = true;
			threadForSeekbar = new myrunabe(myService);
			threadForSeekbar.start();
		}
	}

	boolean isrunable = true;

	class myrunabe extends Thread {
		IPlayBackService service;

		myrunabe(IPlayBackService service) {
			this.service = service;
		}

		@Override
		public void run() {
			while (isrunable) {
				if (service != null && service.getMediaPlayer() != null
						&& service.getMediaPlayer().isPlaying()) {
					Message msg = new Message();
					msg.what = Const.SEEKBAR_CHANGE;
					msg.arg1 = service.getMusicPlayPosition();
					handler.sendMessage(msg);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Const.SEEKBAR_CHANGE:
				int progress = msg.arg1;
				mSeekBar.setProgress(progress);
				break;
			}
		}
	};

	private OnSeekBarChangeListener mySeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			IPlayBackService service = PlayBackServiceManager
					.getPlayBackService(BaseActivity.this);
			if (service.getCurrentPlayState() == PlayStaticConst.STATE_PLAYING) {
				int position = seekBar.getProgress();
				service.setMusicPlaySeekTo(position);
			} else if (service.getCurrentPlayState() == PlayStaticConst.STATE_PAUSE) {
				service.play();
				int position = seekBar.getProgress();
				service.setMusicPlaySeekTo(position);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}
	};

	@Override
	protected void onStop() {
		isrunable = false;
		super.onStop();
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(myReceiver);
		super.onDestroy();
	}
}
