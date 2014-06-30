package com.example.httptest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public final class ConnectivityUtil {
	
	/**
	 * 验证当前设备是否有可用网络
	 * @param context
	 * @return
	 */
	public static boolean isConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null && ni.isAvailable());
	}
	
	/**
	 * 验证当前设备是否是wifi网络连接
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnet(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return (ni != null && ni.isAvailable());
	}
	
	
	/**
	 * 验证当前设备是否是移动运营商网络连接
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnet(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return (ni != null && ni.isAvailable());
	}
}
