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
	private Button btnRotate;
	private Button btnStopRotate;
	private Button btnTransfer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgRefresh = (ImageView) findViewById(R.id.imageView1);
		btnRotate = (Button) findViewById(R.id.btn_rotate);
		btnStopRotate = (Button) findViewById(R.id.btn_stop_rotate);
		btnTransfer = (Button) findViewById(R.id.btn_transfer);
		
		btnRotate.setOnClickListener(this);
		btnStopRotate.setOnClickListener(this);
		btnTransfer.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Animation rotate = AnimationUtils.loadAnimation(
				this, R.anim.anim_rotate);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_set);

		
		switch (v.getId()) {
		case R.id.btn_rotate:
			imgRefresh.startAnimation(rotate);
			break;
		case R.id.btn_stop_rotate:
			rotate.cancel();
			rotate.reset();
			break;
		case R.id.btn_transfer:
			imgRefresh.startAnimation(anim);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
