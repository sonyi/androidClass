package com.example.photomanagetest.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.photomanagetest.R;
import com.example.photomanagetest.data.DataContract.PhoteDataContract;
import com.example.photomanagetest.data.PhotoManageDataAccess;
import com.example.photomanagetest.model.PhotoInformation;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	private final int REQUEST_CODE = 0X00;
	ListView mInfoList;
	List<PhotoInformation> mImgSourse;
	PhotoDataAdapter mAdapter = null;
	ImageButton mImgBtnTitleWrite;
	private String dirName = "HomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_home);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.activity_home_title);

		mInfoList = (ListView) findViewById(R.id.lv_home);
		mInfoList.setDivider(null);

		mImgSourse = new PhotoManageDataAccess(this).queryPhoto();// 初始化数据
		mAdapter = new PhotoDataAdapter();

		View headView = getLayoutInflater().inflate(
				R.layout.activity_home_header, null);
		mInfoList.addHeaderView(headView);
		View footView = getLayoutInflater().inflate(
				R.layout.activity_home_footer, null);
	
		mInfoList.setAdapter(mAdapter);

		mImgBtnTitleWrite = (ImageButton) findViewById(R.id.ib_title_write);
		mImgBtnTitleWrite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this,
						WriteActivity.class);
				intent.putExtra(PhoteDataContract.PHOTO_ID,
						mImgSourse.size() + 1);
				Log.i("id", (mImgSourse.size() + 1) + "");
				// startActivityForResult(intent, REQUEST_CODE);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mImgSourse = new PhotoManageDataAccess(this).queryPhoto();
		if (mAdapter == null) {
			mAdapter = new PhotoDataAdapter();
		} else {
			mAdapter.notifyDataSetChanged();
		}
		super.onResume();
	}

	private class PhotoDataAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
			ViewHolder vh = null;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.activity_home_body,
						null);
				vh = new ViewHolder();
				vh.imgLeftTitle = (TextView) view
						.findViewById(R.id.tv_left_text);
				vh.imgLeftRes = (ImageButton) view
						.findViewById(R.id.iv_left_img);
				vh.imgLeftTime = (TextView) view
						.findViewById(R.id.tv_left_time);
				vh.imgRightTitle = (TextView) view
						.findViewById(R.id.tv_right_text);
				vh.imgRightRes = (ImageButton) view
						.findViewById(R.id.iv_right_img);
				vh.imgRightTime = (TextView) view
						.findViewById(R.id.tv_right_time);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}
			getViewData(view, vh, position);// 给各个组件赋值
			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return mImgSourse.get(position).getId();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mImgSourse.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return (mImgSourse.size() + 1) / 2;
		}
	}

	// 给左右控件赋值
	public void getViewData(View view, ViewHolder vh, int position) {
		PhotoInformation photoLeftData = null;
		PhotoInformation photoRightData = null;

		// 判断右边控件是否是不可见，如果是，设置为可见
		RelativeLayout rlRight = (RelativeLayout) view
				.findViewById(R.id.rl_info_right);
		if (rlRight.getVisibility() != View.VISIBLE) {
			rlRight.setVisibility(View.VISIBLE);
			vh.imgRightTime.setVisibility(View.VISIBLE);
		}

		// 左边控件赋值
		photoLeftData = mImgSourse.get(2 * position);
		vh.imgLeftTitle.setText(photoLeftData.getImgTitle());

		// 读取左边文件图片资源
		Bitmap bmLeft = getFeedBitmap(photoLeftData.getImgResPath());
		vh.imgLeftRes.setImageBitmap(bmLeft);

		vh.imgLeftTime.setText(photoLeftData.getImgTime());

		// 判断是否还有下一个资源填充右边资源
		if (2 * position < mImgSourse.size() - 1) {
			Log.i("position", position + "------------" + mImgSourse.size());

			photoRightData = mImgSourse.get(2 * position + 1);
			vh.imgRightTitle.setText(photoRightData.getImgTitle());

			Bitmap bmRight = getFeedBitmap(photoRightData.getImgResPath());
			vh.imgRightRes.setImageBitmap(bmRight);

			vh.imgRightTime.setText(photoRightData.getImgTime());
		} else {// 如果没有资源，右边控件设置为不可见
			vh.imgRightTime.setVisibility(View.INVISIBLE);
			rlRight.setVisibility(View.INVISIBLE);
		}

		// 设置左边图片监听器
		final PhotoInformation leftData = photoLeftData;
		vh.imgLeftRes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(HomeActivity.this, leftData.getImgTitle(),
						Toast.LENGTH_SHORT).show();
			}
		});
		// 设置右边图片监听器
		final PhotoInformation rightData = photoRightData;
		vh.imgRightRes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(HomeActivity.this, rightData.getImgTitle(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	class ViewHolder {
		TextView imgLeftTitle;
		ImageButton imgLeftRes;
		TextView imgLeftTime;
		TextView imgRightTitle;
		ImageButton imgRightRes;
		TextView imgRightTime;
	}

	// 读取图片资源
	private Bitmap getFeedBitmap(String filePath) {
		BitmapFactory.Options ops = new BitmapFactory.Options();// 压缩图片
		ops.inSampleSize = 5;
		Bitmap bm = BitmapFactory.decodeFile(filePath, ops);
		return bm;
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// // TODO Auto-generated method stub
	// if (resultCode != RESULT_OK) {
	// return;
	// }
	// if(requestCode == REQUEST_CODE){
	// PhotoInformation ph = new PhotoInformation();
	// ph.setId(data.getIntExtra(PhoteDataContract.PHOTO_ID,0));
	// ph.setImgTitle(data.getStringExtra(PhoteDataContract.PHOTO_TITLE));
	// ph.setImgResPath(data.getStringExtra(PhoteDataContract.PHOTO_PATH));
	// ph.setImgTime(data.getStringExtra(PhoteDataContract.PHOTO_TIME));
	// mImgSourse.add(ph);
	// mAdapter.notifyDataSetChanged();
	//
	// }
	// super.onActivityResult(requestCode, resultCode, data);
	// }

	// 设置添加图片监听事件
	// private OnClickListener footViewOnClickListener = new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// PhotoInformation img1 = new PhotoInformation(1, "游戏图片",
	// "img_game_01.jpg", "14:25");
	// PhotoInformation img2 = new PhotoInformation(2, "旅游图片",
	// "img_journey_01.jpg", "18:25");
	// mImgSourse.add(img1);
	// mImgSourse.add(img2);
	// mAdapter.notifyDataSetChanged();
	// }
	// };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
