package com.mymusicplay.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.model.Music;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.server.IPlayBackService;
import com.mymusicplay.server.PlayStaticConst;

public class PlayDetailActivity extends ActionBarActivity implements
		OnClickListener {
	private int[] pageNum = new int[3];
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;
	private ImageView mPlayOrder, mPause, mPrevious, mNext, mVoice, mBack,
			mMusicList;
	private TextView mMusicStart, mMusicDuration, mTitle, mSinger;
	private SeekBar mSeekBar;
	private int HANDLER_MESSAGE = 0x66;
	private Dialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_detail);
		setActionBarLayout(R.layout.activity_play_detail_top);
		initAdapter();
		initWidget();

		// 动画跳转
		overridePendingTransition(R.anim.anim_top_in, R.anim.anim_top_out);
	}

	public void setActionBarLayout(int layoutId) {
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);

			actionBar.setIcon(R.drawable.ic_back);

			LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			actionBar.setCustomView(v, layout);
		}
	}

	private void initAdapter() {
		mViewPager = (ViewPager) findViewById(R.id.detail_view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
	}

	private void initWidget() {
		mPlayOrder = (ImageView) findViewById(R.id.iv_detail_playorder);
		mPause = (ImageView) findViewById(R.id.iv_detail_pause);
		mPrevious = (ImageView) findViewById(R.id.iv_detail_previous);
		mNext = (ImageView) findViewById(R.id.iv_detail_next);
		mVoice = (ImageView) findViewById(R.id.iv_detail_voice);
		mBack = (ImageView) findViewById(R.id.iv_detail_back);
		mMusicList = (ImageView) findViewById(R.id.iv_detail_list);
		mMusicStart = (TextView) findViewById(R.id.tv_detail_start);
		mMusicDuration = (TextView) findViewById(R.id.tv_detail_duration);
		mTitle = (TextView) findViewById(R.id.tv_detail_title);
		mSinger = (TextView) findViewById(R.id.tv_detail_singer);
		mSeekBar = (SeekBar) findViewById(R.id.seekbar_detail_progress);

		mPlayOrder.setOnClickListener(this);
		mPrevious.setOnClickListener(this);
		mPause.setOnClickListener(this);
		mNext.setOnClickListener(this);
		mVoice.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mMusicList.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);;

		// 注册广播接收器
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverAction.ACTION_REFRESH);
		filter.addAction(ReceiverAction.ACTION_PAUSE);
		filter.addAction(ReceiverAction.ACTION_PLAY);
		registerReceiver(myReceiver, filter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		IPlayBackService myService = PlayBackServiceManager
				.getPlayBackService(this);
		Music music = null;
		if (myService != null) {
			music = myService.getCurrentMusic();
			initPlayOrderImg(myService);
		}

		if (music != null && myService != null) {
			refreshBaseActivity(myService, music);
		}
	}
	
	private void initPlayOrderImg(IPlayBackService service){
		switch (service.getCurrentPlayOrder()) {
		case PlayStaticConst.STATE_LOOP:
			mPlayOrder.setImageResource(R.drawable.ic_play_looplist);
			break;
		case PlayStaticConst.STATE_RANDOM:
			mPlayOrder.setImageResource(R.drawable.ic_play_random);
			break;
		case PlayStaticConst.STATE_CYCLE:
			mPlayOrder.setImageResource(R.drawable.ic_play_cycle);
			break;
		}
	}

	private void refreshBaseActivity(IPlayBackService myService, Music music) {

		mTitle.setText(music.getTitle());
		mSinger.setText(music.getArtist());
		mSeekBar.setMax((int) music.getDuration());// 设置进度条最大值为音乐播放时间

		int duration = (int) music.getDuration();
		int durationMin = duration / 60000;
		int durationSec = (duration % 60000) / 1000;
		mMusicDuration.setText(durationMin + ":" + durationSec);

		if (myService.getCurrentPlayState() == PlayStaticConst.STATE_PAUSE) {
			mPause.setImageResource(R.drawable.ic_play);
		} else if (myService.getCurrentPlayState() == PlayStaticConst.STATE_PLAYING) {
			mPause.setImageResource(R.drawable.ic_pause);
		}

		final IPlayBackService service = myService;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				mSeekBar.setProgress(service.getMusicPlayPosition());
				int playPosition = service.getMusicPlayPosition();
				int playPositionMin = playPosition / 60000;
				int playPositionSec = (playPosition % 60000) / 1000;
				String timeForProgerss;
				if (playPositionSec < 10) {
					timeForProgerss = playPositionMin + ":0" + playPositionSec;
				} else {
					timeForProgerss = playPositionMin + ":" + playPositionSec;
				}

				Message msg = new Message();
				msg.what = HANDLER_MESSAGE;
				msg.obj = timeForProgerss;
				handler.sendMessage(msg);
			}
		};
		mMusicStart.setText("time");
		Timer timer = new Timer();
		timer.schedule(task, 1, 1);
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == HANDLER_MESSAGE) {
				String timeForProgress = msg.obj.toString();
				mMusicStart.setText(timeForProgress);
			}
		}
	};

	// ViewPager适配器
	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public Fragment getItem(int arg0) {
			Fragment frgmt = null;
			switch (arg0) {
			case 0:
				frgmt = new DetailFragmentLrc();
				break;
			case 1:
				frgmt = new DetailFragmentPlayInterface();
				break;
			case 2:
				frgmt = new DetailFragmentOther();
				break;
			}

			return frgmt;
		}

		@Override
		public int getCount() {
			return pageNum.length;
		}
	}

	// 接收广播
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 歌曲页面刷新
			if (intent.getAction().equals(ReceiverAction.ACTION_REFRESH)) {
				IPlayBackService myService = PlayBackServiceManager
						.getPlayBackService(PlayDetailActivity.this);
				Music music = myService.getCurrentMusic();
				refreshBaseActivity(myService, music);
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

	@Override
	public void onClick(View v) {
		IPlayBackService myService = PlayBackServiceManager
				.getPlayBackService(PlayDetailActivity.this);
		switch (v.getId()) {
		case R.id.iv_detail_playorder:
			playOrderListener();
			break;
		case R.id.iv_detail_previous:
			myService.previouse();
			break;
		case R.id.iv_detail_pause:
			if (myService.getCurrentPlayState() == PlayStaticConst.STATE_PAUSE) {
				myService.play();
			} else if (myService.getCurrentPlayState() == PlayStaticConst.STATE_PLAYING) {
				myService.pause();
			} else if (myService.getCurrentPlayState() == PlayStaticConst.STATE_STOP) {
				Toast.makeText(this, "先选一下要播放的歌曲啦", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.iv_detail_next:
			myService.next();
			break;
		case R.id.iv_detail_voice:

			break;
		case R.id.iv_detail_back:
			back();
			break;
		case R.id.iv_detail_list:
			if(mDialog != null){
				mDialog.cancel();
				mDialog.dismiss();
			}
			mDialog = new MusicQueueDialog(this, R.style.Dialog_music_list);
			break;
		}
	}
	

	private void playOrderListener() {
		IPlayBackService service = PlayBackServiceManager
				.getPlayBackService(this);
		switch (service.getCurrentPlayOrder()) {
		case PlayStaticConst.STATE_LOOP:
			mPlayOrder.setImageResource(R.drawable.ic_play_random);
			service.setCurrentPlayOrder(PlayStaticConst.STATE_RANDOM);
			Toast.makeText(this, "随机播放", Toast.LENGTH_SHORT).show();
			break;
		case PlayStaticConst.STATE_RANDOM:
			mPlayOrder.setImageResource(R.drawable.ic_play_cycle);
			service.setCurrentPlayOrder(PlayStaticConst.STATE_CYCLE);
			Toast.makeText(this, "单曲循环", Toast.LENGTH_SHORT).show();
			break;
		case PlayStaticConst.STATE_CYCLE:
			mPlayOrder.setImageResource(R.drawable.ic_play_looplist);
			service.setCurrentPlayOrder(PlayStaticConst.STATE_LOOP);
			Toast.makeText(this, "列表循环", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	private OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			IPlayBackService service = PlayBackServiceManager
					.getPlayBackService(PlayDetailActivity.this);
			if (service.getCurrentPlayState() == PlayStaticConst.STATE_PLAYING) {
				int position = seekBar.getProgress();
				service.setMusicPlaySeekTo(position);
			}
		}
	};

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void back() {
		finish();
		overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_bottom_out);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
		if(mDialog != null){
			mDialog.cancel();
			mDialog.dismiss();
		}
	}

}
