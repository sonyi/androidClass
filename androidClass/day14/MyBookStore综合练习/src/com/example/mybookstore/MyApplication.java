package com.example.mybookstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.mybookstore.model.Catagory;
import com.example.mybookstore.util.Literal;


import android.app.Application;
/**
 * 自定义Application，存储少量数据
 * @author Administrator
 *
 */
public class MyApplication extends Application {

	private Map<String, Object> mArgs = new HashMap<String, Object>();
	
	@SuppressWarnings("unchecked")
	public ArrayList<Catagory> getCatagoryArray(){
		return (ArrayList<Catagory>) getArg(Literal.GET_CATAGORY_ARRAY);
	}

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
