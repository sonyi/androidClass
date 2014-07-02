package com.example.downloadimgtest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView mImg;
	private Button mBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImg = (ImageView) findViewById(R.id.iv_download);
		mBtn = (Button) findViewById(R.id.btn);
		
		mBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String urlStr = "http://image.baidu.com/i?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimg4.cache.netease.com%2Fhome%2F2014%2F5%2F30%2F20140530112706144e2.jpg";
				URL url = null;
				try {
					url = new URL(urlStr);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				new DownLoadImgTask().execute(url);
			}
		});
	}
	
	private class DownLoadImgTask extends AsyncTask<URL, Void, ByteArrayOutputStream>{

		@Override
		protected ByteArrayOutputStream doInBackground(URL... params) {
			URL url = params[0];
			HttpURLConnection conn;
			ByteArrayOutputStream baos = null;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(10000);
				conn.setReadTimeout(15000);
				conn.setRequestMethod("GET");
				
				InputStream in;
				if(conn.getResponseCode() == 200){
					in = conn.getInputStream();
					
					baos = new ByteArrayOutputStream(128);
					byte[] buffer = new byte[128];
					int len = 0;
					while ((len = in.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
					}
					baos.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return baos;
		}

		@Override
		protected void onPostExecute(ByteArrayOutputStream result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null){
				byte[] data = result.toByteArray(); 
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);  //Éú³ÉÎ»Í¼  
				mImg.setImageBitmap(bitmap);
			}
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
