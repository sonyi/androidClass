package com.fragmenttabhostdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

		tvContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SecondActivity.class);
				startActivity(intent);
			}
		});

		return view;
	}
}
