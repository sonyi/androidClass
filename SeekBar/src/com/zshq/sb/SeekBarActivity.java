package com.zshq.sb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zshq.sb.MySeekBar.OnSeekBarChangeListener;

public class SeekBarActivity extends Activity {

	private MySeekBar sb = null;
	private TextView tv = null;

	public static final String TAG = "SeekBarActivity";
	public static final boolean DEBUG = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (DEBUG) {
			Log.d(TAG, "onCreate");
		}

		findView();
		setListener();
	}

	private void setListener() {

		sb.setOnSeekBarChangeListener(onSeekBarChangeListener);

	}

	private void findView() {

		sb = (MySeekBar) findViewById(R.id.sb);
		tv = (TextView) findViewById(R.id.tv);

	}

	private OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(MySeekBar VerticalSeekBar, int progress,
				boolean fromUser) {

			if (DEBUG) {
				Log.d(TAG, "Current progress: " + progress);
			}
			tv.setText("½ø¶È" + progress);
		}

		@Override
		public void onStartTrackingTouch(MySeekBar VerticalSeekBar) {

			if (DEBUG) {
				Log.d(TAG, "Start progress: " + VerticalSeekBar.getProgress());
			}

		}

		@Override
		public void onStopTrackingTouch(MySeekBar VerticalSeekBar) {

			if (DEBUG) {
				Log.d(TAG, "Stop progress: " + VerticalSeekBar.getProgress());
			}

		}

	};
}