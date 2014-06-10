package com.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsDetailFragment extends Fragment {
	private TextView tvDetail;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news_detail, null);
        tvDetail = (TextView) view.findViewById(R.id.tv_detail);
		
		return view;
	}
}
