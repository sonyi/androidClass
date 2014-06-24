package com.musicplay.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.musicplay.data.AlbumDataAccess;
import com.musicplay.model.Album;
import com.musicplaytesty.R;

public class AlbumFragment extends Fragment {

	private GridView gvAlbumView;
	private List<Album> mAlbumList;
	private AlbumGridViewAdapter mAlbumAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_album_grid, null);

		mAlbumList = new AlbumDataAccess(getActivity()).getAlbumList();
		gvAlbumView = (GridView) view.findViewById(R.id.gv_album_grid);
		if (mAlbumAdapter == null) {
			mAlbumAdapter = new AlbumGridViewAdapter();
			gvAlbumView.setAdapter(mAlbumAdapter);
		} else {
			mAlbumAdapter.notifyDataSetChanged();
		}

		return view;
	}

	private class AlbumGridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mAlbumList.size();
		}

		@Override
		public Object getItem(int position) {
			return mAlbumList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mAlbumList.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder vh = null;
			if (view == null) {
				view = getActivity().getLayoutInflater().inflate(
						R.layout.view_album_item, null);
				vh = new ViewHolder();
				vh.imgAlbumArt = (ImageView) view
						.findViewById(R.id.img_album_art);
				vh.tvAlbumTitle = (TextView) view
						.findViewById(R.id.tv_album_title);
				vh.tvAlbumArtist = (TextView) view
						.findViewById(R.id.tv_album_artist);
				vh.btnAlbumMore = (ImageButton) view.findViewById(R.id.btn_album_more);
				
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}
			Album album = mAlbumList.get(position);
			new com.musicplay.util.BitmapWorker().fetch(album.getAlbumArt(),
					vh.imgAlbumArt);
			vh.tvAlbumTitle.setText(album.getAlbum());
			vh.tvAlbumArtist.setText(album.getArtist());
			//vh.btnAlbumMore.set
			//立即播放，添加到队列
			//vh.imgAlbumArt.setOnClickListener(mImgAlbumArt);
			return view;
		}

		private class ViewHolder {
			ImageView imgAlbumArt;
			TextView tvAlbumTitle;
			TextView tvAlbumArtist;
			ImageButton btnAlbumMore;
		}
	}
	private OnClickListener mImgAlbumArt = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(),AlbumActivity.class);
//			startActivity(intent);
			
		}
	};
	
}
