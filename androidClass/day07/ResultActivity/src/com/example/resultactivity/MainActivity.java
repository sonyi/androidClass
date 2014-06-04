package com.example.resultactivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private static final int RESULT_CODE_ONE = 0x00;
	private static final int RESULT_CODE_TWO = 0x01;
	
	
	Button btnMainOne, btnMainTwo;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnMainOne = (Button) findViewById(R.id.btn_main_one);
		btnMainTwo = (Button) findViewById(R.id.btn_main_second);
		btnMainOne.setOnClickListener(this);
		btnMainTwo.setOnClickListener(this);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != RESULT_OK) {
			return;
		}
		if (requestCode == RESULT_CODE_ONE) {
			String line = data.getStringExtra("info");
			Toast.makeText(this, line, Toast.LENGTH_SHORT).show();
		}
		if (requestCode == RESULT_CODE_TWO) {
			String line = data.getStringExtra("info");
			Toast.makeText(this, line, Toast.LENGTH_SHORT).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_main_one:
			Intent intent1 = new Intent(MainActivity.this, SecondActivity.class);
			startActivityForResult(intent1, RESULT_CODE_ONE);

			break;
		case R.id.btn_main_second:
			Intent intent2 = new Intent(ThirdActivity.ACTION);
			startActivityForResult(intent2,RESULT_CODE_TWO);
			break;
		}
	}

}
