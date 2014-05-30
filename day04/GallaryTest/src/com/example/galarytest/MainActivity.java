package com.example.galarytest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.anim;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private Gallery gallery;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		gallery = (Gallery) findViewById(R.id.galler);
		getPhonePic();
		
	}

	
	public void getPhonePic(){
		String key = "imgres";
		String name = "imgName";
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		Map<String, Object> img1 = new HashMap<String, Object>();
		img1.put(key, R.drawable.img_01);
		img1.put(name, R.string.view_gallery_pic1);
		data.add(img1);
		
		Map<String, Object> img2 = new HashMap<String, Object>();
		img2.put(key, R.drawable.img_02);
		img2.put(name, R.string.view_gallery_pic2);
		data.add(img2);
		
		Map<String, Object> img3 = new HashMap<String, Object>();
		img3.put(key, R.drawable.img_03);
		img3.put(name, R.string.view_gallery_pic3);
		data.add(img3);
		
		Map<String, Object> img4 = new HashMap<String, Object>();
		img4.put(key, R.drawable.img_04);
		img4.put(name, R.string.view_gallery_pic4);
		data.add(img4);
		
		Map<String, Object> img5 = new HashMap<String, Object>();
		img5.put(key, R.drawable.img_05);
		img5.put(name, R.string.view_gallery_pic5);
		data.add(img5);
		
		Map<String, Object> img6 = new HashMap<String, Object>();
		img6.put(key, R.drawable.img_06);
		img6.put(name, R.string.view_gallery_pic6);
		data.add(img6);
		
		Map<String, Object> img7 = new HashMap<String, Object>();
		img7.put(key, R.drawable.img_07);
		img7.put(name, R.string.view_gallery_pic7);
		data.add(img7);
		
		Map<String, Object> img8 = new HashMap<String, Object>();
		img8.put(key, R.drawable.img_08);
		img8.put(name, R.string.view_gallery_pic8);
		data.add(img8);
		
		String[] from = new String[]{key,name};
		int[] to = new int[]{R.id.img_item,R.id.img_name};
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.view_gallery_item, from, to);
		
		gallery.setAdapter(adapter);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
