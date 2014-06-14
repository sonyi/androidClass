package com.example.mybookstore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.mybookstore.MyApplication;
import com.example.mybookstore.R;
import com.example.mybookstore.data.DataAccess;
import com.example.mybookstore.model.Books;
import com.example.mybookstore.util.Literal;

public class AlterActivity extends ActionBarActivity{
	EditText title;
	EditText author;
	EditText date;
	EditText pages;
	EditText price;
	EditText description;
	Button btnOk;
	Button btnQuit;
	long bookID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_alter);
		init();
		btnOk = (Button) findViewById(R.id.btn_alter_ok);
		btnQuit = (Button) findViewById(R.id.btn_alter_quit);
		btnOk.setOnClickListener(btnOnClickListener);
		btnQuit.setOnClickListener(btnOnClickListener);
	}

	//设置Actionbar，初始化各个控件的数据
	private void init() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Intent intent = getIntent();
		bookID = intent.getLongExtra(Literal.ALTER_INTENT, 0);
		Books book = new DataAccess(this).queryBooks(bookID);
		
		title = (EditText) findViewById(R.id.et_alter_title);
		author = (EditText) findViewById(R.id.et_alter_author);
		date = (EditText) findViewById(R.id.et_alter_date);
		pages = (EditText) findViewById(R.id.et_alter_pages);
		price = (EditText) findViewById(R.id.et_alter_price);
		description = (EditText) findViewById(R.id.et_alter_description);
		
		title.setText(book.getBookTitle());
		author.setText(book.getBookAuthor());
		date.setText(book.getBookDate());
		pages.setText(book.getBookPages()+"");
		price.setText(book.getBookPrice());
		description.setText(book.getBookDescription());
	}
	
	//设置按钮监听器
	private OnClickListener btnOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(arg0.getId() == R.id.btn_alter_ok){
				Books book = new Books();
				book.setBook_id(bookID);
				book.setBookTitle(title.getText().toString());
				book.setBookAuthor(author.getText().toString());
				book.setBookDate(date.getText().toString());
				book.setBookPages(Long.parseLong(pages.getText().toString()));
				book.setBookPrice(price.getText().toString());
				book.setBookDescription(description.getText().toString());
				
				//更新数据库信息
				int result = new DataAccess(AlterActivity.this).updateBooks(book.getBook_id(), book);
						
				MyApplication app = (MyApplication) getApplication();
				app.addArg(Literal.BOOKS_VALUE,book);

				Intent intent = new Intent();
				intent.putExtra(Literal.ALTER_INTENT_BACK, result);
				setResult(RESULT_OK, intent);
				finish();
			}
			if(arg0.getId() == R.id.btn_alter_quit){
				setResult(RESULT_CANCELED);
				finish();
			}
		}
	};
}
