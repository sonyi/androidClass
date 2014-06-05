package com.example.homeactivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.R.anim;
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
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView mInfoList;
	List<Feed> mImgSourse;
	FeedAdapter mAdapter;
	private String dirName = "HomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.view_title);

		mInfoList = (ListView) findViewById(R.id.lv_info);
		mInfoList.setDivider(null);
		initData();
		mAdapter = new FeedAdapter();

		View headView = getLayoutInflater().inflate(R.layout.view_info_header,
				null);
		mInfoList.addHeaderView(headView);
		View footView = getLayoutInflater().inflate(R.layout.view_info_footer,
				null);
		mInfoList.addFooterView(footView);
		TextView tvFooter = (TextView) footView
				.findViewById(R.id.tv_add_footer);
		tvFooter.setOnClickListener(footViewOnClickListener);

		mInfoList.setAdapter(mAdapter);

	}

	public void initData() {
		mImgSourse = new ArrayList<Feed>();
		Feed img1 = new Feed(1, "��ŮͼƬ", "img_peri_01.jpg", "13:28");
		Feed img2 = new Feed(2, "����ͼƬ", "img_animal_01.jpg", "15:22");
		Feed img3 = new Feed(3, "�羰ͼƬ", "img_scenery_01.jpg", "17:58");
		Feed img4 = new Feed(4, "����ͼƬ", "img_animation_01.jpg", "3:31");
		Feed img5 = new Feed(5, "��ӰͼƬ", "img_photo_01.jpg", "7:31");

		mImgSourse.add(img1);
		mImgSourse.add(img2);
		mImgSourse.add(img3);
		mImgSourse.add(img4);
		mImgSourse.add(img5);
	}

	private class FeedAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
			ViewHolder vh = null;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.view_info_item,
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
			getViewData(view, vh, position);// �����������ֵ
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

	// �����ҿؼ���ֵ
	public void getViewData(View view, ViewHolder vh, int position) {
		Feed feedLeftData = null;
		Feed feedRightData = null;

		// �ж��ұ߿ؼ��Ƿ��ǲ��ɼ�������ǣ�����Ϊ�ɼ�
		RelativeLayout rlRight = (RelativeLayout) view
				.findViewById(R.id.rl_info_right);
		if (rlRight.getVisibility() != View.VISIBLE) {
			rlRight.setVisibility(View.VISIBLE);
			vh.imgRightTime.setVisibility(View.VISIBLE);
		}

		// ��߿ؼ���ֵ
		feedLeftData = mImgSourse.get(2 * position);
		vh.imgLeftTitle.setText(feedLeftData.getImgTitle());

		// ��ȡ����ļ�ͼƬ��Դ
		String fileNameLeft = feedLeftData.getImgResPath();
		// Log.i("imgpath", fileNameLeft);
		Bitmap bmLeft = getFeedBitmap(fileNameLeft, dirName);
		vh.imgLeftRes.setImageBitmap(bmLeft);

		// vh.imgLeftRes.setBackgroundResource(feedLeftData.getImgResId());
		vh.imgLeftTime.setText(feedLeftData.getImgTime());

		// �ж��Ƿ�����һ����Դ����ұ���Դ
		if (2 * position < mImgSourse.size() - 1) {
			Log.i("position", position + "------------" + mImgSourse.size());

			feedRightData = mImgSourse.get(2 * position + 1);
			vh.imgRightTitle.setText(feedRightData.getImgTitle());

			String fileNameRight = feedRightData.getImgResPath();
			Bitmap bmRight = getFeedBitmap(fileNameRight, dirName);
			vh.imgRightRes.setImageBitmap(bmRight);
			// vh.imgRightRes.setBackgroundResource(feedRightData.getImgResId());

			vh.imgRightTime.setText(feedRightData.getImgTime());
		} else {// ���û����Դ���ұ߿ؼ�����Ϊ���ɼ�
			vh.imgRightTime.setVisibility(View.INVISIBLE);
			rlRight.setVisibility(View.INVISIBLE);
		}

		// �������ͼƬ������
		final Feed leftData = feedLeftData;
		vh.imgLeftRes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, leftData.getImgTitle(),
						Toast.LENGTH_SHORT).show();
			}
		});
		// �����ұ�ͼƬ������
		final Feed rightData = feedRightData;
		vh.imgRightRes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, rightData.getImgTitle(),
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

	// ��ȡͼƬ��Դ
	private Bitmap getFeedBitmap(String fileName, String dirName) {
		File dir = new File(Environment.getExternalStorageDirectory(), dirName);
		File file = new File(dir, fileName);
		BitmapFactory.Options ops = new BitmapFactory.Options();// ѹ��ͼƬ
		ops.inSampleSize = 3;
		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), ops);
		return bm;
	}

	// �������ͼƬ�����¼�
	private OnClickListener footViewOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Feed img1 = new Feed(1, "��ϷͼƬ", "img_game_01.jpg", "14:25");
			Feed img2 = new Feed(2, "����ͼƬ", "img_journey_01.jpg", "18:25");
			mImgSourse.add(img1);
			mImgSourse.add(img2);
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
