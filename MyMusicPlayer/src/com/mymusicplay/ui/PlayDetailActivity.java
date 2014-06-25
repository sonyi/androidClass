package com.mymusicplay.ui;

import com.mymusicplay.R;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public class PlayDetailActivity extends ActionBarActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_detail);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
	}
}
