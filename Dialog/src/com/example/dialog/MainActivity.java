package com.example.dialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
    	if(keyCode == KeyEvent.KEYCODE_BACK){
//    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//    		builder.setTitle("提示信息").setMessage("不玩了吗？").setPositiveButton("确定", new OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					dialog.dismiss();
//					finish();
//					System.exit(0);
//					
//				}
//			}).setNegativeButton("取消", new OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					dialog.dismiss();
//				}
//			}).create().show();
//    		return true;
    		
    		final String[] typeOfGirl = getResources().getStringArray(R.array.type_of_girl);
    		new AlertDialog.Builder(this).setTitle("请选择类型").setItems(typeOfGirl, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String type = typeOfGirl[which];
					Toast.makeText(MainActivity.this, type, Toast.LENGTH_SHORT).show();
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
