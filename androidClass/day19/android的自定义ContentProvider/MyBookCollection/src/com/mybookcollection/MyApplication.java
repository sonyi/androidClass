package com.mybookcollection;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;

public class MyApplication extends Application {
	private  Map<String, Object> mArgs = new HashMap<String, Object>();

	public Object getArg(String key) {
		return mArgs.get(key);
	}
	
	public void addArg(String key, Object value) {
		mArgs.put(key, value);
	}
	
	public void removeArg(String key) {
		mArgs.remove(key);
	}
}
