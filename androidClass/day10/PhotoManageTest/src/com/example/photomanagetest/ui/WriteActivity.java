package com.example.photomanagetest.ui;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.photomanagetest.R;
import com.example.photomanagetest.data.DataContract.PhoteDataContract;
import com.example.photomanagetest.data.ImgManageDataAccess;
import com.example.photomanagetest.model.ImgInformation;

public class WriteActivity extends Activity {
	ImageView imgAdd;
	private String[] mDialogItems = new String[] { "拍照", "从相册中选择" };
	private static final int REQUEST_CODE_CAPTURE = 0X00;
	private static final int REQUEST_CODE_CHOOSE = 0X01;
	private File mAppDir = new File(Environment.getExternalStorageDirectory(),"ImageCapture");
	private String mFileName;
	private Button mSendBtn;
	private EditText mTitleEt;
	private int imgId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write);
		imgAdd = (ImageView) findViewById(R.id.iv_write_add_img);
		mSendBtn = (Button) findViewById(R.id.btn_write_send);
		mTitleEt = (EditText) findViewById(R.id.et_write_text);
		
		Intent intent = this.getIntent();
		imgId = intent.getIntExtra(PhoteDataContract.PHOTO_ID, 0);
		
		imgAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showChooseImgDialog();
			}
		});
		
		mSendBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendImgToHomeActivity();
			}
		});
	}

	
	private void showChooseImgDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this).setTitle("请选择图片")
				.setItems(mDialogItems, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case REQUEST_CODE_CAPTURE:
							captureByCamera();
							break;
						case REQUEST_CODE_CHOOSE:
							captureByGallery();
							break;
						}
					}
				}).create().show();
	}

	private void captureByCamera() {
		// TODO Auto-generated method stub
		if(!mAppDir.exists()){
			mAppDir.mkdirs();
		}
		String fileName = "img_" + System.currentTimeMillis() + ".jpg";
		File file = new File(mAppDir,fileName);
		mFileName = file.getAbsolutePath();
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Uri uri = Uri.fromFile(file);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 提供一个Extra键值对数据，告诉相机，要把拍到的完整尺寸照片存放到哪个路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, REQUEST_CODE_CAPTURE);
	}

	private void captureByGallery() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_CODE_CHOOSE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != RESULT_OK) {
			return;
		}
		if(requestCode == REQUEST_CODE_CHOOSE){
			mFileName = getImagePathByUri(data.getData());
		}
		BitmapFactory.Options ops = new BitmapFactory.Options();
		ops.inSampleSize = 10;
		Bitmap bm = BitmapFactory.decodeFile(mFileName,ops);
		imgAdd.setImageBitmap(bm);
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
	
	
	@SuppressLint("SimpleDateFormat")
	private void sendImgToHomeActivity() {
		// TODO Auto-generated method stub
		String title = mTitleEt.getText().toString();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date curDate = new Date(System.currentTimeMillis());
		String time = sdf.format(curDate);
		
		ImgInformation photo = new ImgInformation();
		photo.setId(imgId);
		photo.setImgTitle(title);
		photo.setImgResPath(mFileName);
		photo.setImgTime(time);
		ImgManageDataAccess photeData = new ImgManageDataAccess(this);
		photeData.insertPhoto(photo);
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
