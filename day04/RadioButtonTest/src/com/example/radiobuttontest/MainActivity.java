package com.example.radiobuttontest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
	String[] mGanderValues;
	RadioGroup mRgGander;
	Button mBtOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBtOk = (Button) findViewById(R.id.bt_ok);
		mRgGander = (RadioGroup) findViewById(R.id.rg_gander);
		mGanderValues = getResources().getStringArray(R.array.gander_values);

		mBtOk.setOnClickListener(new OnClickListener() {
			String checkGanderValues = mGanderValues[0];

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (mRgGander.getCheckedRadioButtonId()) {
				case R.id.rb_male:
					checkGanderValues = mGanderValues[0];
					break;
				case R.id.rb_female:
					checkGanderValues = mGanderValues[1];
					break;
				case R.id.rb_unknown:
					checkGanderValues = mGanderValues[2];
					break;
				}
				Toast.makeText(MainActivity.this, checkGanderValues,
						Toast.LENGTH_SHORT).show();
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
