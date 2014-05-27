package com.activitydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SecondActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("second", "onCreate()");
		setContentView(R.layout.activity_second);

		Intent intent = getIntent();
		// Bundle args = intent.getBundleExtra("data");

		// Bundle args = intent.getExtras();
		// int count = args.getInt("count", 0);

		int count = intent.getIntExtra("count", 0);
		Toast.makeText(this, "已经点击了" + count + "次", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStart() {
		Log.i("second", "onStart()");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.i("second", "onResume()");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i("second", "onPause()");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i("second", "onStop()");
		super.onStop();
	}

	@Override
	protected void onRestart() {
		Log.i("second", "onRestart()");
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		Log.i("second", "onDestroy()");
		super.onDestroy();
	}
}
