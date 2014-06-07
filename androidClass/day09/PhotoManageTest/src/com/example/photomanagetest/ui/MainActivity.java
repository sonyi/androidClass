package com.example.photomanagetest.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.example.photomanagetest.R;
import com.example.photomanagetest.data.AddPhoto;
import com.example.photomanagetest.data.PhotoManageDataAccess;
import com.example.photomanagetest.model.PhotoInformation;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	ImageView mImgLog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImgLog = (ImageView) findViewById(R.id.iv_welcome_logo);
		mImgLog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,HomeActivity.class);
				startActivity(intent);
			}
		});
		//new AddPhoto(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
