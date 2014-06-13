package com.example.mybookstore.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.R;
import com.example.mybookstore.data.DataAccess;
import com.example.mybookstore.data.DataContract;
import com.example.mybookstore.data.ImageWorker;
import com.example.mybookstore.model.Books;
import com.example.mybookstore.model.BooksBrief;

public class TabFrame extends Fragment{
	ListView mlistView = null;
	ArrayList<BooksBrief> mBooks = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frame_tab, null);
		mlistView = (ListView) view.findViewById(R.id.lv_frame_tab);
		init();
		mlistView.setAdapter(new BooksAdapter());
		mlistView.setOnItemClickListener(BookOnItemClickListener);
		return view;
	}
	
	private void init(){
		Bundle data = getArguments();
		long catagoryId = data.getLong(MainActivity.KEY_VALUE);
		//Log.i("key", catagoryId+ "");	
		DataAccess dataAccess = new DataAccess(getActivity());
		mBooks = dataAccess.queryBooksBrief(catagoryId);

	}
	
	private class BooksAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mBooks.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mBooks.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return mBooks.get(arg0).getBook_id();
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = arg1;
			ViewHolder vh = null;
			if(view == null){
				view = getLayoutInflater(null).inflate(R.layout.tab_frame_view, null);
				vh = new ViewHolder();
				vh.pic = (ImageView) view.findViewById(R.id.iv_book_img);
				vh.title = (TextView) view.findViewById(R.id.tv_book_title);
				vh.author = (TextView) view.findViewById(R.id.tv_book_auth);
				vh.price = (TextView) view.findViewById(R.id.tv_book_price);
				view.setTag(vh);
				
			}else{
				vh = (ViewHolder) view.getTag();
			}
			
			BooksBrief b = mBooks.get(arg0);
			new ImageWorker().fetch(vh.pic, b.getBookArt());
			vh.title.setText(b.getBookTitle());
			vh.author.setText(b.getBookAuthor());
			vh.price.setText(b.getBookPrice());
			return view;
		}
		
		
		
		class ViewHolder{
			private ImageView pic;
			private TextView title;
			private TextView author;
			private TextView price;
			
		}
		
	}
	
	private OnItemClickListener BookOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//Toast.makeText(getActivity(), "hahah---" + mBooks.get(arg2).getBookTitle(), Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getActivity(),DetailActivity.class);
			Bundle data = new Bundle();
			data.putSerializable(DataContract.ACTIVITY_INTENT, mBooks.get(arg2));
			intent.putExtras(data);
			startActivity(intent);
		}
		
	};

}
