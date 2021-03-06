package com.example.android_022_imageswitchandgallery;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity {
	Gallery mGallrey;
	List<Feed> mImgData;
	ImgViewAdapter imgAdapter;
	ImageSwitcher mImgSwitcher;
	ImgSwitcherFactory mImgFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// �ޱ�����
		setContentView(R.layout.activity_main);
		mGallrey = (Gallery) findViewById(R.id.gallery);
		mImgSwitcher = (ImageSwitcher) findViewById(R.id.is_imgswitcher);
		mImgFactory = new ImgSwitcherFactory();
		mImgSwitcher.setFactory(mImgFactory);
		mImgSwitcher.setInAnimation(AnimationUtils.loadAnimation(
				MainActivity.this, android.R.anim.fade_in));
		mImgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
				MainActivity.this, android.R.anim.fade_out));

		initImgData();
		imgAdapter = new ImgViewAdapter();
		mGallrey.setAdapter(imgAdapter);
		
		mGallrey.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				mImgSwitcher.setImageResource(mImgData.get(arg2).getImgres());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				mImgSwitcher.setImageResource(mImgData.get(0).getImgres());
			}
		});

	}

	public void initImgData() {
		mImgData = new ArrayList<Feed>();
		Feed img1 = new Feed(1, R.drawable.img_01);
		Feed img2 = new Feed(2, R.drawable.img_02);
		Feed img3 = new Feed(3, R.drawable.img_03);
		Feed img4 = new Feed(4, R.drawable.img_04);
		Feed img5 = new Feed(5, R.drawable.img_05);
		Feed img6 = new Feed(6, R.drawable.img_06);
		Feed img7 = new Feed(7, R.drawable.img_07);
		Feed img8 = new Feed(8, R.drawable.img_08);

		mImgData.add(img1);
		mImgData.add(img2);
		mImgData.add(img3);
		mImgData.add(img4);
		mImgData.add(img5);
		mImgData.add(img6);
		mImgData.add(img7);
		mImgData.add(img8);

	}

	class ImgViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImgData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mImgData.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return mImgData.get(arg0).getId();
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = arg1;
			ViewHolder vh = null;
			if (view == null) {
				view = getLayoutInflater()
						.inflate(R.layout.view_img_item, null);
				vh = new ViewHolder();
				vh.imgView = (ImageView) view.findViewById(R.id.iv_img);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}

			Feed img = mImgData.get(arg0);
			vh.imgView.setBackgroundResource(img.getImgres());
			// vh.imgView.setImageResource(img.getImgres());

			return view;
		}

		class ViewHolder {
			ImageView imgView;
		}

	}

	class ImgSwitcherFactory implements ViewFactory {

		@Override
		public View makeView() {
			// TODO Auto-generated method stub
			ImageView imgView = new ImageView(MainActivity.this);
			//imgView.setBackgroundColor(0xFF000000);
			imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imgView.setLayoutParams(new ImageSwitcher.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			return imgView;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
