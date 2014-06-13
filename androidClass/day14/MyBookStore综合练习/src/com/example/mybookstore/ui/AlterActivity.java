package com.example.mybookstore.ui;

import com.example.mybookstore.R;
import com.example.mybookstore.data.DataAccess;
import com.example.mybookstore.data.ImageWorker;
import com.example.mybookstore.model.Books;
import com.example.mybookstore.util.Literal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AlterActivity extends ActionBarActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_alter);
		Intent intent = getIntent();
		long bookID = intent.getLongExtra(Literal.ALTER_INTENT, 0);
		Books book = new DataAccess(this).queryBooks(bookID);
		
		//Log.i("id", bookID + "");
		
		EditText title = (EditText) findViewById(R.id.et_alter_title);
		EditText author = (EditText) findViewById(R.id.et_alter_author);
		EditText date = (EditText) findViewById(R.id.et_alter_date);
		EditText pages = (EditText) findViewById(R.id.et_alter_pages);
		EditText price = (EditText) findViewById(R.id.et_alter_price);
		EditText description = (EditText) findViewById(R.id.et_alter_description);
		
		title.setText(book.getBookTitle());
		author.setText(book.getBookAuthor());
		date.setText(book.getBookDate());
		pages.setText(book.getBookPages()+"");
		price.setText(book.getBookPrice());
		description.setText(book.getBookDescription());
	}
	
	
}
