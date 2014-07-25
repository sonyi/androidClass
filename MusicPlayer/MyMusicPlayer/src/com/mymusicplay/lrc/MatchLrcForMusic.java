package com.mymusicplay.lrc;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.mymusicplay.PlayBackServiceManager;
import com.mymusicplay.data.LrcDataAccess;
import com.mymusicplay.lrc.control.Lyric;
import com.mymusicplay.lrc.control.LyricView;
import com.mymusicplay.lrc.control.PlayListItem;
import com.mymusicplay.model.Lrc;
import com.mymusicplay.model.Music;
import com.mymusicplay.server.IPlayBackService;

public class MatchLrcForMusic {
	private Context context;
	private LyricView lyricView;
	private IPlayBackService service;
	private Lyric mLyric;
	private Music music;

	public MatchLrcForMusic(Context context, LyricView lyricView, Music music) {
		this.context = context;
		this.lyricView = lyricView;
		this.music = music;
		init();
	}

	String musicName;
	private void init() {
		service = PlayBackServiceManager.getPlayBackService(context);
		musicName = music.getDisplayName();
		new GetLrcFromDb().execute(musicName);
	}

	//搜索数据集，查看歌词路径
	private class GetLrcFromDb extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			String name = params[0].substring(0, params[0].indexOf("."));
			return new LrcDataAccess(context).getLrcUrl(name);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				showLrc(musicName, result);
			} else {
				showDialog(context).show();
			}
		}
	}

	//数据库中没有歌词路径就弹窗对话框，提示是否搜索本地文件
	private ProgressDialog mDialog;
	private Dialog showDialog(Context context) {
		final Context c = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("还没有歌词，扫描下本地文件查找吧..");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				new SearchLrc().execute(musicName);
				mDialog = ProgressDialog.show(c, " ",
						"正在获取数据,请稍后... ", true);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		return builder.create();
	}

	//搜索本地文件，并将歌词添加到数据库中，再从数据库中获取数据
	private class SearchLrc extends AsyncTask<String, Void, String> {

		@SuppressLint("SdCardPath")
		@Override
		protected String doInBackground(String... params) {
			String name = params[0].substring(0, params[0].indexOf("."));
			File file = new File("/sdcard/");
			searchFiles(file);
			return new LrcDataAccess(context).getLrcUrl(name);
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mDialog.cancel();
			if (result != null) {
				showLrc(musicName, result);
			} else {
				Toast.makeText(context, "没找到歌词...", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	//递归搜索本地文件
	private void searchFiles(File f) { // 搜索sdcard中的所有lrc文件
		if (f != null) {// 耗时操作，应该在工作线程操作
			File files[] = f.listFiles();
			if (files != null && files.length > 0) {
				for (File tempF : files) {
					if (tempF.isDirectory()) {
						searchFiles(tempF);
					} else {
						String path = tempF.getPath();
						if (path.endsWith(".lrc")) {
							String lrcName = path.substring(
									path.lastIndexOf("/") + 1 , path.lastIndexOf("."));
							//判断是否已经存在
							String s = new LrcDataAccess(context).getLrcUrl(lrcName);
							if(s == null){
								Lrc lrc = new Lrc(lrcName, path);
								new LrcDataAccess(context).addLrc(lrc);//添加路径到数据库
							}
						}
					}
				}
			}
		}
	}
	
	//显示歌词控件
	private void showLrc(String musicName, String musicLrcPath) {
		if (musicLrcPath != null && !musicLrcPath.equals("")) {
			PlayListItem pli = new PlayListItem(musicName, music.getData(), 0L,
					true);
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
				lyricView.updateIndex(service.getMediaPlayer()
						.getCurrentPosition());
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
