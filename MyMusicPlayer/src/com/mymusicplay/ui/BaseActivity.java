package com.mymusicplay.ui;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.server.IPlayBackService;
import com.mymusicplay.server.PlayStats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BaseActivity extends Fragment implements OnClickListener{
	ImageView mPause,mNext,mPrevious;
	IPlayBackService myService;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_base, null);
		mNext = (ImageView) view.findViewById(R.id.iv_base_next);
		mPause = (ImageView) view.findViewById(R.id.iv_base_pause);
		mPrevious = (ImageView) view.findViewById(R.id.iv_base_previous);
		mNext.setOnClickListener(this);
		mPause.setOnClickListener(this);
		mPrevious.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onClick(View v) {
		myService = PlayBackServiceManager.getPlayBackService(getActivity());
		if(v.getId() == R.id.iv_base_next){
			myService.next();
		}
		if(v.getId() == R.id.iv_base_pause){
			if(myService.getCurrentPlayState() == PlayStats.STATE_PAUSE){
				myService.play();
			}
			if(myService.getCurrentPlayState() == PlayStats.STATE_PLAYING){
				myService.pause();
			}
			
		}
		if(v.getId() == R.id.iv_base_previous){
			myService.previouse();
		}
	}
}
