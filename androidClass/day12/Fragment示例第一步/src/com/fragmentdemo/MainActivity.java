package com.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity {
	private FrameLayout layoutFrgmtContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		layoutFrgmtContainer = (FrameLayout) findViewById(R.id.frgmt_container);
		
		if (layoutFrgmtContainer != null) {
			//
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
