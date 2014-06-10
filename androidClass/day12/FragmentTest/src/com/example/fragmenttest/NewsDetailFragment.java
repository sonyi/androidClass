package com.example.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsDetailFragment extends Fragment{
	private TextView tvDetail;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_news_detail, null);
		tvDetail = (TextView) view.findViewById(R.id.tv_detail);
		Bundle data = getArguments();
		if(data != null){
			int id = data.getInt(MainActivity.KEY_TITLE_ID);
			showNewsDetail(id);
		}
		return view;
	}
	
	



	public void showNewsDetail(int id){
		String detail = Data.sNewsDetail[id];
		tvDetail.setText(detail);
	}
}
