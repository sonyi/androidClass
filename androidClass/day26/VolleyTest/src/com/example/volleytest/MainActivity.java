package com.example.volleytest;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {
	ListView mListView;
	List<GoodsCategory> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.lv_list);
		getGoodsCategoryList();

	}

	private void getGoodsCategoryList() {
		String urlStr = "http://yunming-api.suryani.cn/api/category/list.do?id=0";
		GoodsCategoryListRequest request = new GoodsCategoryListRequest(
				Request.Method.GET, urlStr, goodsCategoryListener, goodsCategoryErrorListener);
		Volley.newRequestQueue(this).add(request);
		
	}
	
	private Listener<List<GoodsCategory>> goodsCategoryListener = new Listener<List<GoodsCategory>>() {

		@Override
		public void onResponse(List<GoodsCategory> response) {
			mList = response;
			mListView.setAdapter(new GoodsCategoryAdapter());
		}

		
	};
	
	private ErrorListener goodsCategoryErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(MainActivity.this, "请求服务器发生错误:" + error.getMessage(), Toast.LENGTH_SHORT)
			.show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class GoodsCategoryAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return mList.get(arg0).id;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view = arg1;
			ViewHolder vh = null;
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.list_item, null);
				vh = new ViewHolder();
				vh.tvTitle = (TextView) view.findViewById(R.id.tv_title);
				vh.tvDescription = (TextView) view.findViewById(R.id.tv_description);
				view.setTag(vh);
			}else{
				vh = (ViewHolder) view.getTag();
			}
			GoodsCategory goodsCategory = mList.get(arg0);
			vh.tvTitle.setText(goodsCategory.categoryName);
			vh.tvDescription.setText(goodsCategory.description);
			
			return view;
		}
		class ViewHolder{
			TextView tvTitle;
			TextView tvDescription;
		}
	}

}
