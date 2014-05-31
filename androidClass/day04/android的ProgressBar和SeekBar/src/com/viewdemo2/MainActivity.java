package com.viewdemo2;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	private ProgressBar pbProgress;

	private SeekBar seekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
		seekBar = (SeekBar) findViewById(R.id.seek_progress);

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				Log.i("seek_bar", "onStartTrackingTouch()");
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Log.i("seek_bar", "onStopTrackingTouch()");
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					Log.i("seek_bar", "onProgressChanged()");
				}
			}
		});
	}

	@Override
	protected void onStart() {
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				int progress = pbProgress.getProgress();
				progress += 1;
				pbProgress.setProgress(progress);

				// seekBar
				int progress1 = seekBar.getProgress();
				progress1 += 1;
				seekBar.setProgress(progress1);
			}
		};

		Timer timer = new Timer();
		// 使用计时器对象定期执行操作
		// 参数1.定期执行的操作所在的任务 (TimerTask对象)
		// 参数2.多久以后开始执行
		// 参数3.每隔多久执行一次
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
