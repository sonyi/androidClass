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
 * �Զ������󣬵õ�����Ӧ��Employee�����List����
 * 
 * @author user
 */
public class EmpListRequest extends Request<List<Employee>> {
	private Response.Listener<List<Employee>> mListener;

	/**
	 * ���캯��
	 * 
	 * @param method
	 *            ���󷽷�
	 * @param url
	 *            ������api��ַ
	 * @param listener
	 *            ��Ӧ����ʱ�ļ�����
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
