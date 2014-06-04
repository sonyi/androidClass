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
				
				// �÷�����Ŀ��Activity��Ϊ�Ի������
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
    }
    
    // ��д�÷������նԻ����
    // ����1:�����룻����2������룻�������������Ի�������ݵ�Intent����
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
