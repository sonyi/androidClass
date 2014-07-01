package com.example.empmgrdemo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class HttpUtil {
	private static final String HOST = "http://192.168.56.1:8080/HelloWeb";

	public static String post(RequestEntity entity) {
		String strUrl = HOST + entity.getUrl();

		InputStream input = null;
		OutputStream output = null;

		// 响应结果，默认设置为服务器内部错误的信息
		String result = "{\"status\":10000}";
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(10000);
			conn.setReadTimeout(15000);

			// 设置请求头信息
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Charset", "utf-8");
			conn.addRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setDoOutput(true);

			// 发送请求参数
			if (entity.getParams() != null) {
				output = conn.getOutputStream();
				String requestParams = parseToUrlEncodedParams(entity
						.getParams());
				output.write(requestParams.getBytes("utf-8"));
			}

			if (conn.getResponseCode() == 200) {
				input = conn.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
				byte[] buffer = new byte[128];
				int len = 0;
				while ((len = input.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				result = new String(baos.toByteArray(), "utf-8");
				baos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
			}
		}

		return result;
	}

	private static String parseToUrlEncodedParams(Map<String, Object> map) {
		StringBuffer strBuffer = new StringBuffer();
		Set<String> keySet = map.keySet();
		Iterator<String> i = keySet.iterator();
		while (i.hasNext()) {
			String key = i.next();
			String value = map.get(key).toString();
			strBuffer.append(key);
			strBuffer.append("=");
			strBuffer.append(value);
			if (i.hasNext()) {
				strBuffer.append("&");
			}
		}
		return strBuffer.toString();
	}
}
