package com.gridviewdemo;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private Context mContext;
	private List<Feed> mDataSource;

	public GridAdapter(Context context, List<Feed> feedList) {
		mContext = context;
		mDataSource = feedList;
	}

	public void addFeed(Feed feed) {
		mDataSource.add(feed);
	}

	public void removeFeed(int index) {
		mDataSource.remove(index);
	}

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
		View view = convertView;
		ViewHolder vh = null;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.view_grid_item, null);
			vh = new ViewHolder();
			vh.imgPic = (ImageView) view.findViewById(R.id.img_pic);
			vh.tvTitle = (TextView) view.findViewById(R.id.tv_title);
			view.setTag(vh);
		} else {
			vh = (ViewHolder) view.getTag();
		}
		
		Feed feed = mDataSource.get(position);
		vh.tvTitle.setText(feed.getTitle());
    
		String fileName = feed.getImgPath();
		File dir = new File(Environment.getExternalStorageDirectory(),"Demo");
		File file = new File(dir,fileName);
		
		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
		vh.imgPic.setImageBitmap(bm);
		
		return view;
	}

	class ViewHolder {
		ImageView imgPic;
		TextView tvTitle;
	}
}
