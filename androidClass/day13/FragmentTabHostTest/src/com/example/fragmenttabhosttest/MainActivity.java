package com.example.fragmenttabhosttest;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class MainActivity extends FragmentActivity {
	public static final String KEY_NAME = "key";
	private FragmentTabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialTabs();
		
	}

	private void initialTabs(){
		tabHost = (FragmentTabHost) findViewById(R.id.tab_host);
		tabHost.setup(this, getSupportFragmentManager(), R.id.layout_container);
		
		
		TabSpec tabChat = tabHost.newTabSpec("chat");
		View tabChatView = getIndicatorView("聊天", R.drawable.social_chat);
		tabChat.setIndicator(tabChatView);
		Bundle data = new Bundle();
		data.putString(KEY_NAME, "聊天");
		tabHost.addTab(tabChat, TopLevelFragment.class, data);
		
		TabSpec tabFriend = tabHost.newTabSpec("friend");
		View tabFriendView = getIndicatorView("朋友", R.drawable.social_bcc);
		tabFriend.setIndicator(tabFriendView);
		Bundle friendData = new Bundle();
		friendData.putString(KEY_NAME, "朋友");
		tabHost.addTab(tabFriend, TopLevelFragment.class, friendData);
		
		
		TabSpec tabMap = tabHost.newTabSpec("map");
		View tabMapView = getIndicatorView("地图", R.drawable.location_map);
		tabMap.setIndicator(tabMapView);
		Bundle mapData = new Bundle();
		mapData.putString(KEY_NAME, "地图");
		tabHost.addTab(tabMap, TopLevelFragment.class, mapData);
		
		TabSpec tabLocation = tabHost.newTabSpec("locationData");
		View tabLocationView = getIndicatorView("定位", R.drawable.social_chat);
		tabLocation.setIndicator(tabLocationView);
		Bundle locationData = new Bundle();
		locationData.putString(KEY_NAME, "定位");
		tabHost.addTab(tabLocation, TopLevelFragment.class, locationData);
		
	}
	
	private View getIndicatorView(String text,int drawableId){
		View view = getLayoutInflater().inflate(R.layout.view_tab_item, null);
		TextView tvText = (TextView) view.findViewById(R.id.tv_tab_text);
		tvText.setText(text);
		Drawable top = getResources().getDrawable(drawableId);
		tvText.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
		return view;
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
