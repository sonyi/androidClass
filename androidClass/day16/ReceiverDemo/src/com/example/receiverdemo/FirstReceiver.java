package com.example.receiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FirstReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("FirstReceiver", "�������ǵĵ�һ���㲥���������Ѿ������ˡ�");

		int number = getResultExtras(false).getInt("number");
		number *= 5;
		Log.i("FirstReceiver", number + "");
	}
}
