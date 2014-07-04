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
import com.mymusicplay.server.IPlayBackService;

public class MatchLrcForMusic {
	private Context context;
	private LyricView lyricView;
	private IPlayBackService service;
	private Lyric mLyric;
	private String musicName;
	
	public MatchLrcForMusic(Context context,LyricView lyricView,String musicName){
		this.context = context;
		this.lyricView = lyricView;
		this.musicName = musicName;
		init();
	}
	
	
	private void init(){
		service = PlayBackServiceManager.getPlayBackService(context);
//		PlayListItem pli = new PlayListItem(musciName,
//				"sdcard/music/" + musciName + ".mp3", 0L, true);
//		mLyric = new Lyric(new File("sdcard/musci/" + musciName + ".lrc"), pli);
		
		//String musicName = "01";
		PlayListItem pli = new PlayListItem(musicName,
				"sdcard/music/" + musicName + ".mp3", 0L, true);
		mLyric = new Lyric(new File(
				"sdcard/music/" + musicName + ".lrc"), pli);
		
		lyricView.setmLyric(mLyric);
		lyricView.setSentencelist(mLyric.list);
		
		lyricView.setNotCurrentPaintColor(0xffFEB660);
		lyricView.setCurrentPaintColor(Color.RED);
		lyricView.setLrcTextSize(50);
		lyricView.setTexttypeface(Typeface.SERIF);
		lyricView.setBrackgroundcolor(Color.BLACK);
		lyricView.setTextHeight(65);
		new Thread(new UIUpdateThread()).start();
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
