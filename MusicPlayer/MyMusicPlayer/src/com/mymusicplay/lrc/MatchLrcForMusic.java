package com.mymusicplay.lrc;

import java.io.File;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.lrc.control.Lyric;
import com.mymusicplay.lrc.control.LyricView;
import com.mymusicplay.lrc.control.PlayListItem;
import com.mymusicplay.model.Music;
import com.mymusicplay.server.IPlayBackService;

public class MatchLrcForMusic {
	private Context context;
	private LyricView lyricView;
	private IPlayBackService service;
	private Lyric mLyric;
	private Music music;
	
	public MatchLrcForMusic(Context context,LyricView lyricView,Music music){
		this.context = context;
		this.lyricView = lyricView;
		this.music = music;
		init();
	}
	
	
	private void init(){
		service = PlayBackServiceManager.getPlayBackService(context);
		String musicPlayName = music.getDisplayName();
		String musicName = musicPlayName.substring(0, musicPlayName.indexOf("."));
		String musicLrcPath = new GetLrcFromSdcardByMusicName().getLrcPath(musicName);
				
		if(musicLrcPath != null && !musicLrcPath.equals("")){
			PlayListItem pli = new PlayListItem(musicName,music.getData(), 0L, true);
			mLyric = new Lyric(new File(musicLrcPath), pli);
			
			lyricView.setmLyric(mLyric);
			lyricView.setSentencelist(mLyric.list);
			
			lyricView.setNotCurrentPaintColor(0xffB3EF7D);
			lyricView.setCurrentPaintColor(Color.RED);
			lyricView.setLrcTextSize(35);
			lyricView.setTexttypeface(Typeface.SERIF);
			lyricView.setBrackgroundcolor(Color.BLACK);
			lyricView.setTextHeight(45);
			new Thread(new UIUpdateThread()).start();
		}
	}
	
	class UIUpdateThread implements Runnable {
		long time = 100; 

		public void run() {

			while (service.getMediaPlayer().isPlaying()) {
				lyricView.updateIndex(service.getMediaPlayer().getCurrentPosition());
				mHandler.post(mUpdateResults);
				try {
					Thread.sleep(time);

				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}

		}

	}

	Handler mHandler = new Handler();
	Runnable mUpdateResults = new Runnable() {
		public void run() {
			lyricView.invalidate();
		}
	};

}
