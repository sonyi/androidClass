package com.mymusicplay.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.model.Music;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.server.IPlayBackService;
import com.mymusicplay.server.PlayStats;
import com.mymusicplay.util.BitmapWorker;

public class PlayDetailActivity extends ActionBarActivity {
	private int[] pageNum = new int[3];
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;
	private ImageView mPlayOrder, mPause, mPrevious, mNext, mVoice, mBack,
			mMusicList;
	TextView mMusicStart, mMusicDuration, mTitle, mSinger;
	SeekBar mSeekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_detail);

		initAdapter();
		initWidget();
	}

	private void initAdapter() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
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
		}

		if (music != null && myService != null) {
			refreshBaseActivity(myService, music);
		}
	}

	private void refreshBaseActivity(IPlayBackService myService, Music music) {
		// new BitmapWorker(this).fetch(music.getAlbumId(),
		// mMusicCover);// 设置封面
		mTitle.setText(music.getTitle());
		mSinger.setText(music.getArtist());
		mSeekBar.setMax((int) music.getDuration());// 设置进度条最大值为音乐播放时间
		// Log.i("time", music.getDuration() + "");
		
		if(myService.getCurrentPlayState() == PlayStats.STATE_PAUSE){
			mPause.setImageResource(R.drawable.ic_play);
		}else if(myService.getCurrentPlayState() == PlayStats.STATE_PLAYING){
			mPause.setImageResource(R.drawable.ic_pause);
		}

		final IPlayBackService service = myService;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				mSeekBar.setProgress(service.getMusicPlayPosition());
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, 1, 1);
	}

	// ViewPager适配器
	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
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
					refreshBaseActivity(myService,music);
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
}
