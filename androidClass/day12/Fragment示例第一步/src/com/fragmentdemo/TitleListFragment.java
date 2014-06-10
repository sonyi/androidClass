package com.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class TitleListFragment extends Fragment {
	private ListView lvTitleList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_title_list, null);
		lvTitleList = (ListView) view.findViewById(R.id.lv_title_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Data.sNewsTitles);
        lvTitleList.setAdapter(adapter);
		return view;
	}
}
