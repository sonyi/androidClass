package com.mymusicplay.ui;


import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mymusicplay.R;
import com.mymusicplay.data.AlbumDataAccess;
import com.mymusicplay.model.Album;
import com.mymusicplay.model.Music;
import com.mymusicplay.util.BitmapWorker;

public class FragmentTabAlbum extends Fragment{
	GridView mAlbumGridView;
	List<Album> mAlbumList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tab_fragment_album, null);
		mAlbumGridView = (GridView) view.findViewById(R.id.gv_fragment_song);
		
		mAlbumList = new AlbumDataAccess(getActivity()).getAllAlbumList();
		
		mAlbumGridView.setAdapter(myAlbumAdapter);
		
		return view;
	}
	
	ListAdapter myAlbumAdapter = new BaseAdapter() {
		@SuppressWarnings({ "unused", "null" })
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = null;
			ViewHolder vh = null;
			if (view == null) {
				view = getLayoutInflater(null).inflate(
						R.layout.fragment_album_adapter, null);
				vh = new ViewHolder();
				vh.img = (ImageView) view
						.findViewById(R.id.iv_fragment_album_img);
				vh.title = (TextView) view
						.findViewById(R.id.tv_fragment_album_title);
				vh.artist = (TextView) view
						.findViewById(R.id.tv_fragment_album_artist);
				vh.overFlow = (ImageView) view
						.findViewById(R.id.iv_fragment_album_overflow);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}
			
			Album album = mAlbumList.get(position);
			
			new BitmapWorker(getActivity()).fetch(album.getAlbumArt(), vh.img);
			
			vh.title.setText(album.getAlbum());
			vh.artist.setText(album.getArtist());
			
			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return mAlbumList.get(position).getId();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mAlbumList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mAlbumList.size();
		}

		class ViewHolder {
			ImageView img;
			TextView title;
			TextView artist;
			ImageView overFlow;
		}
	};
}
