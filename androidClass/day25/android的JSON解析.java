package com.example.jsonparsedemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		parseJsonArray();
	}

	// 解析简单JSON对象
	private void parseJsonObject() {
		String jsonStr = "{\"emp_id\":1,\"emp_name\":\"Billy\",\"age\":25}";
		try {
			JSONObject jo = new JSONObject(jsonStr);
			int id = jo.getInt("emp_id");
			String empName = jo.getString("emp_name");
			int age = jo.getInt("age");

			Log.i("id", id + "");
			Log.i("name", empName);
			Log.i("age", age + "");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 解析JSON对象数组
	private void parseJsonArray() {
		String json = "[{\"emp_id\":1,\"emp_name\":\"Billy\",\"age\":25},{\"emp_id\":2,\"emp_name\":\"Mary\",\"age\":23},{\"emp_id\":3,\"emp_name\":\"Bob\",\"age\":25}] ";
		try {
			JSONArray ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				int id = jo.getInt("emp_id");
				String name = jo.getString("emp_name");
				int age = jo.getInt("age");
				
				Log.i("id", id + "");
				Log.i("name", name);
				Log.i("age", age + "");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void parseComplexJson() {
		//
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
