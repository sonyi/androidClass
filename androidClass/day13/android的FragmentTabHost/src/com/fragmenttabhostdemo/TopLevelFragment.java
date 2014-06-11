package com.fragmenttabhostdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopLevelFragment extends Fragment {
	private TextView tvContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_top_level, null);
		tvContent = (TextView) view.findViewById(R.id.tv_content);
		
		Bundle args = getArguments();
		String name = args.getString(MainActivity.KEY_NAME);
		tvContent.setText(name);
		
		return view;
	}
}
