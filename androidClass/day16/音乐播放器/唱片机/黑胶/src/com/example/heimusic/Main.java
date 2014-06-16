package com.example.heimusic;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.heimusic.R;
import com.example.heimusic.MusicService.MusicBinder;

public class Main extends Activity
{
	private SongMenuView songview;
	private ImageView tou;
	private ImageView pian;
	private RotateAnimation poweronAnimation, poweroffAnimation;
	private ImageView power;
	protected boolean isPowerOn;
	private MusicBinder mMB;
	private boolean isPrepare;
	ServiceConnection conn = new ServiceConnection()
	{
		@Override
		public void onServiceDisconnected(ComponentName name)
		{
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			mMB = (MusicBinder) service;
		}
	};
	private Button btn_play;
	private List<Song> list;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		songview = (SongMenuView) findViewById(R.id.songMenuView);
		list = loadsonglist();
		songview.setSongList(list);
		VolumnView volumnview = (VolumnView) findViewById(R.id.volumnview);
		tou = (ImageView) findViewById(R.id.iv_tou);
		pian = (ImageView) findViewById(R.id.iv_pian);
		power = (ImageView) findViewById(R.id.iv_power);
		// 音量进度条
		ProgressBar progressbar = (ProgressBar) findViewById(R.id.progressBar1);
		volumnview.setProgressBar(progressbar);
		poweronAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
				Main.this, R.anim.tou_on);
		poweroffAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
				Main.this, R.anim.tou_off);
		// 动画 播放的时候 ，一些设置
		AnimationListener animationListener = new AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{
				isPrepare = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation)
			{
			}
			
			@Override
			public void onAnimationEnd(Animation animation)
			{
				isPrepare = false;
			}
		};
		poweronAnimation.setAnimationListener(animationListener);
		poweroffAnimation.setAnimationListener(animationListener);
		btn_play = (Button) findViewById(R.id.button_play);
		btn_play.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				String name = songview.getCurrentItem();
				String uri = geturi(name);
				if (name != null & mMB != null)
				{
					mMB.play(uri);
					Toast.makeText(Main.this, "下面,请欣赏神曲：" + name, 1000).show();
					
					
				}
				else
					Toast.makeText(Main.this, "给唱片机整点电源！", 1000).show();
			}

		});
		// 电源键的事件处理
		power.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!isPowerOn)
				{
					if (!isPrepare)
					{
						Intent intent = new Intent(Main.this,
								MusicService.class);
//						startService(intent);
						bindService(intent, conn, BIND_AUTO_CREATE);
						isPowerOn = true;
						power.setImageResource(R.drawable.on);
						tou.setVisibility(View.VISIBLE);
						tou.startAnimation(poweronAnimation);
						Toast.makeText(Main.this, "开启唱片机", 1000).show();
					}
					else
					{
						Toast.makeText(Main.this, "风清云淡，稍安勿躁！", 1000).show();
					}
				}
				else
				{
					if (!isPrepare)
					{
						isPowerOn = false;
						unbindService(conn);
						power.setImageResource(R.drawable.off);
						tou.startAnimation(poweroffAnimation);
						tou.postDelayed(new Runnable()
						{
							public void run()
							{
								tou.setVisibility(View.INVISIBLE);
							}
						}, 1000);
						Toast.makeText(Main.this, "关闭唱片机", 1000).show();
					}
					else
					{
						Toast.makeText(Main.this, "风清云淡，稍安勿躁！", 1000).show();
					}
				}
			}
		});
	}
	
	private String geturi(String name)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if(name.equals(list.get(i).songname))
			{
				return list.get(i).uri;
			}
		}
		return list.get(0).uri;
	}
	
	private List<Song> loadsonglist()
	{
		List<Song> list = new ArrayList<Song>();
		Bundle bundle = getIntent().getExtras();
		list = (List<Song>) bundle.getSerializable("list");
		return list;
	}
}
