package com.example.dialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button itemButton, sigleButton, mulButton, showviewButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		itemButton = (Button) findViewById(R.id.btn_item);
		sigleButton = (Button) findViewById(R.id.btn_sigle_chose);
		mulButton = (Button) findViewById(R.id.btn_mul_chose);
		showviewButton = (Button) findViewById(R.id.btn_show_view);

	}

	public void itemOnclick(View view) {
		final String[] typeOfGirl = getResources().getStringArray(
				R.array.type_of_girl);
		new AlertDialog.Builder(this).setTitle("请选择类型")
				.setItems(typeOfGirl, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String type = typeOfGirl[which];
						Toast.makeText(MainActivity.this, type,
								Toast.LENGTH_SHORT).show();
					}
				}).create().show();
	}

	public void singleChoseOnclick(View view) {
		final String[] typeOfGirl = getResources().getStringArray(
				R.array.type_of_girl);
		new AlertDialog.Builder(this).setTitle("请选择类型")
				.setSingleChoiceItems(typeOfGirl, 0, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).create().show();
	}

	public void mulChoseOnclick(View view) {
		final String[] typeOfGirl = getResources().getStringArray(
				R.array.type_of_girl);
		new AlertDialog.Builder(this)
				.setTitle("请选择类型")
				.setMultiChoiceItems(typeOfGirl,
						new boolean[] { true, false, false },
						new OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								// TODO Auto-generated method stub

							}
						}).setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).create().show();
	}

	public void showViewOnclick(View view) {
		View dialogView = getLayoutInflater().inflate(
				R.layout.dialog_show_view, null);
		new AlertDialog.Builder(this).setTitle("请输入你喜欢的类型").setView(dialogView)
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).create().show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示信息").setMessage("不玩了吗？")
					.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							finish();
							System.exit(0);

						}
					}).setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					}).create().show();
			return true;

		}

		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
