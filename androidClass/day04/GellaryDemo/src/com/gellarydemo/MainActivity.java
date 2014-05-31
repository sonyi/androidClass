package com.gellarydemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Gallery;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private Gallery gallery;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        gallery = (Gallery) findViewById(R.id.gallery);
        
        // 定义SimpleAdapter的数据源，是一个包含元素为Map的List
        List<Map<String,Integer>> data  = new ArrayList<Map<String,Integer>>();
        
        // 构造数据项
        String key = "imgRes";
        
        Map<String,Integer> img1 = new HashMap<String, Integer>();
        img1.put(key, R.drawable.img_01);
        data.add(img1);
        
        Map<String,Integer> img2 = new HashMap<String, Integer>();
        img2.put(key, R.drawable.img_02);
        data.add(img2);
        
        Map<String,Integer> img3 = new HashMap<String, Integer>();
        img3.put(key, R.drawable.img_03);
        data.add(img3);
        
        Map<String,Integer> img4 = new HashMap<String, Integer>();
        img4.put(key, R.drawable.img_04);
        data.add(img4);
        
        Map<String,Integer> img5 = new HashMap<String, Integer>();
        img5.put(key, R.drawable.img_05);
        data.add(img5);
        
        Map<String,Integer> img6 = new HashMap<String, Integer>();
        img6.put(key, R.drawable.img_06);
        data.add(img6);
        
        Map<String,Integer> img7 = new HashMap<String, Integer>();
        img7.put(key, R.drawable.img_07);
        data.add(img7);
        
        Map<String,Integer> img8 = new HashMap<String, Integer>();
        img8.put(key, R.drawable.img_08);
        data.add(img8);
        
        String[] from = new String[] {key};
        int[] to = new int[] {R.id.img_item};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.view_gallery_item, from, to);
        gallery.setAdapter(adapter);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
