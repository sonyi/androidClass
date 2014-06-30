package com.example.dialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_layout);

		dialog.setTitle("Custom Dialog");

		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);

		lp.x = 10; // 新位置X坐标
		lp.y = 300; // 新位置Y坐标
//		lp.width = 300; // 宽度
//		lp.height = 700; // 高度
		lp.alpha = 0.7f; // 透明度
		dialogWindow.setAttributes(lp);
		
      WindowManager m = getWindowManager();
      Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
      WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
      p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
      p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
      dialogWindow.setAttributes(p);

		dialog.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
