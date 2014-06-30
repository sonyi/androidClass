package com.mymusicplay.ui;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.R;
import com.mymusicplay.model.Music;
import com.mymusicplay.server.IPlayBackService;

public class MusicQueueDialog extends Dialog {
	private ListView mMusicQueueView;
	private List<Music> mMusicList;
	Activity activity;

	public MusicQueueDialog(Activity activity, int theme) {
		super(activity, theme);
		this.activity = activity;
		initDialog(activity);
	}
	
	
	private void initDialog(Context context) {
		setContentView(R.layout.dialog_music_queue);
		initMusicQueueView();

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);

		WindowManager m = activity.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		int heightWindow = d.getHeight();
		int widthWindow = d.getWidth();

		lp.height = (int) (heightWindow * 0.6); // 高度设置为屏幕的0.6
		lp.width = (int) (widthWindow * 0.65); // 宽度设置为屏幕的0.65
		lp.x = (int)(heightWindow * 0.01); // 新位置X坐标
		lp.y = (int) (widthWindow * 0.1); // 新位置Y坐标
		lp.alpha = 0.7f; // 透明度
		dialogWindow.setAttributes(lp);

		show();
	}

	private void initMusicQueueView() {
		mMusicQueueView = (ListView) findViewById(R.id.lv_music_queue);
		IPlayBackService mService = PlayBackServiceManager
				.getPlayBackService(activity);
		mMusicList = mService.getCurrentMusicList();
		if (mMusicList != null) {
			mMusicQueueView.setAdapter(musicQueueAdapter);
		}
	}

	private ListAdapter musicQueueAdapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder vh = null;
			if (view == null) {
				view = getLayoutInflater().inflate(
						R.layout.music_queue_adapter, null);
				vh = new ViewHolder();
				vh.mMusicId = (TextView) view
						.findViewById(R.id.tv_musicqueue_id);
				vh.mMusicDetail = (TextView) view
						.findViewById(R.id.tv_musicqueue_detail);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}

			Music music = mMusicList.get(position);
			vh.mMusicId.setText(String.valueOf(position + 1));
			vh.mMusicDetail.setText(music.getArtist() + "--" + music.getTitle());
			view.setOnClickListener(new MusicQueueOnClickListener(music));

			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mMusicList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mMusicList.size();
		}

		class ViewHolder {
			TextView mMusicId;
			TextView mMusicDetail;
		}
	};
	
	private class MusicQueueOnClickListener implements android.view.View.OnClickListener{
		Music music;
		MusicQueueOnClickListener(Music music){
			this.music = music;
		}
		@Override
		public void onClick(View v) {
			IPlayBackService service = PlayBackServiceManager.getPlayBackService(getContext());
			//service.stop();
			service.playAtIndex(service.getCurrentMusicIndex(music));
			cancel();
			dismiss();
		}
	}
}
