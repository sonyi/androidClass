package com.example.empmgrdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

/**
 * volley请求队列单例模式
 * 
 * @author Administrator
 * 
 */
public class RequsetQueueSingleton {
	private static RequsetQueueSingleton mInstance;
	private RequestQueue mRequestQueue;

	private RequsetQueueSingleton(Context context) {
		Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);

		String userAgent = "volley/0";
		try {
			String packageName = context.getPackageName();
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, 0);
			userAgent = packageName + "/" + info.versionCode;
		} catch (NameNotFoundException e) {
		}

		HttpStack stack = null;
		if (Build.VERSION.SDK_INT >= 9) {
			stack = new HurlStack();
		} else {
			// Prior to Gingerbread, HttpUrlConnection was unreliable.
			// See:
			// http://android-developers.blogspot.com/2011/09/androids-http-clients.html
			stack = new HttpClientStack(
					AndroidHttpClient.newInstance(userAgent));
		}

		Network network = new BasicNetwork(stack);
		mRequestQueue = new RequestQueue(cache, network);
		mRequestQueue.start();
	}

	public static RequsetQueueSingleton getRequsetQueue(Context context){
		if(mInstance == null){
			mInstance = new RequsetQueueSingleton(context);
		}
		return mInstance;
	}
	
	public RequestQueue getRequest(){
		return mRequestQueue;
	}
	
	public void addRequest(Request request){
		mRequestQueue.add(request);
	}
	
	public void stopRequest(){
		mRequestQueue.stop();
	}
}
