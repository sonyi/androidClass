package com.example.httptest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ConnectChangeReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if(!ConnectivityUtil.isConnect(context)){
			Toast.makeText(context, "��ǰ����ر�", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(ConnectivityUtil.isWifiConnet(context)){
			Toast.makeText(context, "��ǰ��WiFi����", Toast.LENGTH_SHORT).show();
		}
		
		if(ConnectivityUtil.isMobileConnet(context)){
			Toast.makeText(context, "��ǰ���ƶ���������", Toast.LENGTH_SHORT).show();
		}
	}

}
