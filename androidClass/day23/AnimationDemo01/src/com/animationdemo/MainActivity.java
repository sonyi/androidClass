package com.animationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {
	private ImageView imgRefresh;

	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgRefresh = (ImageView) findViewById(R.id.img_refresh);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		btn5 = (Button) findViewById(R.id.button5);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			rotate();
			break;
		case R.id.button2:
			stopRotate();
			break;
		case R.id.button3:
			transition();
			break;
		case R.id.button4:
			scale();
			break;
		case R.id.button5:
			alpha();
			break;
		}
	}

	private void rotate() {
		Animation rotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
		imgRefresh.startAnimation(rotate);
	}
	
	private void stopRotate() {
		Animation rotate = imgRefresh.getAnimation();
		if (rotate != null) {
			rotate.cancel();
			rotate.reset();
		}
	}
	
	private void transition() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_transition);
		imgRefresh.startAnimation(anim);
	}
	
	private void scale () {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
		imgRefresh.startAnimation(anim);
	}
	
	private void alpha() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
		imgRefresh.startAnimation(anim);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
