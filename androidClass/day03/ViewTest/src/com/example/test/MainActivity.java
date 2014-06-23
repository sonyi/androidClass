package com.example.test;

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
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private AutoCompleteTextView etPhone;
	private Button btOk,btQuit;
	private EditText etPassword;
	private String[] phoneNumbers = new String[]{"13656012580","13954851326","13015497325","13818425975",
				"15025978136","13642587161"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btOk = (Button) findViewById(R.id.id_ok);
		btQuit = (Button) findViewById(R.id.id_quit);
		etPhone = (AutoCompleteTextView) findViewById(R.id.et_phone);
		etPassword = (EditText) findViewById(R.id.et_password);

		
		// 创建AutoCompleteTextView的数据适配器
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, phoneNumbers);
		// 给AutoCompleteTextView设置适配器
		etPhone.setAdapter(adapter);
		
		
		btOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "点击事件", Toast.LENGTH_SHORT).show();	
			}
		});
		
		btOk.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "触摸事件", Toast.LENGTH_SHORT).show();	
				return false;
			}
		});
		
		btOk.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "长按事件", Toast.LENGTH_SHORT).show();	
				return false;
			}
		});
		
		
		etPhone.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					String phoneNumber = etPhone.getText().toString().trim();
					if(phoneNumber.length() < 11){
						Toast.makeText(MainActivity.this, "号码长度不够", Toast.LENGTH_SHORT).show();
						//etPhone.requestFocus();
					}
				}
			}
		});
		
		btQuit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				System.exit(0);
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
