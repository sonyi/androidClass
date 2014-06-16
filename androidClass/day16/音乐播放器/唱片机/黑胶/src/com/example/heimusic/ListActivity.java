package com.example.heimusic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity implements OnClickListener
{
	private ArrayList<Song> list;
	private ListView listview;
	private Cursor cursor;
	private PopupWindow popu;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		parent = LayoutInflater.from(this).inflate(R.layout.activity_list,
				null);
		setContentView(parent);
		listview = (ListView) findViewById(R.id.listView_song);
		findViewById(R.id.button_ok).setOnClickListener(this);
		findViewById(R.id.button_all).setOnClickListener(this);
		findViewById(R.id.button_cancel).setOnClickListener(this);
		findViewById(R.id.button_ramdon).setOnClickListener(this);
		getlist();
		iniPopuMenu();
	}
	
	private void iniPopuMenu()
	{
		View contentView = LayoutInflater.from(this).inflate(R.layout.menu,
				null);
		popu = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}
	
 
	
	  @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event)
	    {
	        if (keyCode == KeyEvent.KEYCODE_BACK)
	        { 
	            if (popu.isShowing())
	            {
	            	popu.dismiss();
	                return true;
	            }
	        }
	        else if (keyCode == KeyEvent.KEYCODE_MENU)
	        {
	            if (popu.isShowing())
	            {
	            	popu.dismiss();
	            } else
	            {
	            	popu.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	            }
	        }
	            
	        return super.onKeyDown(keyCode, event);
	    }
	
	private void getlist()
	{
		cursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		list = new ArrayList<Song>();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{
			String songname = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE));
			String author = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ARTIST));
			String uri = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
			long size = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.SIZE));
			Song s = new Song(uri, songname, author);
			if (size > 2091008)
				list.add(s);
		}
		listview.setAdapter(adapter);
	}
	
	BaseAdapter adapter = new BaseAdapter()
	{
		public View getView(final int position, View convertView,
				ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = (RelativeLayout) getLayoutInflater().inflate(
						R.layout.item, null);
			}
			TextView position1 = (TextView) convertView
					.findViewById(R.id.textView_position);
			TextView songname = (TextView) convertView
					.findViewById(R.id.textView_songname);
			TextView author = (TextView) convertView
					.findViewById(R.id.textView_author);
			CheckBox checkbox = (CheckBox) convertView
					.findViewById(R.id.checkBox);
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked)
				{
					if (isChecked)
					{
						list.get(position).isCheck = true;
					}
					else
					{
						list.get(position).isCheck = false;
					}
				}
			});
			position1.setText((position + 1) + "");
			songname.setText(list.get(position).songname);
			author.setText(list.get(position).author);
			checkbox.setChecked(list.get(position).isCheck);
			return convertView;
		}
		
		public long getItemId(int position)
		{
			return 0;
		}
		
		public Object getItem(int position)
		{
			return null;
		}
		
		public int getCount()
		{
			return list.size();
		}
	};
	private View parent;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		 
		return false;
	}
	
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.button_all:
			addall();
			break;
		case R.id.button_cancel:
			cancel();
			break;
		case R.id.button_ok:
			go2play();
			break;
		case R.id.button_ramdon:
			ramdon();
			break;
		}
	}
	
	private void ramdon()
	{
		cancel();
		ArrayList<Integer> numlist = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++)
		{
			numlist.add(i);
		}
		Collections.shuffle(numlist);
		if (list.size() > 20)
		{
			for (int i = 0; i < 20; i++)
			{
				list.get(numlist.get(i)).isCheck = true;
			}
			adapter.notifyDataSetChanged();
		}
		else
		{
			addall();
		}
	}
	
	private void cancel()
	{
		for (int i = 0; i < list.size(); i++)
		{
			list.get(i).isCheck = false;
		}
		adapter.notifyDataSetChanged();
	}
	
	private void addall()
	{
		for (int i = 0; i < list.size(); i++)
		{
			list.get(i).isCheck = true;
		}
		adapter.notifyDataSetChanged();
	}
	
	private void go2play()
	{
		ArrayList<Song> newlist = new ArrayList<Song>();
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).isCheck)
			{
				newlist.add(list.get(i));
			}
		}
		Intent intent = new Intent(this, Main.class);
		Bundle b = new Bundle();
		b.putSerializable("list", newlist);
		intent.putExtras(b);
		startActivity(intent);
	}
}
