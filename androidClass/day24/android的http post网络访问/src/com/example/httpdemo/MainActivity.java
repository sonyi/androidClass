package com.example.httpdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ProgressDialog pdDialog;
	private TextView tvResponse;

	private ConnectChangeReceiver mReceiver;

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

		mReceiver = new ConnectChangeReceiver();
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, filter);
	}

	private void doHttp() {
		if (!ConnectivityUtil.isConnect(this)) {
			Toast.makeText(this, "当前没有可用的网络连接", Toast.LENGTH_SHORT).show();
			return;
		}

		String domain = "http://yunming-api.suryani.cn/api";
		String url = domain + "/passport/login.do";
		new HttpAsyncTask().execute(url);
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
			OutputStream output = null;
			String result = "";
			try {
				URL url = new URL(strUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(10000);
				conn.setReadTimeout(15000);

				// 设置请求头信息
				conn.setRequestMethod("POST");
				conn.addRequestProperty("Charset", "utf-8");
				conn.addRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				conn.setDoOutput(true);

				// 发送请求参数(请求正文内容)
				String telNum = "13950199257";
				String pwd = "111111";
				String client = "android";
				
				StringBuilder builder = new StringBuilder();
				builder.append("phoneNumber=").append(telNum).append("&")
						.append("password=").append(pwd).append("&")
						.append("client=").append(client);
				String urlEncodedParams = builder.toString();

				output = conn.getOutputStream();
				output.write(urlEncodedParams.getBytes("utf-8"));

				// 先判断响应状态码，200表示正确响应，做出正确处理，否则一定要做错误处理
				if (conn.getResponseCode() == 200) {
					// 获取输入流接收服务器的响应数据
					input = conn.getInputStream();
					byte[] buffer = new byte[1024];
					input.read(buffer);

					result = new String(buffer, "utf-8");
				} else {
					Log.i("http", "未能正确响应：" + conn.getResponseMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (input != null) {
						input.close();
					}
					if (output != null) {
						output.flush();
						output.close();
					}
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

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
}
