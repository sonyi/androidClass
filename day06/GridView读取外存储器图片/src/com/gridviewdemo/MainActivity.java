package com.gridviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

public class MainActivity extends Activity {
	private GridView gvFeeds;
	private GridAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gvFeeds = (GridView) findViewById(R.id.gv_feeds);
		List<Feed> feedList = initialData();
		mAdapter = new GridAdapter(this, feedList);
		gvFeeds.setAdapter(mAdapter);
	}

	private List<Feed> initialData() {
		List<Feed> feedList = new ArrayList<Feed>();

		Feed feed1 = new Feed(1, "img_01.jpg", "����1", "����������������");
		Feed feed2 = new Feed(2, "img_02.jpg", "����2", "����������������");
		Feed feed3 = new Feed(3, "img_03.jpg", "����3", "����������������");
		Feed feed4 = new Feed(4, "img_04.jpg", "����4", "����������������");
		Feed feed5 = new Feed(5, "img_05.jpg", "����5", "����������������");
		Feed feed6 = new Feed(6, "img_06.jpg", "����6", "����������������");
		Feed feed7 = new Feed(7, "img_07.jpg", "����7", "����������������");
		Feed feed8 = new Feed(8, "img_08.jpg", "����8", "����������������");

		feedList.add(feed1);
		feedList.add(feed2);
		feedList.add(feed3);
		feedList.add(feed4);
		feedList.add(feed5);
		feedList.add(feed6);
		feedList.add(feed7);
		feedList.add(feed8);
		return feedList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
