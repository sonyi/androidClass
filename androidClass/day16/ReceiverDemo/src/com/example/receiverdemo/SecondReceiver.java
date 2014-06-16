package com.example.receiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("FirstReceiver", "�������ǵĵڶ����㲥���������Ѿ������ˡ�"); 
		int number = intent.getIntExtra("number", 0);
		number += 5;
		
		Bundle extras = new Bundle();
		extras.putInt("number", number);
		
		setResultExtras(extras);
	}
}
