package com.example.test;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {
	private CheckBox mCheckBoxReading, mCheckBoxSports, mCheckBoxSinging;
	private Button mButtonOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCheckBoxReading = (CheckBox) findViewById(R.id.cb_reading);
		mCheckBoxSports = (CheckBox) findViewById(R.id.cb_sports);
		mCheckBoxSinging = (CheckBox) findViewById(R.id.cb_singing);
		mButtonOk = (Button) findViewById(R.id.bt_ok);

		mButtonOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCheckBoxReading.isChecked()) {
					Log.i("checkBox", "reading");
				}
				if (mCheckBoxSports.isChecked()) {
					Log.i("checkBox", "sport");
				}
				if (mCheckBoxSinging.isChecked()) {
					Log.i("checkBox", "singing");
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
