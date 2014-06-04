package com.example.homeactivity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	ListView mInfoList;
	List<Feed> mImgSourse;
	FeedAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mInfoList = (ListView) findViewById(R.id.lv_info);
		initData();
		mAdapter = new FeedAdapter();
		
		
//		View headView = getLayoutInflater().inflate(R.layout.view_info_header, null);
//		mInfoList.addHeaderView(headView);
//		
//		View infoView = getLayoutInflater().inflate(R.layout.view_info_item, null);
		mInfoList.setAdapter(mAdapter);
	}
	
	public void initData(){
		mImgSourse = new ArrayList<Feed>();
		Feed img1 = new Feed(1,"ÃÀÅ®Í¼Æ¬",R.drawable.img_peri_01,"13:28");
		Feed img2 = new Feed(2,"¶¯ÎïÍ¼Æ¬",R.drawable.img_animal_01,"15:22");
		Feed img3 = new Feed(3,"·ç¾°Í¼Æ¬",R.drawable.img_scenery_01,"17:58");
		Feed img4 = new Feed(4,"¶¯ÂþÍ¼Æ¬",R.drawable.img_animation_01,"3:31");
		Feed img5 = new Feed(5,"ÉãÓ°Í¼Æ¬",R.drawable.img_photo_01,"7:31");
		
		mImgSourse.add(img1);
		mImgSourse.add(img2);
		mImgSourse.add(img3);
		mImgSourse.add(img4);
		mImgSourse.add(img5);
	}
	
	private class FeedAdapter extends BaseAdapter{
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
			ViewHolder vh = null;
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.view_info_left_item, null);
				vh = new ViewHolder();
				vh.imgLeftTitle = (TextView) view.findViewById(R.id.tv_left_text);
				vh.imgLeftRes = (ImageView) view.findViewById(R.id.iv_left_img);
				vh.imgLeftTime = (TextView) view.findViewById(R.id.tv_left_time);
				vh.imgRightTitle = (TextView) view.findViewById(R.id.tv_right_text);
				vh.imgRightRes = (ImageView) view.findViewById(R.id.iv_right_img);
				vh.imgRightTime = (TextView) view.findViewById(R.id.tv_right_time);
				view.setTag(vh);
			}else{
				vh = (ViewHolder) view.getTag();
			}
			
			Feed feedLeftData = mImgSourse.get(2*position);
			vh.imgLeftTitle.setText(feedLeftData.getImgTitle());
			vh.imgLeftRes.setImageResource(feedLeftData.getImgResId());
			vh.imgLeftTime.setText(feedLeftData.getImgTime());
			if(2*position < mImgSourse.size()-1){
				Log.i("position",position + "------------" + mImgSourse.size());
				
				
				Feed feedRightData = mImgSourse.get(2*position+1);
				vh.imgRightTitle.setText(feedRightData.getImgTitle());
				vh.imgRightRes.setImageResource(feedRightData.getImgResId());
				vh.imgRightTime.setText(feedRightData.getImgTime());
			}else{
				vh.imgRightTime.setVisibility(View.INVISIBLE);
				RelativeLayout rlRight = (RelativeLayout) view.findViewById(R.id.rl_info_right);
				rlRight.setVisibility(View.INVISIBLE);
			}
			return view;
		}
		
		class ViewHolder{
			TextView imgLeftTitle;
			ImageView imgLeftRes;
			TextView imgLeftTime;
			TextView imgRightTitle;
			ImageView imgRightRes;
			TextView imgRightTime;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return mImgSourse.get(position).getId();
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mImgSourse.get(position);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return (mImgSourse.size()+1)/2;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
