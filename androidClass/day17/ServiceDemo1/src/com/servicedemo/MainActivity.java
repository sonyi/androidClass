package com.servicedemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btnStart;
	private Button btnStart1;

	private ISecondService mSecondService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnStart = (Button) findViewById(R.id.btn_start_service);
		btnStart1 = (Button) findViewById(R.id.btn_start_service1);

		Intent intent = new Intent(MainActivity.this, SecondService.class);
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

		btnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						FirstService.class);
				intent.putExtra("oper", 0);
				startService(intent);
			}
		});

		btnStart1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSecondService != null) {
					double[] location = mSecondService.getPlaceLocation();
					Toast.makeText(MainActivity.this,
							"µ±«∞Œª÷√:(" + location[0] + "," + location[1] + ")",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mSecondService = (ISecondService) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			//
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		Log.i("ServiceDemo", "Activity:onDestroy()");
		unbindService(mServiceConnection);
		super.onDestroy();
	}
}
