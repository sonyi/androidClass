package com.example.asynctasktest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btn;
	private ProgressBar pdProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button) findViewById(R.id.btn);
		pdProgress = (ProgressBar) findViewById(R.id.progressBar);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TestAsyncTask task = new TestAsyncTask();
				task.execute("参数1","参数2","参数3");
			}
		});
	}
	
	private class TestAsyncTask extends AsyncTask<String, Integer, Integer>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//pdProgress = ProgressDialog.show(MainActivity.this, "请稍后", "正在玩命加载中....");
			super.onPreExecute();
		}
		
		
		@Override
		protected Integer doInBackground(String... params) {//该方法的参数类型,该方法不能操作UI
			// TODO Auto-generated method stub
			String param = params[0];
			int result = 0;
			for(int i = 2; i <= 100; i++){
				if(i % 2 == 0){
					result += i;
				}
				publishProgress(i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			int pro = values[0];
			pdProgress.setProgress(pro);
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			//pdProgress.dismiss();
			Toast.makeText(MainActivity.this, result + "", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
