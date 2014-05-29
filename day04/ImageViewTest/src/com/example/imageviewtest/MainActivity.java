package com.example.imageviewtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView imgpic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//imgpic = (ImageView) findViewById(R.id.img_pic);
		
		//方式一：
		//imgpic.setImageResource(R.drawable.img_03);
		
		//方式二：
//		Drawable drawable = getResources().getDrawable(R.drawable.img_03);
//		imgpic.setImageDrawable(drawable);
		
//		//方式三:
//		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_03);
//		imgpic.setImageBitmap(bm);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
