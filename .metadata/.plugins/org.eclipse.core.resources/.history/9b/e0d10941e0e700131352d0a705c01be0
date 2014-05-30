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

		// 给ListView添加头部视图
		View headerView = getLayoutInflater().inflate(
				R.layout.view_info_list_header, null);
		lvInfoList.addHeaderView(headerView);

		// 给listView添加底部视图
		View footerView = getLayoutInflater().inflate(
				R.layout.view_info_list_footer, null);
		lvInfoList.addFooterView(footerView);
		
		TextView tvFooter = (TextView) footerView.findViewById(R.id.tv_footer);
		tvFooter.setOnClickListener(mFootViewClickListener);
		
		lvInfoList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				position--;
				if (position >= 0 && position < mDataSource.size()) {
					Feed feed = mDataSource.get(position);
					Toast.makeText(MainActivity.this, feed.getTitle(),
							Toast.LENGTH_SHORT).show();
				}
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
			// convertView是重用的子项视图对象，如果不为空，就不比执行inflate和findViewById等消耗操作
			View view = convertView;
			ViewHolder vh = null;
			if (view == null) {
				// convertView为空，inflate子项视图，加载控件实例
				view = getLayoutInflater().inflate(R.layout.view_info_item,
						null);
				vh = new ViewHolder();
				vh.imgAvatar = (ImageView) view.findViewById(R.id.img_avater);
				vh.tvTitle = (TextView) view.findViewById(R.id.tv_title);
				vh.tvDescription = (TextView) view
						.findViewById(R.id.tv_description);
				vh.btnAction = (ImageButton) view.findViewById(R.id.btn_action);
				// 将ViewHolder对象与子项视图对象关联
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}
			// 给ViewHolder对象中的控件填充数据
			Feed data = mDataSource.get(position);
			vh.imgAvatar.setImageResource(data.getImgResId());
			vh.tvTitle.setText(data.getTitle());
			vh.tvDescription.setText(data.getDescription());

			final int index = position + 1;
			vh.btnAction.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(MainActivity.this, "第" + index + "行的按钮单击。",
							Toast.LENGTH_SHORT).show();
				}
			});

			return view;
		}

		class ViewHolder {
			ImageView imgAvatar;
			TextView tvTitle;
			TextView tvDescription;
			ImageButton btnAction;
		}
	}
	
	private OnClickListener mFootViewClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Feed feed9 = new Feed(1, R.drawable.img_09, "标题9", "内容内容文字内容");
			Feed feed10 = new Feed(2, R.drawable.img_10, "标题10", "内容内容文字内容");
			Feed feed11 = new Feed(3, R.drawable.img_11, "标题11", "内容内容文字内容");
			Feed feed12 = new Feed(4, R.drawable.img_12, "标题12", "内容内容文字内容");
			mDataSource.add(feed9);
			mDataSource.add(feed10);
			mDataSource.add(feed11);
			mDataSource.add(feed12);
		    // 在适配器设置过之后，以及数据源变更之后，使用如下方法更新视图
			mAdapter.notifyDataSetChanged();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
