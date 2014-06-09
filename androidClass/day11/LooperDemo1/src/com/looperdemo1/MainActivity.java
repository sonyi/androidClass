package com.looperdemo1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText etText;
	private Button btn1;
	
	private Handler h;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etText = (EditText) findViewById(R.id.et_text);
		btn1 = (Button) findViewById(R.id.button1);
		
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.arg1 = Integer.valueOf(etText.getText().toString());
				h.sendMessage(msg);
			}
		});
		
		worker.start();
	}
	
	Thread worker = new Thread(new Runnable() {
		
		@Override
		public void run() {
			Looper.prepare();
			
			h = new Handler() {
				public void handleMessage(android.os.Message msg) {
				     Log.i("num", "工作者线程接收的消息："  + msg.arg1);
				}
			};
			
			Looper.loop();
		}
	});
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
