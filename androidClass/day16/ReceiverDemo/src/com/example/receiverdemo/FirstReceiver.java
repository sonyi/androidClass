package com.example.receiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FirstReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("FirstReceiver", "这是我们的第一个广播接收器，已经工作了。");

		int number = getResultExtras(false).getInt("number");
		number *= 5;
		Log.i("FirstReceiver", number + "");
	}
}
