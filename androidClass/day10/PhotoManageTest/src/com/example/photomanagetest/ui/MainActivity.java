package com.example.photomanagetest.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ImageView;

import com.example.photomanagetest.R;

public class MainActivity extends Activity {
	ImageView mImgLog;
	Handler h = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImgLog = (ImageView) findViewById(R.id.iv_welcome_logo);
//		mImgLog.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(MainActivity.this,HomeActivity.class);
//				startActivity(intent);
//			}
//		});
		//new AddPhoto(this);

	}
	
	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
			h.postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,HomeActivity.class);
					startActivity(intent);
				}
			}, 2000);
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
