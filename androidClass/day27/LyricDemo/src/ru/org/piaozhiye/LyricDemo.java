package ru.org.piaozhiye;

import java.io.File;
import java.io.IOException;

import ru.org.piaozhiye.lyric.Lyric;
import ru.org.piaozhiye.lyric.LyricView;
import ru.org.piaozhiye.lyric.PlayListItem;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class LyricDemo extends Activity {
	private MediaPlayer mp;
	private LyricView lyricView;
	private String path = "/sdcard/aix.mp3";
	public static Lyric mLyric;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lyricView = (LyricView) findViewById(R.id.audio_lrc);
		mp = new MediaPlayer();
		mp.reset();
		try {
			mp.setDataSource(path);
			mp.prepare();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (IllegalStateException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		mp.start();

		PlayListItem pli = new PlayListItem("aix",
				"sdcard/aix.mp3", 0L, true);
		mLyric = new Lyric(new File(
				"sdcard/aix.lrc"), pli);
		
		lyricView.setmLyric(mLyric);
		lyricView.setSentencelist(mLyric.list);
		lyricView.setNotCurrentPaintColor(Color.GREEN);
		lyricView.setCurrentPaintColor(Color.YELLOW);
		lyricView.setLrcTextSize(20);
		lyricView.setTexttypeface(Typeface.SERIF);
		lyricView.setBrackgroundcolor(Color.BLACK);
		lyricView.setTextHeight(40);
		new Thread(new UIUpdateThread()).start();

	}

	class UIUpdateThread implements Runnable {
		long time = 100; 

		public void run() {

			while (mp.isPlaying()) {
				lyricView.updateIndex(mp.getCurrentPosition());
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