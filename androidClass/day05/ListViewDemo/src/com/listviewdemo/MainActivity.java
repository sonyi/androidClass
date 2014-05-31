package com.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView lvInfoList;
	private FeedListAdapter mAdapter;
	private List<Feed> mDataSource = new ArrayList<Feed>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lvInfoList = (ListView) findViewById(R.id.lv_info);
		initialData();
		mAdapter = new FeedListAdapter();
		lvInfoList.setAdapter(mAdapter);

		lvInfoList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Feed feed = mDataSource.get(position);
				Toast.makeText(MainActivity.this, feed.getTitle(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initialData() {
		Feed feed1 = new Feed(1, R.drawable.img_01, "标题1", "内容内容文字内容");
		Feed feed2 = new Feed(2, R.drawable.img_02, "标题2", "内容内容文字内容");
		Feed feed3 = new Feed(3, R.drawable.img_03, "标题3", "内容内容文字内容");
		Feed feed4 = new Feed(4, R.drawable.img_04, "标题4", "内容内容文字内容");
		Feed feed5 = new Feed(5, R.drawable.img_05, "标题5", "内容内容文字内容");
		Feed feed6 = new Feed(6, R.drawable.img_06, "标题6", "内容内容文字内容");
		Feed feed7 = new Feed(7, R.drawable.img_07, "标题7", "内容内容文字内容");
		Feed feed8 = new Feed(8, R.drawable.img_08, "标题8", "内容内容文字内容");

		mDataSource.add(feed1);
		mDataSource.add(feed2);
		mDataSource.add(feed3);
		mDataSource.add(feed4);
		mDataSource.add(feed5);
		mDataSource.add(feed6);
		mDataSource.add(feed7);
		mDataSource.add(feed8);
	}

	private class FeedListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mDataSource.size();
		}

		@Override
		public Object getItem(int position) {
			return mDataSource.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mDataSource.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.view_info_item,
					null);

			ImageView imgAvatar = (ImageView) view
					.findViewById(R.id.img_avater);
			TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
			TextView tvDescription = (TextView) view
					.findViewById(R.id.tv_description);
			ImageButton btnAction = (ImageButton) view
					.findViewById(R.id.btn_action);

			Feed feed = mDataSource.get(position);
			imgAvatar.setImageResource(feed.getImgResId());
			tvTitle.setText(feed.getTitle());
			tvDescription.setText(feed.getDescription());

			final int index = position;
			// 给视图项中的控件加事件监听
			btnAction.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(MainActivity.this,
							"这是第" + index + "行的按钮单击.", Toast.LENGTH_SHORT)
							.show();
				}
			});

			return view;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
