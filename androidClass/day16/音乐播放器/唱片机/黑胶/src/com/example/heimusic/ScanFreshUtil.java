package com.example.heimusic;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

   // 手动刷新歌曲列表
public class ScanFreshUtil
{
	private Context context;
	
	public ScanFreshUtil(Context contex)
	{
		this.context = context;
	}
	private  void scanSdCard()
	{
		IntentFilter intentfilter = new IntentFilter(
				Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentfilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		intentfilter.addDataScheme("file");
		ScanSdReceiver scanSdReceiver = new ScanSdReceiver();
		context.registerReceiver(scanSdReceiver, intentfilter);
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
				.parse("file://"
						+ Environment.getExternalStorageDirectory()
								.getAbsolutePath())));
	}
	
	class ScanSdReceiver extends BroadcastReceiver
	{
		private AlertDialog.Builder builder = null;
		private AlertDialog ad = null;
		private int count1;
		private int count2;
		private int count;
		
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action))
			{
				Cursor c1 = context.getContentResolver().query(
						MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
						null, null, null);
				count1 = c1.getCount();
				builder = new AlertDialog.Builder(context);
				builder.setMessage("正在扫描存储卡...");
				ad = builder.create();
				ad.show();
			}
			else
				if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action))
				{
					Cursor c2 = context.getContentResolver().query(
							MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
							null, null, null);
					count2 = c2.getCount();
					count = count2 - count1;
					ad.cancel();
					if (count >= 0)
					{
						Toast.makeText(context, "共增加" + count + "个多媒体文件",
								Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(context, "共减少" + count + "个多媒体文件",
								Toast.LENGTH_LONG).show();
					}
				}
		}
	}
}
