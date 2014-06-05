package com.example.imagecapturetest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView imgPic;
	private String[] mDialogItems = new String[]{"拍照","从相册中选择"};
	private static final int REQUEST_CODE_CAPTURE = 0X00;
	private static final int REQUEST_CODE_CHOOSE = 0X01;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgPic = (ImageView) findViewById(R.id.img_pic);
		imgPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showChooseImageDialog();
			}
		});
	}

	private void showChooseImageDialog(){
		new AlertDialog.Builder(this).setTitle("选择图片").setItems(mDialogItems, new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(which == 0){
					captureByCamera();
				}else if(which == 1){
					captureFromGallery();
				}
			}
		}).create().show();
	}
	
	private void captureByCamera(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, REQUEST_CODE_CAPTURE);
		
	}
	
	private void captureFromGallery(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_CODE_CHOOSE);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode != RESULT_OK){
			return;
		}
		if(requestCode == REQUEST_CODE_CAPTURE){
			
		}else if(requestCode == REQUEST_CODE_CHOOSE){
			Uri imageUri = data.getData();
			String imagePath = getImagePathByUri(imageUri);
			Bitmap bm = BitmapFactory.decodeFile(imagePath);
			imgPic.setImageBitmap(bm);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	//根据图片uri，获取图片的真实物理路径
	private String getImagePathByUri(Uri uri){
		ContentResolver cr = getContentResolver();
		String[] projection = new String[]{MediaStore.Images.Media.DATA};
		Cursor c = cr.query(uri, projection, null, null, null);
		String path = "";
		if(c.moveToNext()){
			path = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
		}
		c.close();
		return path;
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
