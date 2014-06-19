package com.mybookcollection.ui;

import com.mybookcollection.MyApplication;
import com.mybookcollection.R;
import com.mybookcollection.model.BookBrief;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BookDetailActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);
		// ����ʾLogo
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		// ��ʾ���ϵ�����С��ͷ
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// ����ʾ�������
		// getSupportActionBar().setDisplayShowTitleEnabled(false);

		MyApplication app = (MyApplication) getApplication();
		BookBrief book = (BookBrief) app.getArg("data");
		//...
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.book_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
