package com.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���ò�����Դ�ķ�ʽ 
        setContentView(R.layout.activity_main);
        
        // ��setContentView()��������ؿؼ�ʵ��
        TextView tvInfo = (TextView) findViewById(R.id.tv_text);
        tvInfo.setText(R.string.text_example);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
