package com.screendemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EditText etDemo = new EditText(this);
		// 在代码中设置控件尺寸时，用dp单位规定尺寸，使用前转换成px单位
		int dpWidth = DisplayUtil.dip2Px(this, 170);
		int dpHeight = DisplayUtil.dip2Px(this, 50);
		LayoutParams params = new LayoutParams(dpWidth, dpHeight);
		addContentView(etDemo, params);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
