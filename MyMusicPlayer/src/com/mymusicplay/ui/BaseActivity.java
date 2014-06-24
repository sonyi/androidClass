package com.mymusicplay.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.model.Music;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.server.IPlayBackService;
import com.mymusicplay.server.PlayStats;
import com.mymusicplay.util.BitmapWorker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

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
		IntentFilter refreshFilter = new IntentFilter(
				ReceiverAction.ACTION_REFRESH);
		getActivity().registerReceiver(myReceiverRefresh, refreshFilter);

		IntentFilter pauseFilter = new IntentFilter(ReceiverAction.ACTION_PAUSE);
		getActivity().registerReceiver(myReceiverRefresh, pauseFilter);

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
			if(playflag){
				mPause.setImageResource(R.drawable.ic_play);
				playflag = false;
				myService.pause();
			}else{
				mPause.setImageResource(R.drawable.ic_pause);
				playflag = true;
				myService.play();
			}
			
			
//			if (myService.getCurrentPlayState() == PlayStats.STATE_PAUSE) {
//				myService.play();
//			}
//			if (myService.getCurrentPlayState() == PlayStats.STATE_PLAYING) {
//				myService.pause();
//			}

		}
		if (v.getId() == R.id.iv_base_previous) {
			myService.previouse();
		}
	}

	private BroadcastReceiver myReceiverRefresh = new BroadcastReceiver() {
		IPlayBackService myService;
		@Override
		public void onReceive(Context context, Intent intent) {
			// Toast.makeText(getActivity(), "hhhh", Toast.LENGTH_SHORT).show();
			myService = PlayBackServiceManager
					.getPlayBackService(getActivity());
			Music music = myService.getCurrentMusic();
			new BitmapWorker(getActivity()).fetch(music.getAlbumId(),
					mMusicCover);// 设置封面
			mMusicTitle.setText(music.getTitle());
			mMusicSinger.setText(music.getArtist());
			mSeekBar.setMax((int) music.getDuration());// 设置进度条最大值为音乐播放时间
			Log.i("time", music.getDuration() + "");
			setProgress();
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

	

	private BroadcastReceiver myReceiverPause = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(getActivity(), "hhhh", Toast.LENGTH_SHORT).show();
			
		};
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
		getActivity().unregisterReceiver(myReceiverRefresh);
		super.onDestroy();
	}

}
