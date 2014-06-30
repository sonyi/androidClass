package com.example.httptest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public final class ConnectivityUtil {
	
	/**
	 * ��֤��ǰ�豸�Ƿ��п�������
	 * @param context
	 * @return
	 */
	public static boolean isConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null && ni.isAvailable());
	}
	
	/**
	 * ��֤��ǰ�豸�Ƿ���wifi��������
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnet(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return (ni != null && ni.isAvailable());
	}
	
	
	/**
	 * ��֤��ǰ�豸�Ƿ����ƶ���Ӫ����������
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnet(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return (ni != null && ni.isAvailable());
	}
}
