package com.mymusicplay.server;

import java.util.Calendar;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.format.Time;
import android.util.Log;

import com.mymusicplay.PlayBackServiceManager;

public class MySensorEventListener implements SensorEventListener {
	private static final int SENSOR_SHAKE = 10;
	private Vibrator vibrator;
	private Context context;
	private long oldTime = 0;
	
	
	MySensorEventListener(Context context,Vibrator vibrator){
		this.vibrator = vibrator;
		this.context = context;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		//传感器信息改变时执行该方法
		float[] values = event.values;
		float x = values[0]; // x轴方向的重力加速度，向右为正
		float y = values[1]; //  y轴方向的重力加速度，向前为正
		float z = values[2]; //z轴方向的重力加速度，向上为正
		//Time t=new Time();
		
		Calendar c = Calendar.getInstance(); 
		long time = c.getTimeInMillis();
		//Log.i("sign", time + "");
		int medumValue = 15;
		if (Math.abs(x) > medumValue || Math.abs(y) > medumValue
				|| Math.abs(z) > medumValue) {
			if((time - oldTime) > 1000){
				Message msg = new Message();
				msg.what = SENSOR_SHAKE;
				handler.sendMessage(msg);
				vibrator.vibrate(100);
				
			}
			oldTime = time;
		}
	}
	
	/**
	 * 动作执行
	 */
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SENSOR_SHAKE:
				IPlayBackService myService = PlayBackServiceManager.getPlayBackService(context);
				if(myService.getCurrentPlayState() == PlayStaticConst.STATE_PLAYING){
					myService.next();//切换到下一首
				}
				break;
			}
		}
	};

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}


