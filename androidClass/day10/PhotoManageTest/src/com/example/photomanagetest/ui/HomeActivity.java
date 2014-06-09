package com.example.photomanagetest.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.photomanagetest.R;
import com.example.photomanagetest.data.DataContract.PhoteDataContract;
import com.example.photomanagetest.data.ImageWorker;
import com.example.photomanagetest.data.ImgManageDataAccess;
import com.example.photomanagetest.model.ImgInformation;

public class HomeActivity extends Activity {
	ListView mInfoList;
	List<ImgInformation> mImgSourse, mImgList;
	PhotoDataAdapter mAdapter = null;
	ImageButton mImgBtnTitleWrite;
	int row = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_home);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.activity_home_title);

		mInfoList = (ListView) findViewById(R.id.lv_home);
		mInfoList.setDivider(null);

		mImgSourse = new ImgManageDataAccess(this).queryPhoto();// 初始化数据
		row = getRow();
		mAdapter = new PhotoDataAdapter();

		View headView = getLayoutInflater().inflate(
				R.layout.activity_home_header, null);
		mInfoList.addHeaderView(headView);
		View footView = getLayoutInflater().inflate(
				R.layout.activity_home_footer, null);
		mInfoList.addFooterView(footView);
		footView.setOnClickListener(footViewOnClickListener);

		mInfoList.setAdapter(mAdapter);

		mImgBtnTitleWrite = (ImageButton) findViewById(R.id.ib_title_write);
		mImgBtnTitleWrite.setOnClickListener(mImgBtnTitleWriteOnClickListener);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		updataImg();
		super.onResume();
	}

	private OnClickListener mImgBtnTitleWriteOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(HomeActivity.this, WriteActivity.class);
			intent.putExtra(PhoteDataContract.PHOTO_ID, mImgSourse.size() + 1);
			Log.i("id", (mImgSourse.size() + 1) + "");
			// startActivityForResult(intent, REQUEST_CODE);
			startActivity(intent);
		}
	};

	private OnClickListener footViewOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			int rowAll = (mImgSourse.size() + 1) / 2;
			switch (rowAll - row) {
			case 0:
				break;
			case 1:
				row += 1;
				updataImg();
				break;
			default:
				row += 2;
				updataImg();
			}
		}
	};

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
			return row;
		}
	}

	// 给左右控件赋值
	public void getViewData(View view, ViewHolder vh, int position) {
		ImgInformation photoLeftData = null;
		ImgInformation photoRightData = null;

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
		// Bitmap bmLeft = getFeedBitmap(photoLeftData.getImgResPath());
		// vh.imgLeftRes.setImageBitmap(bmLeft);
		new ImageWorker().fetch(vh.imgLeftRes, photoLeftData.getImgResPath());

		vh.imgLeftTime.setText(photoLeftData.getImgTime());

		// 判断是否还有下一个资源填充右边资源
		if (2 * position < mImgSourse.size() - 1) {
			Log.i("position", position + "------------" + mImgSourse.size());

			photoRightData = mImgSourse.get(2 * position + 1);
			vh.imgRightTitle.setText(photoRightData.getImgTitle());

			// Bitmap bmRight = getFeedBitmap(photoRightData.getImgResPath());
			// vh.imgRightRes.setImageBitmap(bmRight);
			new ImageWorker().fetch(vh.imgRightRes,
					photoRightData.getImgResPath());

			vh.imgRightTime.setText(photoRightData.getImgTime());
		} else {// 如果没有资源，右边控件设置为不可见
			vh.imgRightTime.setVisibility(View.INVISIBLE);
			rlRight.setVisibility(View.INVISIBLE);
		}

		// 设置左边图片监听器
		final ImgInformation leftData = photoLeftData;
		vh.imgLeftRes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(HomeActivity.this, leftData.getImgTitle(),
						Toast.LENGTH_SHORT).show();
			}
		});
		// 设置右边图片监听器
		final ImgInformation rightData = photoRightData;
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

	private void updataImg() {
		mImgSourse = new ImgManageDataAccess(this).queryPhoto();
		if(mImgSourse.size() > 0 && mImgSourse.size() <= 2){
			row = 1;
		}
		if(mImgSourse.size() > 2 && mImgSourse.size() <= 4){
			row = 2;
		}
		if (mAdapter == null) {
			mAdapter = new PhotoDataAdapter();
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	private int getRow() {
		row = (mImgSourse.size() + 1) / 2;
		if (row > 2) {
			row = 2;
		}
		return row;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
