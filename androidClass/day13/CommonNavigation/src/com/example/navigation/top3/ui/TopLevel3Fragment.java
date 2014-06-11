package com.example.navigation.top3.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigation.R;

public class TopLevel3Fragment extends Fragment {
	private TextView tvTopLevelText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.fragment_top_level_demo, null);
		initWidgets(rootView);
		return rootView;
	}

	private void initWidgets(View rootView) {
		this.tvTopLevelText = (TextView) rootView
				.findViewById(R.id.tv_top_level_text);
		this.tvTopLevelText.setText("This is top level 3.");
	}
}
