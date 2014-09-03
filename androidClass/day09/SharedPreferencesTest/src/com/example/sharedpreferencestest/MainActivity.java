package com.example.sharedpreferencestest;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText etUserName, etPassWord;
	Button btnSave, btnRead;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etUserName = (EditText) findViewById(R.id.et_username);
		etPassWord = (EditText) findViewById(R.id.et_password);
		btnSave = (Button) findViewById(R.id.btn_ok);
		btnRead = (Button) findViewById(R.id.btn_read);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				saveUsr();
			}
		});
		
		btnRead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				read();
			}
		});
	}

	
	private void saveUsr() {
		String userName = etUserName.getText().toString();
		String passWord = etPassWord.getText().toString();
		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("username", userName);
		editor.putString("pwd", passWord);
		editor.putInt("id", 7);
		editor.putBoolean("haveMarried", true);		
		editor.commit();
	}
	
	private void read(){
		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		String userName = sp.getString("username", "");
		String userPassword = sp.getString("pwd", "");
		int id = sp.getInt("id", 0);
		boolean hasMarried = sp.getBoolean("havaMarried", false);
		Log.i("msg", "userName:" + userName + "userPassword:" + userPassword + "id:" + id + "hasMarried:" + hasMarried);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		String favorite = sp.getString("favorite", "");
		Toast.makeText(this, favorite, Toast.LENGTH_SHORT).show();
		super.onResume();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.action_settings){
			Intent intent = new Intent(this,SecondActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
