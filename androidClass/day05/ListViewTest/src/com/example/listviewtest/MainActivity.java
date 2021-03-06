package com.example.listviewtest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView lvInfoList;
	private List<Feed> mDataSource = new ArrayList<Feed>();
	private FeedListAdapter mAdapter;
	private TextView tv_footer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lvInfoList = (ListView) findViewById(R.id.lv_info);
		mAdapter = new FeedListAdapter();//创建适配器
		initData();//初始化数据
		
		//添加头部视图
		View headerView = getLayoutInflater().inflate(
				R.layout.view_info_header, null);
		lvInfoList.addHeaderView(headerView);
		

		
		//添加尾部视图并添加监听器
		View footerView = getLayoutInflater().inflate(R.layout.view_info_foot,
				null);
		lvInfoList.addFooterView(footerView);
		tv_footer = (TextView) footerView.findViewById(R.id.tv_footer);
		tv_footer.setOnClickListener(mFooterViewClickListener);

		//加载适配器，必须放在所以视图加载后再设置适配器
		lvInfoList.setAdapter(mAdapter);

		//添加视图单击监听事件
		lvInfoList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				arg2--;
				if (arg2 >= 0 && arg2 < mDataSource.size()) {// 对点击的视图进行判断，排除头尾视图
					Feed feed = mDataSource.get(arg2);
					Toast.makeText(MainActivity.this, feed.getTitle(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	public void initData() {
		Feed feed1 = new Feed(1, R.drawable.img_01, "这是标题1", "这是图片注释");
		Feed feed2 = new Feed(2, R.drawable.img_02, "这是标题2", "这是图片注释");
		Feed feed3 = new Feed(3, R.drawable.img_03, "这是标题3", "这是图片注释");
		Feed feed4 = new Feed(4, R.drawable.img_04, "这是标题4", "这是图片注释");
		Feed feed5 = new Feed(5, R.drawable.img_05, "这是标题5", "这是图片注释");
		Feed feed6 = new Feed(6, R.drawable.img_06, "这是标题6", "这是图片注释");
		Feed feed7 = new Feed(7, R.drawable.img_07, "这是标题7", "这是图片注释");
		Feed feed8 = new Feed(8, R.drawable.img_08, "这是标题8", "这是图片注释");

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
		int count = 0;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDataSource.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mDataSource.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return mDataSource.get(arg0).getId();
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			// convertView是重用的子项视图对象，如果不为空，就不比执行inflate和findViewById等消耗操作
			View view = arg1;
			ViewHolder vh = null;

			if (view == null) {
				// convertView为空，inflate子项视图，加载控件实例
				view = getLayoutInflater().inflate(R.layout.view_info_item,
						null);
				vh = new ViewHolder();
				vh.imgAvater = (ImageView) view.findViewById(R.id.img_avater);
				vh.tvTitle = (TextView) view.findViewById(R.id.tv_title);
				vh.tvDescription = (TextView) view
						.findViewById(R.id.tv_description);
				vh.btnAction = (ImageButton) view.findViewById(R.id.btn_action);
				// 将ViewHolder对象与子项视图对象关联
				view.setTag(vh);

				Log.i("view", "创建了第" + (++count) + "个view");
			} else {
				vh = (ViewHolder) view.getTag();
			}

			Feed data = mDataSource.get(arg0);
			vh.imgAvater.setImageResource(data.getImgResId());
			vh.tvTitle.setText(data.getTitle());
			vh.tvDescription.setText(data.getDescription());

			final int index = arg0;
			vh.btnAction.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this,
							"这是单击了第" + (index + 1) + "行的按钮", Toast.LENGTH_SHORT)
							.show();
				}
			});

			return view;
		}

		class ViewHolder {
			ImageView imgAvater;
			TextView tvTitle;
			TextView tvDescription;
			ImageButton btnAction;
		}

	}

	private OnClickListener mFooterViewClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Feed feed1 = new Feed(1, R.drawable.img_01, "这是标题1", "这是图片注释");
			Feed feed2 = new Feed(2, R.drawable.img_02, "这是标题2", "这是图片注释");
			Feed feed3 = new Feed(3, R.drawable.img_03, "这是标题3", "这是图片注释");
			Feed feed4 = new Feed(4, R.drawable.img_04, "这是标题4", "这是图片注释");
			mDataSource.add(feed1);
			mDataSource.add(feed2);
			mDataSource.add(feed3);
			mDataSource.add(feed4);
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
