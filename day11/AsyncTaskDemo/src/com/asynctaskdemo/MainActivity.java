package com.asynctaskdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btnExec;

	// private ProgressDialog pdProgress;
	
	private ProgressBar pbProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnExec = (Button) findViewById(R.id.btn_exec);
		pbProgress = (ProgressBar) findViewById(R.id.progressBar1);
		
		btnExec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DemoAsyncTask task = new DemoAsyncTask();
				task.execute("这是参数","参数1","参数2");
			}
		});
	}

	private class DemoAsyncTask extends AsyncTask<String, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// pdProgress = ProgressDialog.show(MainActivity.this, "请稍后",
			//		"正在玩命加载...");
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) {
			String param = params[0];
			Log.i("async_param", param);

			int result = 0;
			for (int i = 1; i <= 100; i++) {
				if (i % 2 == 0) {
					result += i;
				}
				publishProgress(i);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int progress = values[0];
			pbProgress.setProgress(progress);
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// pdProgress.dismiss();
			Toast.makeText(MainActivity.this, result + "", Toast.LENGTH_SHORT)
					.show();
			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
