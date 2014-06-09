package com.example.handlertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button mFindBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFindBtn = (Button) findViewById(R.id.btn_find);
		mFindBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doFindNum();
			}
		});
	}
	
	
	
	private void doFindNum() {
		// TODO Auto-generated method stub
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				findNum();
			}

			
		});
		t.start();
	}
	private void findNum() {
		// TODO Auto-generated method stub
		for(int i = 1; i < 100; i++){
			if((i % 3 == 0) && (i % 5 == 0)){
				//方式一：
//				Message msg = h.obtainMessage();
//				msg.what = 1;
//				msg.arg1 = i;
//				msg.sendToTarget();
				
				//方式二：
				Message msg = new Message();
				msg.what = 1;
				msg.arg1 = i;
				h.sendMessage(msg);
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}

	Handler h = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == 1){
				int num = msg.arg1;
				Toast.makeText(MainActivity.this, num + "", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
