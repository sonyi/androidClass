package com.example.empmgrdemo;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private ProgressDialog pbProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getEmpList();
	}

	private void getEmpList() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cid", 1);
		
		RequestEntity entity = new RequestEntity("/demo", params);
		new HttpAsyncTask().execute(entity);
	}

	private class HttpAsyncTask extends AsyncTask<RequestEntity, Void, String> {
		@Override
		protected void onPreExecute() {
			pbProgress = ProgressDialog.show(MainActivity.this, "请稍候",
					"正在执行请求...");
		}

		@Override
		protected String doInBackground(RequestEntity... params) {
			return HttpUtil.post(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			pbProgress.dismiss();
			Log.i("Emp", result);
			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
