package com.fragmentdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TitleListFragment extends Fragment implements OnItemClickListener {
	private ListView lvTitleList;
	
	private OnPerformNewsDetail mPerformNewsDetail;
	
	@Override
	public void onAttach(Activity activity) {
		if (activity instanceof OnPerformNewsDetail) {
			mPerformNewsDetail = (OnPerformNewsDetail) activity;
		} else {
			throw new ClassCastException("activity is not a instance of OnPerformNewsDetail.");
		}
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_title_list, null);
		lvTitleList = (ListView) view.findViewById(R.id.lv_title_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Data.sNewsTitles);
        lvTitleList.setAdapter(adapter);
        lvTitleList.setOnItemClickListener(this);
        
		return view;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mPerformNewsDetail.performNewsDetail(position);
	}
}
