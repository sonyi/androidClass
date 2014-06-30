package com.example.httpdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ConnectChangeReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
          if (!ConnectivityUtil.isConnect(context)) {
        	  Toast.makeText(context, "当前网络连接已关闭", Toast.LENGTH_SHORT).show();
              return;
          } 
          if (ConnectivityUtil.isWifiConnect(context)) {
        	  Toast.makeText(context, "当前是wifi连接", Toast.LENGTH_SHORT).show();
          }
          
          if (ConnectivityUtil.isMobileConnect(context)) {
        	  Toast.makeText(context, "当前是移动数据网络连接", Toast.LENGTH_SHORT).show();
          }
	}
}
