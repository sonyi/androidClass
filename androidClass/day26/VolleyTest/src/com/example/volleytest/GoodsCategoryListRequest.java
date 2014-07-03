package com.example.volleytest;

import java.util.List;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;


public class GoodsCategoryListRequest extends Request<List<GoodsCategory>>{
	private Listener<List<GoodsCategory>> mListener;
	
	/**
	 * 
	 * @param method 请求方法
	 * @param url  服务器api地址
	 * @param listener  响应正确时监听器
	 * @param errorlistener  响应错误时监听器
	 */
	public GoodsCategoryListRequest(int method, String url,
			Listener<List<GoodsCategory>> listener,
			ErrorListener errorlistener) {
		super(method, url, errorlistener);
		mListener = listener;
		
	}

	@Override
	protected Response<List<GoodsCategory>> parseNetworkResponse(
			NetworkResponse response) {
		try {
			String json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			Log.i("Request", json);
			int status = Integer.parseInt(JsonUtil.getJsonValueByKey(json,
					"status"));
			if (status == 200) {
				String data = JsonUtil.getJsonValueByKey(json, "data");
				List<GoodsCategory> list = JsonUtil.toObjectList(data,
						GoodsCategory.class);
				Log.i("Request", list.toString());
				return Response.success(list,
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
	protected void deliverResponse(List<GoodsCategory> response) {
		mListener.onResponse(response);
	}
}
