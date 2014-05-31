package com.gellarydemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Gallery gallery;
	private GalleryAdapter mAdapter;
	private List<ImageItem> mDataSource = new ArrayList<ImageItem>();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        gallery = (Gallery) findViewById(R.id.gallery);
        initialData();
        
        mAdapter = new GalleryAdapter();
        gallery.setAdapter(mAdapter);
    }
	
	private void initialData () {
		ImageItem img1 = new ImageItem(1,"eye",R.drawable.img_01);
		ImageItem img2 = new ImageItem(2,"one dollar",R.drawable.img_02);
		ImageItem img3 = new ImageItem(3,"drugs",R.drawable.img_03);
		ImageItem img4 = new ImageItem(4,"Great Gatsby1",R.drawable.img_04);
		ImageItem img5 = new ImageItem(5,"Great Gatsby2",R.drawable.img_05);
		ImageItem img6 = new ImageItem(6,"Great Gatsby3",R.drawable.img_06);
		ImageItem img7 = new ImageItem(7,"bitch1",R.drawable.img_07);
		ImageItem img8 = new ImageItem(8,"bitch2",R.drawable.img_08);
		
		mDataSource.add(img1);
		mDataSource.add(img2);
		mDataSource.add(img3);
		mDataSource.add(img4);
		mDataSource.add(img5);
		mDataSource.add(img6);
		mDataSource.add(img7);
		mDataSource.add(img8);
	}
	
	private class GalleryAdapter extends BaseAdapter {
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
			// 1. ��ȡ������ͼ��view����ʵ��
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.view_gallery_item, null);
			
			// 2.��ȡview���еĸ����ؼ�ʵ��
			TextView tvImgName = (TextView) view.findViewById(R.id.tv_img_name);
			ImageView imgItem = (ImageView) view.findViewById(R.id.img_item);
			
			// 3.��ȡָ��λ�õ���������ֶ�ֵ������ʾ��������ͼ�еĶ�Ӧ�Ŀؼ�֮��
			ImageItem data = mDataSource.get(position);
			tvImgName.setText(data.getImgName());
			imgItem.setImageResource(data.getImgResId());
			
			// 4.����������ͼ����ʵ��
			return view;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
