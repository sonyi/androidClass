package com.example.progressbartest;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	ProgressBar progressBar;
	private SeekBar seekBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressBar = (ProgressBar) findViewById(R.id.pb_progress);	
		seekBar = (SeekBar) findViewById(R.id.seek_progress);
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				Log.i("seekBar", "onStopTrackingTouch");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				Log.i("seekBar", "onStartTrackingTouch");
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				//Log.i("seekBar", "onProgressChanged");
			}
		});
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		TimerTask task = new TimerTask() {	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int progress = progressBar.getProgress();
				progress += 1;
				progressBar.setProgress(progress);
				
				int seekProgress = seekBar.getProgress();
				seekProgress += 1;
				seekBar.setProgress(seekProgress);
			}
		};
		
		Timer timer = new Timer();
		//使用计时器对象定期执行操作
		//参数1：定期执行的操作所在的任务(TimerTask对象)
		//参数2：多久以后开始执行
		//参数3：每隔多久执行一次
		timer.schedule(task, 1, 1);
		super.onStart();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
