package com.example.gridviewtest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	GridAdapter mAdapter;
	GridView mGridView;
	List<Feed> mDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGridView = (GridView) findViewById(R.id.gv_grid);
		initData();
		mAdapter = new GridAdapter(this, mDataSource);
		mGridView.setAdapter(mAdapter);

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				if (arg2 >= 0 && arg2 < mDataSource.size()) {// 对点击的视图进行判断，排除头尾视图
					Feed feed = mDataSource.get(arg2);
					Toast.makeText(MainActivity.this, feed.getTitle(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	public void initData() {
		mDataSource = new ArrayList<Feed>();
		Feed feed1 = new Feed(1, "img_01.jpg", "这是标题1", "这是图片注释");
		Feed feed2 = new Feed(2, "img_02.jpg", "这是标题2", "这是图片注释");
		Feed feed3 = new Feed(3, "img_03.jpg", "这是标题3", "这是图片注释");
		Feed feed4 = new Feed(4, "img_04.jpg", "这是标题4", "这是图片注释");
		Feed feed5 = new Feed(5, "img_05.jpg", "这是标题5", "这是图片注释");
		Feed feed6 = new Feed(6, "img_06.jpg", "这是标题6", "这是图片注释");
		Feed feed7 = new Feed(7, "img_07.jpg", "这是标题7", "这是图片注释");
		Feed feed8 = new Feed(8, "img_08.jpg", "这是标题8", "这是图片注释");

		 mDataSource.add(feed1);
		 mDataSource.add(feed2);
		 mDataSource.add(feed3);
		 mDataSource.add(feed4);
		 mDataSource.add(feed5);
		 mDataSource.add(feed6);
		 mDataSource.add(feed7);
		 mDataSource.add(feed8);
		
	}

//	private class FeedListAdapter extends BaseAdapter {
//		
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
