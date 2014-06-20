package com.example.img.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.imagetest.R;
import com.example.img.util.BitmapWorker;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		
		gvImages = (GridView) findViewById(R.id.gv_img);
		getImages();
		ImageDataAdapter mImageDataAdapter = new ImageDataAdapter();
		gvImages.setAdapter(mImageDataAdapter);
	}

	private void getImages(){
		if(mImagePaths == null){
			mImagePaths = new ArrayList<String>();
		}
		ContentResolver cr = getContentResolver();
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[]{MediaStore.Images.Media.DATA};
		Cursor c = cr.query(uri, projection, null, null, null);
		
		while(c.moveToNext()){
			String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
			mImagePaths.add(path);
		}
		c.close();
	}
	
	private class ImageDataAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImagePaths.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mImagePaths.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
			ViewHolder vh = null;
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.view_img_item, null);
				vh = new ViewHolder();
				vh.imgItem = (ImageView) view.findViewById(R.id.img_item);
				view.setTag(vh);
			}else{
				vh = (ViewHolder) view.getTag();
			}
			
//			Bitmap bm =  BitmapFactory.decodeFile(mImagePaths.get(position));
//			vh.imgItem.setImageBitmap(bm);
			
			String absolutePath = mImagePaths.get(position);
			new BitmapWorker().fetch(absolutePath, vh.imgItem);
			return view;
		}
		
		class ViewHolder{
			ImageView imgItem;
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
