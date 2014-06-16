package com.example.heimusic;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

public class MusicService extends Service
{
	private MediaPlayer mMp;
    private MusicBinder mMb;
	public class MusicBinder extends Binder
	{
		void play(String uri)
		{
			MusicService.this.play(uri);
		}
	}
	void play(String uri)
	{
		try
		{
			mMp.reset();
			mMp.setDataSource(uri);
			mMp.prepare();
			mMp.start();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	public MusicService()
	{
	}
	@Override
	public void onCreate()
	{
		super.onCreate();
		mMp = new MediaPlayer();
		mMb = new MusicBinder();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if(mMp != null)
		{
			mMp.release();
		}
	}
	@Override
	public IBinder onBind(Intent intent)
	{
		return mMb;
	}
}
