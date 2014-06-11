package com.example.fragmenttest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TitleListFragment extends Fragment implements OnItemClickListener{
	ListView lvTitleList;

	private OnPerformNewsDetail mPerformNewsDetail;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_title_list, null);
		lvTitleList = (ListView) view.findViewById(R.id.lv_title_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, Data.sNewsTitle);
		lvTitleList.setAdapter(adapter);
		lvTitleList.setOnItemClickListener(this);
		Log.i("life", "Fragment===================onCreateView");
		return view;
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		if(activity instanceof OnPerformNewsDetail){
			mPerformNewsDetail = (OnPerformNewsDetail) activity;
		}else{
			throw new ClassCastException("activity is not a instance of OnPerformNewsDetail.");
		}
		Log.i("life", "Fragment===================onAttach");
		super.onAttach(activity);
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		mPerformNewsDetail.performNewsDetail(arg2);// 回调方法
	}


	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		mPerformNewsDetail = null;
		Log.i("life", "Fragment===================onDetach");
		super.onDetach();
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("life", "Fragment===================onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("life", "Fragment===================onCreate");
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("life", "Fragment===================onDestroy");
		super.onDestroy();
	}


	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.i("life", "Fragment===================onDestroyView");
		super.onDestroyView();
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.i("life", "Fragment===================onPause");
		super.onPause();
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Log.i("life", "Fragment===================onResume");
		super.onResume();
	}


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.i("life", "Fragment===================onStart");
		super.onStart();
	}


	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.i("life", "Fragment===================onStop");
		super.onStop();
	}
}