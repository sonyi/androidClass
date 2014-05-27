package com.activitydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btn1;

	private int clickCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("main", "onCreate()");
		setContentView(R.layout.activity_main);
		btn1 = (Button) findViewById(R.id.btn1);

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clickCount++;
				
				// ����Intent��ָ����ͼ
				Intent intent = new Intent(MainActivity.this,
						SecondActivity.class);
				
				// ����Ҫ���ݵ����ݣ������һ��Bundle��
				// Bundle args = new Bundle();
				// args.putInt("count", clickCount);
				
				// ��Intent����Bundle���͵�����
				intent.putExtra("count", clickCount);
				
				// intent.setClass(MainActivity.this, SecondActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onStart() {
		Log.i("main", "onStart()");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.i("main", "onResume()");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i("main", "onPause()");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i("main", "onStop()");
		super.onStop();
	}

	@Override
	protected void onRestart() {
		Log.i("main", "onRestart()");
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		Log.i("main", "onDestroy()");
		super.onDestroy();
	}
}
