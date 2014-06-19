package com.mybookcollection.ui;

import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mybookcollection.MyApplication;
import com.mybookcollection.R;
import com.mybookcollection.data.DataUtil;
import com.mybookcollection.model.BookBrief;
import com.mybookcollection.util.StorageUtil;
import com.mybookcollection.util.ToastUtil;

/**
 * 以列表形式显示图书信息的Fragment
 */
public class BookListFragment extends Fragment {
	private ListView lvBookList;
	private BookListViewAdapter mAdapter;
	private List<BookBrief> mBookList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book_list, null);

		// 获取数据
		Bundle args = getArguments();
		long catagoryId = args.getLong("ctgrId");
		mBookList = new DataUtil(getActivity())
				.getBookListByCatagoryId(catagoryId);

		lvBookList = (ListView) view.findViewById(R.id.lv_book_list);
		// 设置适配器，加载数据
		if (mAdapter == null) {
			mAdapter = new BookListViewAdapter();
			lvBookList.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}

		lvBookList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						BookDetailActivity.class);

				BookBrief book = mBookList.get(position);
				MyApplication app = (MyApplication) getActivity()
						.getApplication();
				app.addArg("data", book);

				startActivity(intent);
			}
		});

		return view;
	}

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
				view = getActivity().getLayoutInflater().inflate(
						R.layout.view_book_item, null);
				viewHolder = new ViewHolder();
				viewHolder.imgBookArt = (ImageView) view
						.findViewById(R.id.img_book_art);
				viewHolder.tvBookTitle = (TextView) view
						.findViewById(R.id.tv_book_title);
				viewHolder.tvBookAuthor = (TextView) view
						.findViewById(R.id.tv_book_author);
				viewHolder.btnOverFlow = (ImageButton) view
						.findViewById(R.id.btn_over_flow);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			final BookBrief book = mBookList.get(position);
			Bitmap bm = StorageUtil.getBookArt(book.getArt());
			viewHolder.imgBookArt.setImageBitmap(bm);
			viewHolder.tvBookTitle.setText(book.getTitle());
			viewHolder.tvBookAuthor.setText(book.getAuthor());

			viewHolder.btnOverFlow
					.setOnClickListener(new BtnOverFlowOnClickListener(book));

			return view;
		}

		private class BtnOverFlowOnClickListener implements OnClickListener {
			private BookBrief mBook;

			BtnOverFlowOnClickListener(BookBrief book) {
				mBook = book;
			}

			@Override
			public void onClick(View v) {
				PopupMenu menu = new PopupMenu(getActivity(), v);
				menu.inflate(R.menu.book_item_popup);
				menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						if (item.getItemId() == R.id.action_update_book) {
							// 执行更新图书信息的操作
							updateBook();
							return true;
						}
						if (item.getItemId() == R.id.action_remove_book) {
							removeBook();
							return true;
						}
						return false;
					}

					private void updateBook() {
						//
					}

					private void removeBook() {
						// 删除图书信息
						int count = new DataUtil(getActivity())
								.removeBookById(mBook.getId());
						if (count == 1) {
							ToastUtil.showToast(getActivity(), "删除图书信息成功.");
							mBookList.remove(mBook);
							mAdapter.notifyDataSetChanged();
						}
					}
				});
				menu.show();
			}
		};

		private class ViewHolder {
			ImageView imgBookArt;
			TextView tvBookTitle;
			TextView tvBookAuthor;
			ImageButton btnOverFlow;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
