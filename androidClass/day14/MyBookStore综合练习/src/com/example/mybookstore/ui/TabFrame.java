package com.example.mybookstore.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.MyApplication;
import com.example.mybookstore.R;
import com.example.mybookstore.data.DataAccess;
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
	long mCatagoryID;
	String mtitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frame_tab, null);
		mlistView = (ListView) view.findViewById(R.id.lv_frame_tab);
		mCatagoryID = getCatagoryID();
		mBooks = getBookBrief(mCatagoryID, null);
		
		
		setHasOptionsMenu(true);
		mBooksAdapter = new BooksAdapter();
		mlistView.setAdapter(mBooksAdapter);
		mlistView.setOnItemClickListener(BookOnItemClickListener);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
		searchView.setOnQueryTextListener(mOnQueryTextListener);
	}

	final long cagID = mCatagoryID;
	private OnQueryTextListener mOnQueryTextListener = new OnQueryTextListener() {

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onQueryTextChange(String arg0) {
			// TODO Auto-generated method stub
			if(arg0 == null || arg0.equals("")){
				return true;
			}else {
				if(!mBooks.isEmpty()){
					mBooks = getBookBrief(mBooks.get(0).getBook_CatagoryId(), null);
					mBooksAdapter.notifyDataSetChanged();	
				}

				Toast.makeText(getActivity(), "okdok--" + arg0, Toast.LENGTH_SHORT).show();
			}

			return true;
			
		}
	};

	// 接收MainActivity数据，并从数据库中查询相应的图书(简化)信息
	private long getCatagoryID() {
		Bundle data = getArguments();
		long catagoryId = data.getLong(Literal.FRAGMENT_KEY_VALUE);
		Log.i("cat", catagoryId + "");
		return catagoryId;
	}

	private ArrayList<BooksBrief> getBookBrief(long catagoryId, String title) {
		if (dataAccess == null) {
			dataAccess = new DataAccess(getActivity());
		}
		mBooks = dataAccess.queryBooksBrief(catagoryId, title);
		return mBooks;
	}

	// 设置ListView适配器
	private class BooksAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mBooks.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mBooks.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return mBooks.get(arg0).getBook_id();
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
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
				vh.btnOverflow = (ImageView) view
						.findViewById(R.id.tv_book_overflow);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}

			BooksBrief booksBrief = mBooks.get(arg0);
			// 开启工作线程，获取图片资源，并对图片进行压缩
			new ImageWorker().fetch(vh.pic, booksBrief.getBookArt(), 4);
			vh.title.setText(booksBrief.getBookTitle());
			vh.author.setText("作者:" + booksBrief.getBookAuthor());
			vh.price.setText("￥" + booksBrief.getBookPrice());
			vh.btnOverflow.setOnClickListener(new bookOverflowOnClickListener(
					booksBrief));
			changeBook = booksBrief;
			return view;
		}

		class ViewHolder {
			private ImageView pic;
			private TextView title;
			private TextView author;
			private TextView price;
			private ImageView btnOverflow;
		}
	}

	// 监听其他Activity返回的信息
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != getActivity().RESULT_OK) {
			return;
		}
		if (requestCode == Literal.RESULT_CODE) {
			if (data.getIntExtra(Literal.ALTER_INTENT_BACK, 0) != 0) {
				// 通过Application获取对象
				MyApplication app = (MyApplication) getActivity()
						.getApplication();
				Books book = (Books) app.getArg(Literal.BOOKS_VALUE);
				app.removeArg(Literal.BOOKS_VALUE);

				BooksBrief updateBook = null;
				for (BooksBrief b : mBooks) {
					if (b.getBook_id() == book.getBook_id()) {
						updateBook = b;
					}
				}
				updateBook.setBook_id(book.getBook_id());
				updateBook.setBookTitle(book.getBookTitle());
				updateBook.setBookAuthor(book.getBookAuthor());
				updateBook.setBookPrice(book.getBookPrice());
				updateBook.setBookArt(book.getBookArt());
				mBooksAdapter.notifyDataSetChanged();// 刷新界面
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 设置图书列表监听器
	private OnItemClickListener BookOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(getActivity(), DetailActivity.class);
			Bundle data = new Bundle();
			data.putLong(Literal.ACTIVITY_INTENT, mBooks.get(arg2).getBook_id());
			intent.putExtras(data);
			startActivity(intent);
		}
	};

	// 设置图书列表中ImageView(更多操作)监听器
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

	// 设置popupMenu监听器
	private class menuOnMenuItemClickListener implements
			OnMenuItemClickListener {
		private BooksBrief book;

		menuOnMenuItemClickListener(BooksBrief book) {
			this.book = book;
		}

		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			// 更新图书信息
			if (arg0.getItemId() == R.id.action_update_book) {
				Intent intent = new Intent(getActivity(), AlterActivity.class);
				Bundle data = new Bundle();
				data.putLong(Literal.ALTER_INTENT, book.getBook_id());
				intent.putExtras(data);
				startActivityForResult(intent, Literal.RESULT_CODE);
				return true;
			}
			// 删除图书信息
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
