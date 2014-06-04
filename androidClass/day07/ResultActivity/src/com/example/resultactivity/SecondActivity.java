package com.example.resultactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondActivity extends Activity implements OnClickListener {
	private Button btnSecondOk, btnSecondQuit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		btnSecondOk = (Button) findViewById(R.id.btn_second_ok);
		btnSecondQuit = (Button) findViewById(R.id.btn_second_quit);
		btnSecondOk.setOnClickListener(this);
		btnSecondQuit.setOnClickListener(this);
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
		case R.id.btn_second_ok:
			Intent intent = new Intent();
			intent.putExtra("info", "ÄãºÃ£¬Ð»Ð»£¬¹þ¹þ");
			setResult(RESULT_OK,intent);
			finish();
			break;
		case R.id.btn_second_quit:
			setResult(RESULT_CANCELED);
			finish();
			break;

		}

	}

}
