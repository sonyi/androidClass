package com.example.viewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private AutoCompleteTextView etPhoneNumber;
	private EditText etPassword;

	private Button btnOk;
	private Button btnCancel;

	private String[] mPhoneNumbers = new String[] { "13654687458",
			"13698456785", "13956487512", "13955555554", "13845555555",
			"15233333333" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnOk = (Button) findViewById(R.id.btn_ok);
		btnCancel = (Button) findViewById(R.id.btn_cancel);

		etPhoneNumber = (AutoCompleteTextView) findViewById(R.id.et_phone_number);
		// 创建AutoCompleteTextView的数据适配器
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, mPhoneNumbers);
		// 给AutoCompleteTextView设置适配器
		etPhoneNumber.setAdapter(adapter);

		etPassword = (EditText) findViewById(R.id.et_password);

		// 设置onTouch事件
		btnOk.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Toast.makeText(MainActivity.this, "触摸事件", Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});

		// 设置单击事件监听
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "单击事件", Toast.LENGTH_SHORT)
						.show();
			}
		});

		btnOk.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(MainActivity.this, "长按事件", Toast.LENGTH_SHORT)
						.show();
				return false;
			}
		});

		etPhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					String phoneNumber = etPhoneNumber.getText().toString()
							.trim();
					if (phoneNumber.length() < 11) {
						Toast.makeText(MainActivity.this, "手机号必须为11位数字",
								Toast.LENGTH_SHORT).show();
						// etPhoneNumber.requestFocus();
					}
				}
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
