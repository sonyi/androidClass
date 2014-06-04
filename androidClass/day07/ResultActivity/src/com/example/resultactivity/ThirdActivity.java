package com.example.resultactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ThirdActivity extends Activity implements OnClickListener {
	private Button btnThirdOk, btnThirdQuit;
	public static final String ACTION = "com.resultactivity.ACTION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		btnThirdOk = (Button) findViewById(R.id.btn_third_ok);
		btnThirdQuit = (Button) findViewById(R.id.btn_third_quit);
		btnThirdOk.setOnClickListener(this);
		btnThirdQuit.setOnClickListener(this);
		
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
		case R.id.btn_third_ok:
			Intent intent = new Intent();
			intent.putExtra("info", "爱你一直不变");
			setResult(RESULT_OK,intent);
			finish();
			break;
		case R.id.btn_third_quit:
			setResult(RESULT_CANCELED);
			finish();
			break;

		}

	}

}
