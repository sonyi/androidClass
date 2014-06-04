package com.activityandtask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements
		android.view.View.OnClickListener {
	private Button btnItems;
	private Button btnSingle;
	private Button btnMulti;
	private Button btnCustom;
	
	private Button btnOpenSecond;
	private Button btnOpenSelf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnItems = (Button) findViewById(R.id.btn_items_dialog);
		btnSingle = (Button) findViewById(R.id.btn_single_choice_dialog);
		btnMulti = (Button) findViewById(R.id.btn_multi_choice_dialog);
		btnCustom = (Button) findViewById(R.id.btn_custom_dialog);
		btnOpenSelf = (Button) findViewById(R.id.btn_open_self);
       
		btnItems.setOnClickListener(this);
		btnSingle.setOnClickListener(this);
		btnMulti.setOnClickListener(this);
		btnCustom.setOnClickListener(this);
		
		btnOpenSecond = (Button) findViewById(R.id.btn_open_second);
		btnOpenSecond.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,SecondActivity.class);
				startActivity(intent);
			}
		});
		
		btnOpenSelf.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,MainActivity.class);
				startActivity(intent);				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_items_dialog:
			showItemsDialog();
			break;
		case R.id.btn_single_choice_dialog:
			showSingleChoiceDialog();
			break;
		case R.id.btn_multi_choice_dialog:
			showMultiChoiceDialog();
			break;
		case R.id.btn_custom_dialog:
			showCustomDialog();
			break;
		}
	}

	// 显示列表项形式的对话框
	private void showItemsDialog() {
		final String[] typeOfGirls = getResources().getStringArray(
				R.array.type_of_girls);

		new AlertDialog.Builder(this).setTitle("请选择")
				.setItems(typeOfGirls, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String type = typeOfGirls[which];
						Toast.makeText(MainActivity.this, type,
								Toast.LENGTH_SHORT).show();
					}
				}).create().show();
	}

	// 显示单选形式的对话框
	private void showSingleChoiceDialog() {
		final String[] typeOfGirls = getResources().getStringArray(
				R.array.type_of_girls);
		new AlertDialog.Builder(this).setTitle("请选择")
				.setSingleChoiceItems(typeOfGirls, 1, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).setPositiveButton("确定", null).setNegativeButton("取消", null)
				.create().show();
	}

	// 显示多选形式的对话框
	private void showMultiChoiceDialog() {
		final String[] typeOfGirls = getResources().getStringArray(
				R.array.type_of_girls);
		new AlertDialog.Builder(this)
				.setTitle("请选择")
				.setMultiChoiceItems(typeOfGirls,
						new boolean[] { true, true, false },
						new OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								// TODO Auto-generated method stub

							}
						}).setPositiveButton("确定", null)
				.setNegativeButton("取消", null).create().show();
	}

	// 显示自定义布局的对话框
	private void showCustomDialog() {
		View view = getLayoutInflater().inflate(R.layout.dialog_content, null);
		new AlertDialog.Builder(this).setTitle("你好").setView(view)
				.setPositiveButton("确定", null).setNegativeButton("取消", null)
				.create().show();
	}

	// 点返回按钮时，提示是否要退出
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示").setMessage("不玩了吗？")
					.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 关闭对话框
							dialog.dismiss();
							// 关闭当前activity
							finish();
						}
					}).setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
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
