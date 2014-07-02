package com.example.empmgrdemo;

import java.util.List;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * 自定义请求，得到的响应是Employee对象的List集合
 * 
 * @author user
 */
public class EmpListRequest extends Request<List<Employee>> {
	private Response.Listener<List<Employee>> mListener;

	/**
	 * 构造函数
	 * 
	 * @param method
	 *            请求方法
	 * @param url
	 *            服务器api地址
	 * @param listener
	 *            响应错误时的监听器
	 */
	public EmpListRequest(int method, String url,
			Response.Listener<List<Employee>> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	@Override
	protected Response<List<Employee>> parseNetworkResponse(
			NetworkResponse response) {
		try {
			String json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			Log.i("EmpListRequest", json);
			int status = Integer.parseInt(JsonUtil.getJsonValueByKey(json,
					"status"));
			if (status == 200) {
				String data = JsonUtil.getJsonValueByKey(json, "data");
				List<Employee> empList = JsonUtil.toObjectList(data,
						Employee.class);
				return Response.success(empList,
						HttpHeaderParser.parseCacheHeaders(response));
			} else {
				String message = JsonUtil.getJsonValueByKey(json, "message");
				return Response.error(new VolleyError(message));
			}
		} catch (Exception e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(List<Employee> response) {
		mListener.onResponse(response);		
	}
}
