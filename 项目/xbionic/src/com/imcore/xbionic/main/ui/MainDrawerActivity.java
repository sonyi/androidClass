package com.imcore.xbionic.main.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imcore.xbionic.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainDrawerActivity extends Activity{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private View mDrawerUserDetail;
	private String[] mNaviItemText;
	private final static String NAVI_ITEM_TEXT = "text";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_drawer);
		initDrawerLayout();
		
	}
	
	private void initDrawerLayout() {
		mNaviItemText = getResources().getStringArray(R.array.drawer_item_array);
		//mDrawerTitle = getResources().getString(R.string.app_name);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
		
		
		
		
		Map<String, Object> item1 = new HashMap<String, Object>();
		item1.put(NAVI_ITEM_TEXT, mNaviItemText[0]);
		
		Map<String, Object> item2 = new HashMap<String, Object>();
		item2.put(NAVI_ITEM_TEXT, mNaviItemText[1]);
		
		Map<String, Object> item3 = new HashMap<String, Object>();
		item3.put(NAVI_ITEM_TEXT, mNaviItemText[2]);
		
		Map<String, Object> item4 = new HashMap<String, Object>();
		item4.put(NAVI_ITEM_TEXT, mNaviItemText[3]);
		
		Map<String, Object> item5 = new HashMap<String, Object>();
		item5.put(NAVI_ITEM_TEXT, mNaviItemText[4]);
		
		Map<String, Object> item6 = new HashMap<String, Object>();
		item6.put(NAVI_ITEM_TEXT, mNaviItemText[5]);
		
		Map<String, Object> item7 = new HashMap<String, Object>();
		item7.put(NAVI_ITEM_TEXT, mNaviItemText[6]);
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		data.add(item1);
		data.add(item2);
		data.add(item3);
		data.add(item4);
		data.add(item5);
		data.add(item6);
		data.add(item7);
		
		String[] from = new String[] { NAVI_ITEM_TEXT};
		int[] to = new int[] { R.id.tv_navi_item_text};
		mDrawerList = (ListView) findViewById(R.id.lv_drawer_list);
		mDrawerList.setAdapter(new SimpleAdapter(this, data,
				R.layout.view_navi_drawer_item, from, to));
		
	}
	
}
