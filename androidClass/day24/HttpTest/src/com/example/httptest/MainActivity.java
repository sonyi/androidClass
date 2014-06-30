package com.example.httptest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView mText;
	private Button mBtn;
	private ProgressDialog mDialog;
	ConnectChangeReceiver mReceiver;
	private EditText mName,mPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBtn = (Button) findViewById(R.id.btn);
		mText = (TextView) findViewById(R.id.tv);
		mName = (EditText) findViewById(R.id.et_name);
		mPwd = (EditText) findViewById(R.id.et_pwd);
		mBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//doHttpGET();
				doHttpPOST();
			}
		});
		
		mReceiver = new ConnectChangeReceiver();
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, filter);
	}
	
	private void doHttpPOST(){
		if(!ConnectivityUtil.isConnect(this)){
			Toast.makeText(this, "没有网络", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String domain = "http://yunming-api.suryani.cn/api";
		String url = domain + "/passport/login.do";
		new HttpAsyncTaskPOST().execute(url);

	}
	
	private class HttpAsyncTaskPOST extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			mDialog = ProgressDialog.show(MainActivity.this, "请稍后...",
					"数据加载中.....");

			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String strUrl = params[0];
			InputStream in = null;
			OutputStream out = null;
			String result = null;
			try {
				URL url = new URL(strUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(10000);
				conn.setReadTimeout(15000);
				
				// 设置请求头信息
				conn.setRequestMethod("POST");
				conn.addRequestProperty("Charset", "utf-8");
				conn.addRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				conn.setDoOutput(true);
				
				
				// 发送请求参数(请求正文内容)
				String telNum = mName.getText().toString().trim();
				String pwd = mPwd.getText().toString().trim();
				String client = "android";
				
				StringBuffer b = new StringBuffer();
				b.append("phoneNumber=").append(telNum).append("&password=").append(pwd).append("&client=").append(client);
				String urlEncodedParams = b.toString();
				Log.i("http", urlEncodedParams);
				
				out = conn.getOutputStream();
				out.write(urlEncodedParams.getBytes("utf-8"));
				
				if(conn.getResponseCode() == 200){
					in = conn.getInputStream();
					byte[] buf = new byte[1024];
					in.read(buf);
					result = new String(buf,"utf-8");
				}else{
					Log.i("http", "未能正常工作：" + conn.getResponseCode());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(in != null){
						in.close();
					}
					if(out != null){
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			mDialog.dismiss();
			if(result != null){
				mText.setText(result);
			}
			
			super.onPostExecute(result);
		}

	}
	
	
	
	/*private void doHttpGET(){
		if(!ConnectivityUtil.isConnect(this)){
			Toast.makeText(this, "没有网络", Toast.LENGTH_SHORT).show();
			return;
		}
		new HttpAsyncTaskGET().execute("http://www.baidu.com");
		
	}

	
	private class HttpAsyncTaskGET extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			mDialog = ProgressDialog.show(MainActivity.this, "请稍后...",
					"数据加载中.....");

			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String strUrl = params[0];
			InputStream in = null;
			String result = null;
			try {
				URL url = new URL(strUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(10000);
				conn.setReadTimeout(15000);
				conn.setRequestMethod("GET");
				if(conn.getResponseCode() == 200){
					in = conn.getInputStream();
					byte[] buf = new byte[1024];
					in.read(buf);
					result = new String(buf,"utf-8");
				}else{
					Log.i("http", "未能正常工作：" + conn.getResponseCode());
				}
				
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(in != null){
						in.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			mDialog.dismiss();
			if(result != null){
				mText.setText(result);
			}
			
			super.onPostExecute(result);
		}

	}*/

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
