package com.example.mymoments.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mymoments.R;

public class TabFrameLeft extends Fragment {
	ImageView mSendImgView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frame_tab_left, null);
		
		
		
		mSendImgView = (ImageView) view.findViewById(R.id.iv_tab_left_sendmessage);
		mSendImgView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),SendActivity.class);
				startActivity(intent);
			}
		});
		
		return view;
	}

}
