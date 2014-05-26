package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{
	private TextView tv_background;
	private Button bt_blue,bt_red;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_background = (TextView) findViewById(R.id.tv_background);
        bt_blue = (Button) findViewById(R.id.bt_blue);
        bt_red = (Button) findViewById(R.id.bt_red);
        bt_blue.setOnClickListener(this);
        bt_red.setOnClickListener(this);
        
    }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_search:
			Toast.makeText(this, "点击了搜索", Toast.LENGTH_SHORT).show();
			break;

		case R.id.action_refresh:
			Toast.makeText(this, "点击了刷新", Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_settings:
			Toast.makeText(this, "点击了设置", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}	
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_blue:
			tv_background.setBackgroundResource(R.color.blue);
			break;
		case R.id.bt_red:
			tv_background.setBackgroundResource(R.color.pink);
			break;
		default:
			break;
		}
	}
}
