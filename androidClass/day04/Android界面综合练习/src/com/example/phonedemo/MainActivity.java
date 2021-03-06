package com.example.phonedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Gallery;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity {
	@SuppressWarnings("deprecation")
	private Gallery mGallery;
	private AutoCompleteTextView mPhNumber;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGallery = (Gallery) findViewById(R.id.gallery);
		mPhNumber = (AutoCompleteTextView) findViewById(R.id.phone_number);
		spinner = (Spinner) findViewById(R.id.spinner);

		getPhonePic();
		getPhoneNumber();
		getAddress();

	}

	public void getAddress() {
		String[] address = new String[] { "厦门", "广州", "武汉", "青岛", "重庆", "丽江",
				"杭州", "上海" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, address);
		spinner.setAdapter(adapter);
	}

	public void getPhoneNumber() {
		String[] phoneNumbers = new String[] { "13656012580", "13954851326",
				"13015497325", "13818425975", "15025978136", "13642587161" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, phoneNumbers);
		mPhNumber.setAdapter(adapter);
	}

	public void getPhonePic() {
		String key = "imgres";
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> img1 = new HashMap<String, Object>();
		img1.put(key, R.drawable.img_01);
		data.add(img1);

		Map<String, Object> img2 = new HashMap<String, Object>();
		img2.put(key, R.drawable.img_02);
		data.add(img2);

		Map<String, Object> img3 = new HashMap<String, Object>();
		img3.put(key, R.drawable.img_03);
		data.add(img3);

		Map<String, Object> img4 = new HashMap<String, Object>();
		img4.put(key, R.drawable.img_04);
		data.add(img4);

		Map<String, Object> img5 = new HashMap<String, Object>();
		img5.put(key, R.drawable.img_05);
		data.add(img5);

		Map<String, Object> img6 = new HashMap<String, Object>();
		img6.put(key, R.drawable.img_06);
		data.add(img6);

		Map<String, Object> img7 = new HashMap<String, Object>();
		img7.put(key, R.drawable.img_07);
		data.add(img7);

		Map<String, Object> img8 = new HashMap<String, Object>();
		img8.put(key, R.drawable.img_08);
		data.add(img8);

		String[] from = new String[] { key };
		int[] to = new int[] { R.id.img_item };
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.view_pic_item, from, to);
		mGallery.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
