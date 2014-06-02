package com.example.galarytest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.anim;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Gallery gallery;
	GalleryAdapter mAdapter;
	private List<ImageItem> mDateSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gallery = (Gallery) findViewById(R.id.galler);

		// ��ʽһ��
		//getPhonePic();

		 //��ʽ����
		 initialData();
		 mAdapter = new GalleryAdapter();
		 gallery.setAdapter(mAdapter);
	}

	// ��ʽ����
	private void initialData() {
		mDateSource = new ArrayList<ImageItem>();
		ImageItem img1 = new ImageItem(1, "ͼƬ1", R.drawable.img_01);
		ImageItem img2 = new ImageItem(2, "ͼƬ2", R.drawable.img_02);
		ImageItem img3 = new ImageItem(3, "ͼƬ3", R.drawable.img_03);
		ImageItem img4 = new ImageItem(4, "ͼƬ4", R.drawable.img_04);
		ImageItem img5 = new ImageItem(5, "ͼƬ5", R.drawable.img_05);
		ImageItem img6 = new ImageItem(6, "ͼƬ6", R.drawable.img_06);
		ImageItem img7 = new ImageItem(7, "ͼƬ7", R.drawable.img_07);
		ImageItem img8 = new ImageItem(8, "ͼƬ8", R.drawable.img_08);

		mDateSource.add(img1);
		mDateSource.add(img2);
		mDateSource.add(img3);
		mDateSource.add(img4);
		mDateSource.add(img5);
		mDateSource.add(img6);
		mDateSource.add(img7);
		mDateSource.add(img8);
	}

	private class GalleryAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDateSource.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mDateSource.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return mDateSource.get(arg0).getId();
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			// ��ȡ������ͼ��view����ʵ����
			// LayoutInflater view = LayoutInflater.from(MainActivity.this);
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.view_gallery_item, null);

			// ��ȡview���еĸ����ؼ�
			TextView tvImgName = (TextView) view.findViewById(R.id.tv_img_name);
			ImageView imgItem = (ImageView) view.findViewById(R.id.img_item);

			// ��ȡָ��λ�õ���������ֶ�
			ImageItem data = mDateSource.get(arg0);
			tvImgName.setText(data.getImgName());
			imgItem.setImageResource(data.getImgResId());

			// ��������
			return view;
		}

	}

	// ��ʽһ��
	public void getPhonePic() {
		String key = "imgres";
		String name = "imgName";
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> img1 = new HashMap<String, Object>();
		img1.put(key, R.drawable.img_01);
		img1.put(name, R.string.view_gallery_pic1);
		data.add(img1);

		Map<String, Object> img2 = new HashMap<String, Object>();
		img2.put(key, R.drawable.img_02);
		img2.put(name, R.string.view_gallery_pic2);
		data.add(img2);

		Map<String, Object> img3 = new HashMap<String, Object>();
		img3.put(key, R.drawable.img_03);
		img3.put(name, R.string.view_gallery_pic3);
		data.add(img3);

		Map<String, Object> img4 = new HashMap<String, Object>();
		img4.put(key, R.drawable.img_04);
		img4.put(name, R.string.view_gallery_pic4);
		data.add(img4);

		Map<String, Object> img5 = new HashMap<String, Object>();
		img5.put(key, R.drawable.img_05);
		img5.put(name, R.string.view_gallery_pic5);
		data.add(img5);

		Map<String, Object> img6 = new HashMap<String, Object>();
		img6.put(key, R.drawable.img_06);
		img6.put(name, R.string.view_gallery_pic6);
		data.add(img6);

		Map<String, Object> img7 = new HashMap<String, Object>();
		img7.put(key, R.drawable.img_07);
		img7.put(name, R.string.view_gallery_pic7);
		data.add(img7);

		Map<String, Object> img8 = new HashMap<String, Object>();
		img8.put(key, R.drawable.img_08);
		img8.put(name, R.string.view_gallery_pic8);
		data.add(img8);

		String[] from = new String[] { key, name };
		int[] to = new int[] { R.id.img_item, R.id.tv_img_name };
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.view_gallery_item, from, to);

		gallery.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}