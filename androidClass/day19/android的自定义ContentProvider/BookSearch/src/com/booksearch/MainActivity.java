package com.booksearch;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mybookcollection.model.BookBrief;
import com.mybookcollection.provider.MetaDataContract.BookContract;

public class MainActivity extends ActionBarActivity {
	private ListView lvBookList;
	private BookListViewAdapter mAdapter;

	private List<BookBrief> mBookList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lvBookList = (ListView) findViewById(R.id.lv_book_list);

		getBookListByKey("");
		mAdapter = new BookListViewAdapter();
		lvBookList.setAdapter(mAdapter);
	}

	private void getBookListByKey(String key) {
		if (mBookList == null) {
			mBookList = new ArrayList<BookBrief>();
		}
		
		mBookList.clear();

		Uri uri = BookContract.CONTENT_URI;
		String[] projection = new String[] { BookContract._ID,
				BookContract.TITLE, BookContract.AUTHOR, BookContract.ART };
		String selection = BookContract.TITLE + " like '" + key + "%'";

		ContentResolver cr = getContentResolver();
		Cursor c = cr.query(uri, projection, selection, null, BookContract._ID);

		while (c.moveToNext()) {
			BookBrief book = new BookBrief();
			book.setId(c.getLong(c.getColumnIndexOrThrow(BookContract._ID)));
			book.setTitle(c.getString(c
					.getColumnIndexOrThrow(BookContract.TITLE)));
			book.setArt(c.getString(c.getColumnIndexOrThrow(BookContract.ART)));
			book.setAuthor(c.getString(c
					.getColumnIndexOrThrow(BookContract.AUTHOR)));
			mBookList.add(book);
		}
		c.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
		searchView.setOnQueryTextListener(mOnQueryTextListener);
		return true;
	}

	private OnQueryTextListener mOnQueryTextListener = new OnQueryTextListener() {
		@Override
		public boolean onQueryTextChange(String text) {
			getBookListByKey(text);
			mAdapter.notifyDataSetChanged();
			return true;
		}

		@Override
		public boolean onQueryTextSubmit(String text) {
			return false;
		}
	};

	private class BookListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mBookList.size();
		}

		@Override
		public Object getItem(int position) {
			return mBookList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mBookList.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder viewHolder = null;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.view_book_item,
						null);
				viewHolder = new ViewHolder();
				viewHolder.imgBookArt = (ImageView) view
						.findViewById(R.id.img_book_art);
				viewHolder.tvBookTitle = (TextView) view
						.findViewById(R.id.tv_book_title);
				viewHolder.tvBookAuthor = (TextView) view
						.findViewById(R.id.tv_book_author);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			final BookBrief book = mBookList.get(position);
			// Bitmap bm = StorageUtil.getBookArt(book.getArt());
			// viewHolder.imgBookArt.setImageBitmap(bm);
			viewHolder.tvBookTitle.setText(book.getTitle());
			viewHolder.tvBookAuthor.setText(book.getAuthor());
			return view;
		}

		private class ViewHolder {
			ImageView imgBookArt;
			TextView tvBookTitle;
			TextView tvBookAuthor;
		}
	}

	// 使用ContentProvider添加数据
	private void addBook() {
		ContentValues cv = new ContentValues();
		cv.put(BookContract.TITLE, "飘");
		cv.put(BookContract.AUTHOR, "米歇尔");
		cv.put(BookContract.CATAGORY_ID, 1);
		cv.put(BookContract.PRICE, 30);
		cv.put(BookContract.ART, "gwtw.jpg");

		Uri uri = BookContract.CONTENT_URI;
		ContentResolver cr = getContentResolver();
		Uri result = cr.insert(uri, cv);
		Log.i("book_search", result.toString());
	}
}
