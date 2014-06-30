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
		// ��������Ϣ�ı�ʱִ�и÷���
		float[] values = event.values;
		float x = values[0]; // x�᷽����������ٶȣ�����Ϊ��
		float y = values[1]; // y�᷽����������ٶȣ���ǰΪ��
		float z = values[2]; // z�᷽����������ٶȣ�����Ϊ��
		//Log.i("TAG", "x�᷽����������ٶ�" + x + "��y�᷽����������ٶ�" + y + "��z�᷽����������ٶ�" + z);
		// һ����������������������ٶȴﵽ40�ʹﵽ��ҡ���ֻ���״̬��
		int medumValue = 15;// ���� i9250��ô�ζ����ᳬ��20��û�취��ֻ����19��
		if (Math.abs(x) > medumValue || Math.abs(y) > medumValue
				|| Math.abs(z) > medumValue) {
			
			Message msg = new Message();
			msg.what = SENSOR_SHAKE;
			handler.sendMessage(msg);
			vibrator.vibrate(100);
			try {
				Thread.sleep(800);//��ֹһ��������
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ����ִ��
	 */
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SENSOR_SHAKE:
				IPlayBackService myService = PlayBackServiceManager.getPlayBackService(context);
				if(myService.getCurrentPlayState() == PlayStaticConst.STATE_PLAYING){
					myService.next();//�л�����һ��
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


