package com.mymusicplay.ui;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.data.MusicDataAccess;
import com.mymusicplay.model.Music;
import com.mymusicplay.server.IPlayBackService;

public class FragmentTabMusic extends Fragment {
	ListView mMusicListView;
	List<Music> mMusic;
	IPlayBackService myService; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_fragment_music, null);
		
		mMusicListView = (ListView) view.findViewById(R.id.lv_fragment_song);
		mMusic = new MusicDataAccess(getActivity()).getAllMusic();
		mMusicListView.setAdapter(myMusicAdapter);
		return view;
	}
	

	ListAdapter myMusicAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
			ViewHolder vh = null;
			if (view == null) {
				view = getLayoutInflater(null).inflate(
						R.layout.fragment_music_adapter, null);
				vh = new ViewHolder();
				vh.title = (TextView) view
						.findViewById(R.id.tv_fragment_music_title);
				vh.singer = (TextView) view
						.findViewById(R.id.tv_fragment_music_singer);
				vh.overFlow = (ImageView) view
						.findViewById(R.id.iv_fragment_overflow);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}
			
			Music music = mMusic.get(position);
			//Log.i("music", music.toString());
			
			vh.title.setText(music.getTitle());
			vh.singer.setText(music.getArtist());
			view.setOnClickListener(new myViewOnClickListener(music));
			return view;
		}

		@Override
		public long getItemId(int position) {
			return mMusic.get(position).getId();
		}

		@Override
		public Object getItem(int position) {
			return mMusic.get(position);
		}

		@Override
		public int getCount() {
			return mMusic.size();
		}

		class ViewHolder {
			TextView title;
			TextView singer;
			ImageView overFlow;
		}
	};
	
	
	private class myViewOnClickListener implements OnClickListener {
		Music music;
		private myViewOnClickListener(Music music){
			this.music = music;
		}
		
		@Override
		public void onClick(View v) {
			myService = PlayBackServiceManager.getPlayBackService(getActivity());
			myService.addToPlayQueue(mMusic);
			int index = myService.getCurrentMusicIndex(music);
			
			//int index = myService.addToPlayQuene(music);
			
			//myService.stop();
			myService.playAtIndex(index);
			
		}
	};
}
