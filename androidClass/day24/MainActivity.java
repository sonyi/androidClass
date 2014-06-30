package com.example.httpdemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ProgressDialog pdDialog;
	private TextView tvResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvResponse = (TextView) findViewById(R.id.tv_response);
		Button btnRequest = (Button) findViewById(R.id.btn_request);
		btnRequest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doHttp();
			}
		});
	}

	private void doHttp() {
		new HttpAsyncTask().execute("http://www.baidu.com");
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			pdDialog = ProgressDialog.show(MainActivity.this, "请稍候...",
					"正在获取网页内容");
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String strUrl = params[0];

			InputStream input = null;
			String result = "";
			try {
				URL url = new URL(strUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(10000);
				conn.setReadTimeout(15000);

				// 设置请求方式
				conn.setRequestMethod("GET");
				// 获取输入流接收服务器的响应数据
				input = conn.getInputStream();
				byte[] buffer = new byte[1024];
				input.read(buffer);

				result = new String(buffer, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			pdDialog.dismiss();
			
			if (result != null) {
				tvResponse.setText(result);
			}
			
			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
