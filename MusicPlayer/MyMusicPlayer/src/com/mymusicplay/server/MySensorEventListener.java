package com.mymusicplay.server;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;

import com.mymusicplay.PlayBackServiceManager;

public class MySensorEventListener implements SensorEventListener {
	private static final int SENSOR_SHAKE = 10;
	Vibrator vibrator;
	Context context;
	
	MySensorEventListener(Context context,Vibrator vibrator){
		this.vibrator = vibrator;
		this.context = context;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// 传感器信息改变时执行该方法
		float[] values = event.values;
		float x = values[0]; // x轴方向的重力加速度，向右为正
		float y = values[1]; // y轴方向的重力加速度，向前为正
		float z = values[2]; // z轴方向的重力加速度，向上为正
		//Log.i("TAG", "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" + z);
		// 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
		int medumValue = 15;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
		if (Math.abs(x) > medumValue || Math.abs(y) > medumValue
				|| Math.abs(z) > medumValue) {
			
			Message msg = new Message();
			msg.what = SENSOR_SHAKE;
			handler.sendMessage(msg);
			vibrator.vibrate(100);
			try {
				Thread.sleep(800);//防止一次跳几首
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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


