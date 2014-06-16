package com.example.receiverdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String ACTION_FIRST_RECEIVER = "com.example.receiverdemo.BROADCAST";

	private FirstReceiver mFirstReceiver;
	private SecondReceiver mSecondReceiver;
	
	private NetworkStateChangeReceiver mNetworkStateChangeReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 注册广播接收器
		// 1.创建接收器实例
		mFirstReceiver = new FirstReceiver();
		mSecondReceiver = new SecondReceiver();
		
		// 2.创建IntentFilter
		IntentFilter filter1 = new IntentFilter(ACTION_FIRST_RECEIVER);
		filter1.setPriority(100);
		
		IntentFilter filter2 = new IntentFilter(ACTION_FIRST_RECEIVER);
		filter2.setPriority(101);
		
		// 3.注册广播接收器
		registerReceiver(mFirstReceiver, filter1);
		registerReceiver(mSecondReceiver, filter2);
		
		Button btnSend = (Button) findViewById(R.id.btn_send);
		btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ACTION_FIRST_RECEIVER);
				intent.putExtra("number", 7);
				
				// 发送有序广播
				sendOrderedBroadcast(intent, null);
			}
		});
		
		mNetworkStateChangeReceiver = new NetworkStateChangeReceiver();
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
	    registerReceiver(mNetworkStateChangeReceiver, filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// 一定要解除注册
		unregisterReceiver(mFirstReceiver);
		unregisterReceiver(mSecondReceiver);
		
		unregisterReceiver(mNetworkStateChangeReceiver);
		super.onDestroy();
	}
	
	private class NetworkStateChangeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			if (cm.getActiveNetworkInfo() == null) {
				Toast.makeText(MainActivity.this, "网络链接已断开。", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
