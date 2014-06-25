package com.mymusicplay.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.model.Music;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.server.IPlayBackService;
import com.mymusicplay.server.PlayStats;
import com.mymusicplay.util.BitmapWorker;

public class BaseActivity extends Fragment implements OnClickListener {
	ImageView mPause, mNext, mPrevious, mMusicCover;
	TextView mMusicTitle, mMusicSinger;
	SeekBar mSeekBar;
	public static boolean playflag = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_base, null);
		mNext = (ImageView) view.findViewById(R.id.iv_base_next);
		mPause = (ImageView) view.findViewById(R.id.iv_base_pause);
		mPrevious = (ImageView) view.findViewById(R.id.iv_base_previous);
		mMusicCover = (ImageView) view.findViewById(R.id.iv_base_cover);
		mMusicTitle = (TextView) view.findViewById(R.id.tv_base_song);
		mMusicSinger = (TextView) view.findViewById(R.id.tv_base_singer);
		mSeekBar = (SeekBar) view.findViewById(R.id.seekbar_base_progress);

		mNext.setOnClickListener(this);
		mPause.setOnClickListener(this);
		mPrevious.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(null);

		// 注册广播接收器
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverAction.ACTION_REFRESH);
		filter.addAction(ReceiverAction.ACTION_PAUSE);
		filter.addAction(ReceiverAction.ACTION_PLAY);
		getActivity().registerReceiver(myReceiver, filter);

		return view;
	}

	@Override
	public void onClick(View v) {
		IPlayBackService myService = PlayBackServiceManager
				.getPlayBackService(getActivity());
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
	}

	//接收广播
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {
		IPlayBackService myService;

		@Override
		public void onReceive(Context context, Intent intent) {
			//歌曲页面刷新
			if(intent.getAction().equals(ReceiverAction.ACTION_REFRESH)){
				myService = PlayBackServiceManager
						.getPlayBackService(getActivity());
				Music music = myService.getCurrentMusic();
				new BitmapWorker(getActivity()).fetch(music.getAlbumId(),
						mMusicCover);// 设置封面
				mMusicTitle.setText(music.getTitle());
				mMusicSinger.setText(music.getArtist());
				mSeekBar.setMax((int) music.getDuration());// 设置进度条最大值为音乐播放时间
				// Log.i("time", music.getDuration() + "");
				mPause.setImageResource(R.drawable.ic_pause);
				setProgress();
			}
			
			//歌曲播放
			if(intent.getAction().equals(ReceiverAction.ACTION_PLAY)){
				mPause.setImageResource(R.drawable.ic_pause);
			}
			
			//歌曲暂停
			if(intent.getAction().equals(ReceiverAction.ACTION_PAUSE)){
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
		getActivity().unregisterReceiver(myReceiver);
		super.onDestroy();
	}
}
