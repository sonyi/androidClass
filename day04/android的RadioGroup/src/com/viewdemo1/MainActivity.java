package com.viewdemo1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
	private RadioGroup rgGander;
	private Button btnOk;

	private String[] mGanderValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 获取xml形式的数组资源，得到一个java数组
		mGanderValues = getResources().getStringArray(R.array.gander_values);

		rgGander = (RadioGroup) findViewById(R.id.rg_gander);
		// rgGander.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		// // checkedId参数表式当前被选中的RadioButton的id
		// switch (checkedId) {
		// case R.id.rb_male:
		// Toast.makeText(MainActivity.this, "男", Toast.LENGTH_SHORT).show();
		// break;
		// case R.id.rb_female:
		// Toast.makeText(MainActivity.this, "女", Toast.LENGTH_SHORT).show();
		// break;
		// case R.id.rb_unknown:
		// Toast.makeText(MainActivity.this, "未知", Toast.LENGTH_SHORT).show();
		// break;
		// }
		// }
		// });

		btnOk = (Button) findViewById(R.id.btn_ok);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String checkedGanderValue = mGanderValues[0];
				
				switch(rgGander.getCheckedRadioButtonId()) {
				case R.id.rb_male:
					checkedGanderValue = mGanderValues[0];
					break;
				case R.id.rb_female:
					checkedGanderValue = mGanderValues[1];
					break;
				case R.id.rb_unknown:
					checkedGanderValue = mGanderValues[2];
					break;
				}
				
				Toast.makeText(MainActivity.this, checkedGanderValue, Toast.LENGTH_SHORT).show();
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
