package com.musicplay.ui;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.musicplay.PlayBackServiceManager;
import com.musicplay.data.AlbumDataAccess;
import com.musicplay.data.MusicDataAccess;
import com.musicplay.model.Music;
import com.musicplay.service.IPlayBackService;
import com.musicplaytesty.R;

public class TracksFragment extends Fragment {
	
	private ListView lvTracksView;
	private List<Music> mMusicList;
	private TracksListViewAdapter mTracksListViewAdapter;
	private IPlayBackService myService;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tracks_list, null);
		
		mMusicList = new MusicDataAccess(getActivity()).getAllMusic();
		lvTracksView = (ListView) view.findViewById(R.id.lv_tracks_list);
		if (mTracksListViewAdapter == null) {
			mTracksListViewAdapter = new TracksListViewAdapter();
			lvTracksView.setAdapter(mTracksListViewAdapter);
		} else {
			mTracksListViewAdapter.notifyDataSetChanged();
		}

		return view;
	}
	
	private class TracksListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mMusicList.size();
		}

		@Override
		public Object getItem(int position) {
			return mMusicList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mMusicList.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder vh = null;
			if(view == null){
				view = getActivity().getLayoutInflater().inflate(R.layout.view_tracks_item, null);
				vh = new ViewHolder();
				vh.imgTracksArt = (ImageView) view.findViewById(R.id.img_tracks_art);
				vh.tvTracksTitle = (TextView) view.findViewById(R.id.tv_tracks_title);
				vh.tvTracksArtist = (TextView) view.findViewById(R.id.tv_tracks_artist);
				vh.btnTracksMore = (ImageButton) view.findViewById(R.id.btn_tracks_more);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			} 
			final Music music = mMusicList.get(position);
			String path = new AlbumDataAccess(getActivity()).getAlbumArtByAlbumId(music.getAlbumId());
			new com.musicplay.util.BitmapWorker().fetch(path, vh.imgTracksArt);
			vh.tvTracksTitle.setText(music.getDisplayName());
			vh.tvTracksArtist.setText(music.getArtist());
			//vh.btnTracksMore;//添加到队列
			vh.imgTracksArt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					myService = PlayBackServiceManager.getPlayBackService(getActivity());

					int index = myService.addToPlayQueue(music);
					myService.stop();
					myService.playAtIndex(index);;
				}
			});
			
			return view;
		}
		class ViewHolder{
			ImageView imgTracksArt;
			TextView tvTracksTitle;
			TextView tvTracksArtist;
			ImageButton btnTracksMore;
		}
	}
	
}
