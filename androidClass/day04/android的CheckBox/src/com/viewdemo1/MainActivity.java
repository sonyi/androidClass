package com.viewdemo1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity {
	private CheckBox cbReading;
	private CheckBox cbSports;
	private CheckBox cbGirls;

	private Button btnOk;

	// private List<String> mCheckedTextList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cbReading = (CheckBox) findViewById(R.id.cb_reading);
		cbSports = (CheckBox) findViewById(R.id.cb_sports);
		cbGirls = (CheckBox) findViewById(R.id.cb_girls);
		btnOk = (Button) findViewById(R.id.btn_ok);

		// cbReading.setOnCheckedChangeListener(this);
		// cbSports.setOnCheckedChangeListener(this);
		// cbGirls.setOnCheckedChangeListener(this);

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cbReading.isChecked()) {
					Log.i("view_demo", cbReading.getText().toString());
				}
				if (cbSports.isChecked()) {
					Log.i("view_demo", cbSports.getText().toString());
				}
				if (cbGirls.isChecked()) {
					Log.i("view_demo", cbGirls.getText().toString());
				}
			}
		});
	}

	// @Override
	// public void onCheckedChanged(CompoundButton buttonView, boolean
	// isChecked) {
	// switch (buttonView.getId()) {
	// case R.id.cb_reading:
	// if (isChecked) {
	// mCheckedTextList.add(cbReading.getText().toString());
	// }
	// break;
	// case R.id.cb_sports:
	// if (isChecked) {
	// mCheckedTextList.add(cbSports.getText().toString());
	// }
	// break;
	// case R.id.cb_girls:
	// if (isChecked) {
	// mCheckedTextList.add(cbGirls.getText().toString());
	// }
	// break;
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
