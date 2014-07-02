package com.example.empmgrdemo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class MainActivity extends Activity {
	private ListView lvEmpList;
	private EmpAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lvEmpList = (ListView) findViewById(R.id.lv_emp_list);

		getEmpList();
	}

	private void getEmpList() {
		// Use Volley
		String url = "http://192.168.1.148:8080/HelloWeb/demo?cid=1";

		// 使用自定义Request
		EmpListRequest request = new EmpListRequest(Request.Method.GET, url,
				mResponseSuccess, mErrorListener);

		// 写法1：
		// RequestQueue queue =
		// RequestQueueSingleton.getInstance(this).getRequestQueue();
		// queue.add(request);

		// 写法2:
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
	}

	private Response.Listener<List<Employee>> mResponseSuccess = new Response.Listener<List<Employee>>() {
		@Override
		public void onResponse(List<Employee> response) {
			if (mAdapter == null) {
				mAdapter = new EmpAdapter(response);
				lvEmpList.setAdapter(mAdapter);
			} else {
				mAdapter.notifyDataSetChanged();
			}
		}
	};

	private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(MainActivity.this, "请求服务器发生错误:" + error.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	};

	private class EmpAdapter extends BaseAdapter {
		private List<Employee> mEmpList;

		public EmpAdapter(List<Employee> empList) {
			mEmpList = empList;
		}
		
		public void setEmpList(List<Employee> empList) {
			mEmpList = empList;
		}

		@Override
		public int getCount() {
			return mEmpList.size();
		}

		@Override
		public Object getItem(int position) {
			return mEmpList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mEmpList.get(position).id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(
					android.R.layout.simple_list_item_1, null);
			TextView text1 = (TextView) view.findViewById(android.R.id.text1);

			Employee emp = mEmpList.get(position);
			String content = emp.id + "	" + emp.empName + "	" + emp.age;
			text1.setText(content);
			return view;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
