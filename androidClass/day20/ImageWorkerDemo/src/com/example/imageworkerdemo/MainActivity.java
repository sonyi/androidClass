package com.example.imageworkerdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private List<String> mImagePaths;
	private GridView gvImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gvImages = (GridView) findViewById(R.id.gv_images);
		getImages();

		ImageDataAdapter adapter = new ImageDataAdapter();
		gvImages.setAdapter(adapter);
	}

	private void getImages() {
		if (mImagePaths == null) {
			mImagePaths = new ArrayList<String>();
		}

		//Thumbnailsº”‘ÿÀı¬‘Õº£¨Mediaº”‘ÿÕÍ’˚Õº∆¨
		ContentResolver cr = getContentResolver();
		//Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Images.Thumbnails.DATA };
		Cursor c = cr.query(uri, projection, null, null, null);

		while (c.moveToNext()) {
			String path = c.getString(c
					.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
			mImagePaths.add(path);
		}
		c.close();
	}

	private class ImageDataAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mImagePaths.size();
		}

		@Override
		public Object getItem(int position) {
			return mImagePaths.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder vh = null;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.view_image_item,
						null);
				vh = new ViewHolder();
				vh.imgItem = (ImageView) view.findViewById(R.id.img_item);

				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}

			String data = mImagePaths.get(position);
			new BitmapWorker().fetch(data, vh.imgItem);

			return view;
		}

		class ViewHolder {
			ImageView imgItem;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
