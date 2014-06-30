package com.example.httptest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ConnectChangeReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if(!ConnectivityUtil.isConnect(context)){
			Toast.makeText(context, "当前网络关闭", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(ConnectivityUtil.isWifiConnet(context)){
			Toast.makeText(context, "当前是WiFi连接", Toast.LENGTH_SHORT).show();
		}
		
		if(ConnectivityUtil.isMobileConnet(context)){
			Toast.makeText(context, "当前是移动网络连接", Toast.LENGTH_SHORT).show();
		}
	}

}
