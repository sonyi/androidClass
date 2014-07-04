package com.mymusicplay.ui;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.data.AlbumDataAccess;
import com.mymusicplay.lrc.MatchLrcForMusic;
import com.mymusicplay.lrc.control.LyricView;
import com.mymusicplay.model.Music;
import com.mymusicplay.receiver.ReceiverAction;
import com.mymusicplay.server.IPlayBackService;
import com.mymusicplay.util.BitmapWorker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DetailFragmentLrc extends Fragment {
	private ImageView mBackgroundLrc;
	private LyricView mlLyricView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.detail_activity_lrc, null);

		return initWidget(view);
	}

	private View initWidget(View view) {
		mBackgroundLrc = (ImageView) view.findViewById(R.id.iv_lrc_background);
		mlLyricView = (LyricView) view.findViewById(R.id.audio_lrc);
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverAction.ACTION_REFRESH);
		filter.addAction(ReceiverAction.ACTION_PAUSE);
		filter.addAction(ReceiverAction.ACTION_NEXT);
		filter.addAction(ReceiverAction.ACTION_PREVIOUS);
		filter.addAction(ReceiverAction.ACTION_PLAY);
		getActivity().registerReceiver(myReceiver, filter);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		IPlayBackService myService = PlayBackServiceManager
				.getPlayBackService(getActivity());
		Music music = null;
		if (myService != null) {
			music = myService.getCurrentMusic();
		}

		if (music != null && myService != null) {
			//setAlbumImg(music);
			new MatchLrcForMusic(getActivity(), mlLyricView, music);
		}
	}

	
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			IPlayBackService myService = PlayBackServiceManager
						.getPlayBackService(getActivity());
				Music music = myService.getCurrentMusic();
			
			if(intent.getAction().equals(ReceiverAction.ACTION_REFRESH)){
				
//				setAlbumImg(music);
				new MatchLrcForMusic(context, mlLyricView, music);
			} else if(intent.getAction().equals(ReceiverAction.ACTION_PLAY)){
				new MatchLrcForMusic(context, mlLyricView, music);
			}
			
	
		}

	};

	private void setAlbumImg(Music music) {
		String path = new AlbumDataAccess(getActivity())
				.getAlbumArtByAlbumId(music.getAlbumId());
		if (path != null) {
			new BitmapWorker(getActivity()).fetch(path, mBackgroundLrc);
		} else {
			mBackgroundLrc.setImageResource(R.drawable.ic_default_art);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(myReceiver);
	}
}
