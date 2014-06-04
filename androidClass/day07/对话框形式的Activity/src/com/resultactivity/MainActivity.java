package com.resultactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btnOpen;
	
	private static final int REQUEST_CODE = 0x00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnOpen = (Button) findViewById(R.id.btn_open_second);
        
        btnOpen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,SecondActivity.class);
				
				// 该方法将目标Activity作为对话结果打开
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
    }
    
    // 重写该方法接收对话结果
    // 参数1:请求码；参数2：结果码；参数三：包含对话结果数据的Intent对象
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode != RESULT_OK) {
    	    return;
    	}
    	if (requestCode == REQUEST_CODE) {
    		String info = data.getStringExtra("info");
    		Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
