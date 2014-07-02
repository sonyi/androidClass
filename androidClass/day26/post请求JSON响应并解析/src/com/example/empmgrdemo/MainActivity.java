package com.example.empmgrdemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Paint.Join;
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

public class MainActivity extends Activity {
	private ProgressDialog pbProgress;

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

	private void getEmpList() {
		//noUseVolley();
		//useVolley();
		//useVolleyByGET();
		useVolleyByPost();
	}

	private void useVolley() {
		//使用volley，不自定义RequestQueue
		String url = "http://192.168.1.148:8080/HelloWeb/demo?cid=1";
		StringRequest request = new StringRequest(Request.Method.GET, url, mResponseSuccess, mErrorListener);
		RequestQueue queue = Volley.newRequestQueue(this);
		queue.add(request);
	}

	private void useVolleyByGET() {
		//使用volley，自定义RequestQueue，并用GET传值
		
		String url = "http://192.168.1.148:8080/HelloWeb/demo?cid=1";
		StringRequest request = new StringRequest(Request.Method.GET, url, mResponseSuccess, mErrorListener);
		
		//方式一：
//		RequestQueue queue = RequsetQueueSingleton.getRequsetQueue(this).getRequest();
//		queue.add(request);
		
		//方式二
		RequsetQueueSingleton.getRequsetQueue(this).addRequest(request);
	}

	private void useVolleyByPost() {
		//使用volley，自定义RequestQueue，并用POST传值
		
		String url = "http://192.168.1.148:8080/HelloWeb/demo";
		StringRequest request = new StringRequest(Request.Method.POST, url, mResponseSuccess, mErrorListener){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("cid", "1");
				return params;
			}
		};
		RequsetQueueSingleton.getRequsetQueue(this).addRequest(request);
	}

	private void noUseVolley() {
		//不使用volley
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cid", 1);

		RequestEntity entity = new RequestEntity("/demo", params);
		new HttpAsyncTask().execute(entity);
	}
	
	private Response.Listener<String> mResponseSuccess = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			Toast.makeText(MainActivity.this, "response", Toast.LENGTH_SHORT).show();
			int status = Integer.parseInt(JsonUtil.getJsonValueByKey(response,
					"status"));
			if (status == 200) {
				String data = JsonUtil.getJsonValueByKey(response, "data");
				mEmpList = JsonUtil.toObjectList(data, Employee.class);
         
				// 一定要在异步任务的onPostExcute()方法，获取到响应数据之后，再刷新界面
				if (mAdapter == null) {
					mAdapter = new EmpAdapter();
					lvEmpList.setAdapter(mAdapter);
				} else {
					mAdapter.notifyDataSetChanged();
				}
				
			} else {
				String message = JsonUtil.getJsonValueByKey(response, "message");
				Toast.makeText(MainActivity.this, "请求发送错误：" + message,
						Toast.LENGTH_SHORT).show();
			}
			
		}
	};
	
	private Response.ErrorListener mErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
		}
	};

	
	
	
	private class HttpAsyncTask extends AsyncTask<RequestEntity, Void, String> {
		@Override
		protected void onPreExecute() {
			pbProgress = ProgressDialog.show(MainActivity.this, "请稍候",
					"正在执行请求...");
		}

		@Override
		protected String doInBackground(RequestEntity... params) {
			return HttpUtil.post(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			pbProgress.dismiss();
			Log.i("Emp", result);

			if (JsonUtil.isJsonNull(result)) {
				return;
			}
			int status = Integer.parseInt(JsonUtil.getJsonValueByKey(result,
					"status"));
			if (status == 200) {
				String data = JsonUtil.getJsonValueByKey(result, "data");
				mEmpList = JsonUtil.toObjectList(data, Employee.class);
         
				// 一定要在异步任务的onPostExcute()方法，获取到响应数据之后，再刷新界面
				if (mAdapter == null) {
					mAdapter = new EmpAdapter();
					lvEmpList.setAdapter(mAdapter);
				} else {
					mAdapter.notifyDataSetChanged();
				}
				
			} else {
				String message = JsonUtil.getJsonValueByKey(result, "message");
				Toast.makeText(MainActivity.this, "请求发送错误：" + message,
						Toast.LENGTH_SHORT).show();
			}

			super.onPostExecute(result);
		}
	}

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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	
		super.onDestroy();
	}

}
