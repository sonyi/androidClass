package com.example.parceltest;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class SecondActivity extends Activity{
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_main);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		Feed feed = intent.getParcelableExtra("data");
		String line = feed.getId() + "--" + feed.getTitle() + "--" + feed.getAuthor();
		Toast.makeText(this, line, Toast.LENGTH_SHORT).show();
	}
}
