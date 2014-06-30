package com.example.httpdemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class ConnectivityUtil {

	/**
	 * 验证当前设备是否具有可用的网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null && ni.isAvailable());
	}

	/**
	 * 验证当前设备的当前网络连接是否是wifi连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return (ni != null && ni.isAvailable());
	}

	/**
	 * 验证当前设备的当前网络连接是否具有移动运营商网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return (ni != null && ni.isAvailable());
	}
}
