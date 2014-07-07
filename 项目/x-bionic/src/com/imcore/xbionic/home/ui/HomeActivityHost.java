package com.imcore.xbionic.home.ui;

import com.imcore.xbionic.R;
import com.imcore.xbionic.util.ToastUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomeActivityHost extends Fragment implements OnClickListener {
	private View mFragmentView;
	private ImageView mImgLogo;
	private ViewPager viewPager;
	private ImageView[] mImageViews;
	private int[] imgIdArray;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mFragmentView = inflater.inflate(R.layout.drawer_activity_main, null);
		mImgLogo = (ImageView) mFragmentView
				.findViewById(R.id.iv_drawer_main_logo);
		mImgLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ToastUtil.showToast(getActivity(), "logo");
			}
		});

		initWidget();

		return mFragmentView;
	}

	//viewPager小于三张时会出错，未做判断
	private void initWidget() {
		viewPager = (ViewPager) mFragmentView
				.findViewById(R.id.drawer_main_body_layout);

		imgIdArray = new int[] { R.drawable.ic_drawer_list_icon,
				R.drawable.ic_drawer_list_next, R.drawable.ic_drawer_main_logo,
				R.drawable.ic_drawer_main_menu };

		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setBackgroundResource(imgIdArray[i]);
			mImageViews[i] = imageView;
		}

		viewPager.setAdapter(new MyAdapter());
		//viewPager.setOnPageChangeListener(pageViewChangeListener);
		viewPager.setCurrentItem((mImageViews.length) * 200);//设置当前position

	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mImageViews[position
					% mImageViews.length]);

		}

		@Override
		public Object instantiateItem(View container, int position) {
			View view = null;
			try{
				view = mImageViews[position% mImageViews.length];
				final int p = position;
				view.setOnClickListener(new OnClickListener() {//pager点击监听事件
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ToastUtil.showToast(getActivity(), p% mImageViews.length + "");
					}
				});
				
				((ViewPager) container).addView(view, 0);
			}catch(Exception e){
				
			}
			return view;
		}
	}

/*	private OnPageChangeListener pageViewChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			// setImageBackground(arg0 % mImageViews.length);
		}
	};*/

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_drawer_main_menu:

			break;
		case R.id.iv_drawer_main_logo:

			break;
		case R.id.iv_drawer_main_search:

			break;
		}

	}

}
