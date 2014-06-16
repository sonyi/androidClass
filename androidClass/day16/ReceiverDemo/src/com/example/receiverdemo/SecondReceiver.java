package com.example.receiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("FirstReceiver", "这是我们的第二个广播接收器，已经工作了。"); 
		int number = intent.getIntExtra("number", 0);
		number += 5;
		
		Bundle extras = new Bundle();
		extras.putInt("number", number);
		
		setResultExtras(extras);
	}
}
