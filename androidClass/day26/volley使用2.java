package com.example.empmgrdemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.ResponseConnControl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends Activity {
	private ListView lvEmpList;
	private EmpAdapter mAdapter;
	private List<Employee> mEmpList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lvEmpList = (ListView) findViewById(R.id.lv_emp_list);

		getEmpList();
	}

	@SuppressWarnings("unchecked")
	private void getEmpList() {
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("cid", 1);
		//
		// RequestEntity entity = new RequestEntity("/demo", params);
		// new HttpAsyncTask().execute(entity);

		// Use Volley
		String url = "http://192.168.1.148:8080/HelloWeb/demo";
		StringRequest request = new StringRequest(Request.Method.POST, url,
				mResponseSuccess, mErrorListener) {
			@Override
			protected Map<String, String> getParams()
					throws AuthFailureError {
				Map<String,String> params = new HashMap<String, String>();
				params.put("cid", "1");
				return params;
			}
		};
		
		// 写法1：
		// RequestQueue queue = RequestQueueSingleton.getInstance(this).getRequestQueue();
		// queue.add(request);
		
		// 写法2:
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
	}

	private Response.Listener<String> mResponseSuccess = new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			// 在这里添加正确得到响应之后的处理
			Log.i("Http", response);
			int status = Integer.parseInt(JsonUtil.getJsonValueByKey(response,
					"status"));
			if (status == 200) {
				String data = JsonUtil.getJsonValueByKey(response, "data");
				mEmpList = JsonUtil.toObjectList(data, Employee.class);
				if (mAdapter == null) {
					mAdapter = new EmpAdapter();
					lvEmpList.setAdapter(mAdapter);
				} else {
					mAdapter.notifyDataSetChanged();
				}
			} else {
				String message = JsonUtil
						.getJsonValueByKey(response, "message");
				Toast.makeText(MainActivity.this, "请求服务器发生错误:" + message,
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(MainActivity.this, "请求服务器发生错误", Toast.LENGTH_SHORT)
					.show();
		}
	};

	private class EmpAdapter extends BaseAdapter {

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
