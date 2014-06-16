package com.example.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int num = intent.getIntExtra("num", 0);
		Log.i("receiver", "µÚ¶þ¸öreceiver---" + num);
		
		num += 5;
		Bundle extras = new Bundle();
		extras.putInt("num", num);
		setResultExtras(extras);
		
	}

}
