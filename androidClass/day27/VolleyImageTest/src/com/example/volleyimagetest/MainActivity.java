package com.example.volleyimagetest;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.volleyimagetest.volley.RequestQueueSingleton;

public class MainActivity extends Activity {
	@SuppressWarnings("deprecation")
	ListView mList;

	String[] ImgUrl = new String[] {
			"http://f.hiphotos.baidu.com/image/pic/item/7dd98d1001e939016c45953e79ec54e736d1966e.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0fefdf48e015e6034a85fdf72c5.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/d53f8794a4c27d1e72c33ec419d5ad6edcc438cc.jpg",
			"http://b.hiphotos.baidu.com/image/pic/item/267f9e2f070828385ee83f10ba99a9014d08f1c9.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd50eb6edf453da81cb38db3dcc.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/c9fcc3cec3fdfc034f0b3bbcd63f8794a4c22647.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/9825bc315c6034a8cd0b8a85c9134954082376cc.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/d52a2834349b033b9e3d47de17ce36d3d539bd79.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/738b4710b912c8fc19802ec9fe039245d6882147.jpg",
			"http://b.hiphotos.baidu.com/image/pic/item/83025aafa40f4bfb02975c4f014f78f0f7361862.jpg",
			"http://b.hiphotos.baidu.com/image/pic/item/10dfa9ec8a1363272bc51737938fa0ec08fac78e.jpg",
			"http://b.hiphotos.baidu.com/image/pic/item/18d8bc3eb13533fa435a4a2aaad3fd1f40345bc2.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31ca5384fbf85d6277f9e2ff8aa.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/960a304e251f95cafc8b2eb6cb177f3e670952b7.jpg" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mList = (ListView) findViewById(R.id.lv_list);
		mList.setAdapter(new ImageAdapter());

	}

	public class ImageAdapter extends BaseAdapter {
		public int getCount() {
			return ImgUrl.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder vh = null;
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.img_item, null);
				vh = new ViewHolder();
				vh.imgView = (ImageView) view.findViewById(R.id.iv_img);
				view.setTag(vh);
			}else{
				vh = (ViewHolder) view.getTag();
			}
			
			getImag(vh.imgView, ImgUrl[position]);

			return view;
		}
		class ViewHolder{
			ImageView imgView;
		}
	}

	private void getImag(ImageView image, String url) {
		ImageLoader loader = RequestQueueSingleton.getInstance(
				getApplicationContext()).getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(image,
				R.drawable.ic_launcher, R.drawable.ic_launcher);
		loader.get(url, listener, 2000, 2000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
