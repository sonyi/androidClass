package com.example.mybookstore.ui;

import com.example.mybookstore.R;
import com.example.mybookstore.data.DataContract;
import com.example.mybookstore.model.BooksBrief;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class DetailActivity extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Intent intent = this.getIntent();
		BooksBrief b = (BooksBrief) intent.getSerializableExtra(DataContract.ACTIVITY_INTENT);
		Log.i("book", b.toString());
		
	}

}
