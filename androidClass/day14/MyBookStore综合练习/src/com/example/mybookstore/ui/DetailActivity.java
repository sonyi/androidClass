package com.example.mybookstore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.R;
import com.example.mybookstore.data.DataAccess;
import com.example.mybookstore.data.ImageWorker;
import com.example.mybookstore.model.Books;
import com.example.mybookstore.util.Literal;

public class DetailActivity extends ActionBarActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Books book = getBookData();
		setActionBar();
		initData(book);
	}
	
	//��ȡͼ��id���������ݿ��в�ѯͼ����ϸ��Ϣ
	private Books getBookData() {
		Intent intent = this.getIntent();
		long bookId = intent.getLongExtra(Literal.ACTIVITY_INTENT, 0);
		DataAccess dataAccess = new DataAccess(this);
		return dataAccess.queryBooks(bookId);
	}
	
	//����Actionbar
	private void setActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
	}

	//��ʼ�����ؼ�����Ϣ
	private void initData(Books book) {
		ImageView img = (ImageView) findViewById(R.id.iv_detail_pic);
		TextView title = (TextView) findViewById(R.id.tv_detail_title);
		TextView author = (TextView) findViewById(R.id.tv_detail_author);
		TextView date = (TextView) findViewById(R.id.tv_detail_date);
		TextView pages = (TextView) findViewById(R.id.tv_detail_pages);
		TextView price = (TextView) findViewById(R.id.tv_detail_price);
		TextView description = (TextView) findViewById(R.id.tv_detail_description);
		
		new ImageWorker().fetch(img, book.getBookArt(),0);
		title.setText(book.getBookTitle());
		author.setText(Literal.AUTHOR + book.getBookAuthor());
		date.setText(Literal.DATE + book.getBookDate());
		pages.setText(Literal.PAGES + book.getBookPages());
		price.setText(Literal.PRICE + book.getBookPrice());
		description.setText(Literal.DESCRIPTION + book.getBookDescription());
	}
}
