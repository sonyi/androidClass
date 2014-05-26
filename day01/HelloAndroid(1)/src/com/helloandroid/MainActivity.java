package com.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private TextView tvInfo;
	private Button btnBlue;
	private Button btnRed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 引用布局资源的方式
		setContentView(R.layout.activity_main);

		// 在setContentView()方法后加载控件实例
		tvInfo = (TextView) findViewById(R.id.tv_text);
		btnBlue = (Button) findViewById(R.id.btn_blue);
		btnRed = (Button) findViewById(R.id.btn_red);

		btnBlue.setOnClickListener(this);
		btnRed.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_blue:
			tvInfo.setBackgroundResource(R.color.blue);
			break;
		case R.id.btn_red:
			tvInfo.setBackgroundResource(R.color.pink);
			break;
		}
	}
}
