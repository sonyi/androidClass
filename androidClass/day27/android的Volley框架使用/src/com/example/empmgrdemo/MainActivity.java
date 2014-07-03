package com.example.empmgrdemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.NetworkImageView;

public class MainActivity extends Activity {
	// private ListView lvEmpList;
	private NetworkImageView imgPic;

	private EmpAdapter mAdapter;

	private List<Employee> mEmpList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// lvEmpList = (ListView) findViewById(R.id.lv_emp_list);
		imgPic = (NetworkImageView) findViewById(R.id.img_pic);
		getImageFromWeb3();

		// getEmpList();
	}

	private void getImageFromWeb1() {
		String url = "http://b.hiphotos.baidu.com/image/pic/item/91ef76c6a7efce1bb4d19f39ad51f3deb58f654e.jpg";
		ImageRequest request = new ImageRequest(url, mImageListener, 750, 500, Config.RGB_565, mImageErrorListener);
	    RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
	}

	private Response.Listener<Bitmap> mImageListener = new Listener<Bitmap>() {
		@Override
		public void onResponse(Bitmap response) {
			imgPic.setImageBitmap(response);
		}
	};

	private Response.ErrorListener mImageErrorListener = new ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
           imgPic.setImageResource(R.drawable.ic_launcher);
		}
	};
	
	// 推荐使用这种方法加载网络图片，尤其是大量的图片
	private void getImageFromWeb2() {
		String url = "http://b.hiphotos.baidu.com/image/pic/item/91ef76c6a7efce1bb4d19f39ad51f3deb58f654e.jpg";
	    ImageLoader loader = RequestQueueSingleton.getInstance(getApplicationContext()).getImageLoader();
	    ImageListener listener = ImageLoader.getImageListener(imgPic, R.drawable.ic_launcher, R.drawable.ic_launcher);
	    loader.get(url, listener, 300, 300);
	}
	
	private void getImageFromWeb3() {
		String url = "http://b.hiphotos.baidu.com/image/pic/item/91ef76c6a7efce1bb4d19f39ad51f3deb58f654e.jpg";
	    ImageLoader loader = RequestQueueSingleton.getInstance(getApplicationContext()).getImageLoader();
	    imgPic.setImageUrl(url, loader);
	}

	private void getEmpList() {
		// Use Volley
		String url = "http://192.168.1.148:8080/HelloWeb/demo";

		// 使用自定义Request
		DataRequest request = new DataRequest(Request.Method.POST, url,
				mResponseSuccess, mErrorListener) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("cid", "1");
				return params;
			}
		};

		// 把请求添加到请求队列
		RequestQueueSingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);
	}

	private Response.Listener<String> mResponseSuccess = new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			mEmpList = JsonUtil.toObjectList(response, Employee.class);
			if (mAdapter == null) {
				mAdapter = new EmpAdapter();
				// lvEmpList.setAdapter(mAdapter);
			} else {
				mAdapter.notifyDataSetChanged();
			}
		}
	};

	private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(MainActivity.this,
					"请求服务器发生错误:" + error.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	};

	private class EmpAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mEmpList.size();
		}

		@Override
		public Object getItem(int position) {
			return mEmpList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mEmpList.get(position).id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(
					android.R.layout.simple_list_item_1, null);
			TextView text1 = (TextView) view.findViewById(android.R.id.text1);

			Employee emp = mEmpList.get(position);
			String content = emp.id + "	" + emp.empName + "	" + emp.age;
			text1.setText(content);
			return view;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
