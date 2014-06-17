package com.example.servicetest;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btnStart, btnEnd, btnBinder;
	private ISecondService mISecondService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = new Intent(MainActivity.this,SecondService.class);
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
		
		btnStart = (Button) findViewById(R.id.btn_start_service);
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,FirstService.class);
				startService(intent);
			}
		});
		
		btnEnd = (Button) findViewById(R.id.btn_end_service);
		btnEnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,FirstService.class);
				stopService(intent);
			}
		});
		
		btnBinder = (Button) findViewById(R.id.btn_binder_service);
		btnBinder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mServiceConnection != null){
					double[] location = mISecondService.getPlaceLocation();
					Toast.makeText(MainActivity.this, "µ±«∞Œª÷√£∫(" + location[0] + "," + location[1] + ")",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mISecondService = (ISecondService) service;

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unbindService(mServiceConnection);
		super.onDestroy();
	}

}
