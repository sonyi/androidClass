package com.example.mybookstore.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
import com.example.mybookstore.util.Literal;

public class TabFrame extends Fragment {
	ListView mlistView = null;
	ArrayList<BooksBrief> mBooks = null;
	DataAccess dataAccess = null;
	BooksAdapter mBooksAdapter = null;
	BooksBrief changeBook = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frame_tab, null);
		mlistView = (ListView) view.findViewById(R.id.lv_frame_tab);
		init();
		mBooksAdapter = new BooksAdapter();
		mlistView.setAdapter(mBooksAdapter);
		mlistView.setOnItemClickListener(BookOnItemClickListener);
		return view;
	}

	private void init() {
		Bundle data = getArguments();
		long catagoryId = data.getLong(MainActivity.KEY_VALUE);
		dataAccess = new DataAccess(getActivity());
		mBooks = dataAccess.queryBooksBrief(catagoryId);

	}

	private class BooksAdapter extends BaseAdapter {

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
			if (view == null) {
				view = getLayoutInflater(null).inflate(R.layout.tab_frame_view,
						null);
				vh = new ViewHolder();
				vh.pic = (ImageView) view.findViewById(R.id.iv_book_img);
				vh.title = (TextView) view.findViewById(R.id.tv_book_title);
				vh.author = (TextView) view.findViewById(R.id.tv_book_auth);
				vh.price = (TextView) view.findViewById(R.id.tv_book_price);
				vh.btnOverflow = (ImageButton) view
						.findViewById(R.id.tv_book_overflow);
				view.setTag(vh);

			} else {
				vh = (ViewHolder) view.getTag();
			}

			BooksBrief b = mBooks.get(arg0);
			new ImageWorker().fetch(vh.pic, b.getBookArt(), 4);
			vh.title.setText(b.getBookTitle());
			vh.author.setText("作者:" + b.getBookAuthor());
			vh.price.setText("￥" + b.getBookPrice());
			vh.btnOverflow
					.setOnClickListener(new bookOverflowOnClickListener(b));
			changeBook = b;
			return view;
		}

		class ViewHolder {
			private ImageView pic;
			private TextView title;
			private TextView author;
			private TextView price;
			private ImageButton btnOverflow;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode != getActivity().RESULT_OK){
			return;
		}
		if(requestCode == Literal.RESULT_CODE){
			Books book = (Books) data.getSerializableExtra(Literal.ALTER_INTENT);
			//BooksBrief oldBook = (BooksBrief) data.getSerializableExtra(Literal.ALTER_INTENT);
			BooksBrief oldBook = null;
			for(BooksBrief b : mBooks){
				if(b.getBook_id() == book.getBook_id()){
					oldBook = b;
				}
			}
			
			
			int count = dataAccess.updateBooks(book.getBook_id(), book);
			if(count != 0){
				BooksBrief b = new BooksBrief();
				b.setBook_id(book.getBook_id());
				b.setBookTitle(book.getBookTitle());
				b.setBookAuthor(book.getBookAuthor());
				b.setBookPrice(book.getBookPrice());
				b.setBookArt(book.getBookArt());
				mBooks.remove(oldBook);
				mBooks.add(b);
				mBooksAdapter.notifyDataSetChanged();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private OnItemClickListener BookOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), DetailActivity.class);
			Bundle data = new Bundle();
			data.putLong(Literal.ACTIVITY_INTENT, mBooks.get(arg2).getBook_id());
			intent.putExtras(data);
			startActivity(intent);
		}

	};

	private class bookOverflowOnClickListener implements OnClickListener {
		private BooksBrief book;

		bookOverflowOnClickListener(BooksBrief book) {
			this.book = book;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PopupMenu menu = new PopupMenu(getActivity(), v);
			menu.inflate(R.menu.book_item_pup);
			menu.setOnMenuItemClickListener(new menuOnMenuItemClickListener(
					book));
			menu.show();
		}
	};

	private class menuOnMenuItemClickListener implements
			OnMenuItemClickListener {
		private BooksBrief book;

		menuOnMenuItemClickListener(BooksBrief book) {
			this.book = book;
		}

		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			// TODO Auto-generated method stub
			if (arg0.getItemId() == R.id.action_update_book) {
				Intent intent = new Intent(getActivity(),AlterActivity.class);
				Bundle data = new Bundle();
				//data.putSerializable(Literal.ALTER_INTENT, book);
				
				data.putLong(Literal.ALTER_INTENT, book.getBook_id());
				intent.putExtras(data);
				
				startActivityForResult(intent,Literal.RESULT_CODE);
				
//				Toast.makeText(getActivity(), "update--" + book.getBook_id(),
//						Toast.LENGTH_SHORT).show();

			}
			if (arg0.getItemId() == R.id.action_remove_book) {
				int count = dataAccess.removeBook(book.getBook_id());
				if (count == 1) {
					mBooks.remove(book);
					mBooksAdapter.notifyDataSetChanged();
					Toast.makeText(getActivity(),
							book.getBookTitle() + ":删除成功！", Toast.LENGTH_SHORT)
							.show();
				}
				return true;
			}
			return false;
		}
	};
}
