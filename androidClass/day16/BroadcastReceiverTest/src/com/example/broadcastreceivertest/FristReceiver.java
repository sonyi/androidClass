package com.example.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FristReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//int num = intent.getIntExtra("num", 0);
		//Log.i("receiver", "��һ��receiver-----" + num);
		int num = getResultExtras(false).getInt("num");
		Log.i("receiver", "��һ��receiver-----" + num);
	}

}
