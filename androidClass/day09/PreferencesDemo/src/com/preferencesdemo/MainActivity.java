package com.preferencesdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText etUserName;
	private EditText etPassword;
	
	private Button btnSave;
	private Button btnRead;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etUserName = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnSave = (Button) findViewById(R.id.btn_save);
		btnRead = (Button) findViewById(R.id.btn_read);
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveUser();
			}
		});
		
		btnRead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				read();
			}
		});
	}
	
	@Override
	protected void onResume() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		boolean allowLand = sp.getBoolean("allowLand", false);
		Toast.makeText(this, "‘ –Ì∫·∆¡£∫" + allowLand, Toast.LENGTH_SHORT).show();
		
		super.onResume();
	}
	
	private void saveUser() {
		String userName = etUserName.getText().toString();
		String password = etPassword.getText().toString();
		
		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("username", userName);
		editor.putString("pwd", password);
		
		editor.putInt("id", 7);
		editor.putBoolean("hasMarried", false);
		
		editor.commit();
	}
	
	private void read() {
		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		String userName = sp.getString("username", "");
		int id = sp.getInt("id", 1);
		boolean hasMarried = sp.getBoolean("hasMarried", false);
		
		Toast.makeText(this, id +"," + userName + "ªÈ∑Ò£∫" + hasMarried, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() ==R.id.action_settings) {
			Intent intent = new Intent(this,SettingActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
