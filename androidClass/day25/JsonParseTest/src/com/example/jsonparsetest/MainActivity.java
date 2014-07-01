package com.example.jsonparsetest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView mTv1, mTv2, mTv3, mTv4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTv1 = (TextView) findViewById(R.id.tv_text1);
		mTv2 = (TextView) findViewById(R.id.tv_text2);
		mTv3 = (TextView) findViewById(R.id.tv_text3);
		mTv4 = (TextView) findViewById(R.id.tv_text4);

		parseJsonObject1();
		parseJsonObject2();
		parseJsonArray();
		parseJsonComposite();

	}

	private void parseJsonComposite() {
		/*
		 * 4.数组嵌套在对象中 { "dep_id":1, "dep_name":"develop", "num_of_emp":10,
		 * "leader":{ "emp_id":7, "emp_name":Jack, "age":32 }
		 * 
		 * "emp_list": [ { "emp_id":1, "emp_name":"Billy", "age":25 }, {
		 * "emp_id":2, "emp_name":"Mary", "age":23 }, { "emp_id":3,
		 * "emp_name":"Bob", "age":25 } ] }
		 */
		String json = "{\"dep_id\":1,\"dep_name\":\"develop\",\"num_of_emp\":10,"
				+ "\"leader\":{\"emp_id\":7,\"emp_name\":\"Jack\",\"age\":32},"
				+ "\"emp_list\": [{\"emp_id\":1,\"emp_name\":\"Billy\",\"age\":25},"
				+ "{\"emp_id\":2,\"emp_name\":\"Mary\",\"age\":23},"
				+ "{\"emp_id\":3,\"emp_name\":\"Bob\",\"age\":25}]}";
		StringBuffer buf = new StringBuffer();
		buf.append("4.数组嵌套在对象中 的解析\r\n");
		try {
			JSONObject jo = new JSONObject(json);
			int depId = jo.getInt("dep_id");
			String depName = jo.getString("dep_name");
			int depNum = jo.getInt("num_of_emp");
			buf.append("dep:" + depId + "--" + depName + "--" + depNum + "\r\n");
			
			JSONObject leader = jo.getJSONObject("leader");
			int empId = leader.getInt("emp_id");
			String empName = leader.getString("emp_name");
			int age = leader.getInt("age");
			buf.append("emp:" + empId + "--" + empName + "--" + age + "\r\n");
			
			JSONArray ja = jo.getJSONArray("emp_list");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject j = ja.getJSONObject(i);
				int id = j.getInt("emp_id");
				String name = j.getString("emp_name");
				int a = j.getInt("age");
				buf.append("emp:" + id + "--" + name + "--" + a + "\r\n");
			}
			mTv4.setText(buf.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parseJsonArray() {
		/*
		 * 3.对象数组 [ { "emp_id":1, "emp_name":"Billy", "age":25 }, {
		 * "emp_id":2,"emp_name":"Mary", "age":23 }, { "emp_id":3,
		 * "emp_name":"Bob","age":25 } ]
		 */
		String json = " [{\"emp_id\":1,\"emp_name\":\"Billy\",\"age\":25},"
				+ "{\"emp_id\":2,\"emp_name\":\"Mary\",\"age\":23},"
				+ "{\"emp_id\":3,\"emp_name\":\"Bob\",\"age\":25}]";
		StringBuffer buf = new StringBuffer();
		buf.append("3.JSON对象数组解析\r\n");
		try {
			JSONArray ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				int id = jo.getInt("emp_id");
				String empName = jo.getString("emp_name");
				int age = jo.getInt("age");
				buf.append("emp:" + id + "--" + empName + "--" + age + "\r\n");
			}
			mTv3.setText(buf.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parseJsonObject2() {
		/*
		 * 2.嵌套对象的JSON对象 { "dep_id":1, "dep_name":"develop", "num_of_emp":10,
		 * "leader":{ "emp_id":7, "emp_name":Jack, "age":32 } }
		 */
		String jsonStr1 = "{\"dep_id\":1,\"dep_name\":\"develop\",\"num_of_emp\":10,"
				+ "\"leader\":{\"emp_id\":7,\"emp_name\":\"Jack\",\"age\":32}}";
		try {
			JSONObject jo1 = new JSONObject(jsonStr1);
			int depId = jo1.getInt("dep_id");
			String depName = jo1.getString("dep_name");
			int depNum = jo1.getInt("num_of_emp");

			int empId = jo1.getJSONObject("leader").getInt("emp_id");
			String empName = jo1.getJSONObject("leader").getString("emp_name");
			int empAge = jo1.getJSONObject("leader").getInt("age");
			mTv2.setText("2.嵌套对象的JSON对象解析\r\ndep:" + depId + "--" + depName
					+ "--" + depNum + "\r\n" + "emp:" + empId + "--" + empName
					+ "--" + empAge);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parseJsonObject1() {
		/*
		 * 1.简单JSON对象 {"dep_id":1,"dep_name":"develop","num_of_emp":10}
		 * {"emp_id":1,"emp_name":"Billy","age":25}
		 */
		String jsonStr = "{\"emp_id\":1,\"emp_name\":\"Billy\",\"age\":25}";
		try {
			JSONObject jo = new JSONObject(jsonStr);
			int id = jo.getInt("emp_id");
			String empName = jo.getString("emp_name");
			int age = jo.getInt("age");
			mTv1.setText("1.简单JSON对象解析\r\nemp:" + id + "--" + empName + "--"
					+ age);
			Log.i("msg", id + "--" + empName + "--" + age);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
