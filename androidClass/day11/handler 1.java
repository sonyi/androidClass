package com.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView tvNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvNumber = (TextView) findViewById(R.id.textView1);
		Button btnFind = (Button) findViewById(R.id.btn_find);
		btnFind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doFindNumber();
			}
		});
	}

	private void doFindNumber() {
		Thread worker = new Thread(new Runnable() {

			@Override
			public void run() {
				findNumber();
			}
		});
		worker.start();
	}

	private void findNumber() {
		for (int i = 1; i <= 100; i++) {
			if ((i % 2 == 0) && (i % 3 == 0)) {
				Message msg = new Message();
				
				// 方法2：
				// Message msg = h.obtainMessage();
				
				msg.what = 1;
				msg.arg1 = i;

				// Bundle data = new Bundle();
				// data.putInt("num", i);
				// msg.setData(data);
				
				h.sendMessage(msg);
				// 方法2：
				// msg.sendToTarget();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Handler h = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				int num = msg.arg1;
				Toast.makeText(MainActivity.this, num + "", Toast.LENGTH_SHORT)
						.show();
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
