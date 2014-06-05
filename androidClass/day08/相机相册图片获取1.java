package com.imagecapturedemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView imgPic;

	private String[] mDialogItems = new String[] { "����", "�����ѡ��" };

	private File mAppDir = new File(Environment.getExternalStorageDirectory(),
			"ImageCapture");
	private String mFileName;

	private static final int REQUEST_CODE_CAPTURE = 0x00;
	private static final int REQUEST_CODE_CHOOSE = 0x01;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imgPic = (ImageView) findViewById(R.id.img_pic);
		imgPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showChooseImageDialog();
			}
		});
	}

	private void showChooseImageDialog() {
		new AlertDialog.Builder(this).setTitle("ѡ��ͼƬ")
				.setItems(mDialogItems, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							captureByCamera();
						} else if (which == 1) {
							chooseFromGallery();
						}
					}
				}).create().show();
	}

	private void captureByCamera() {
		if (!mAppDir.exists()) {
			mAppDir.mkdirs();
		}
		String fileName = "img_" + System.currentTimeMillis() + ".jpg";

		File file = new File(mAppDir, fileName);
		mFileName = file.getAbsolutePath();
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Uri uri = Uri.fromFile(file);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// �ṩһ��Extra��ֵ�����ݣ����������Ҫ���ĵ��������ߴ���Ƭ��ŵ��ĸ�·��
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

		startActivityForResult(intent, REQUEST_CODE_CAPTURE);
	}

	private void chooseFromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_CODE_CHOOSE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		
		if (requestCode == REQUEST_CODE_CHOOSE) {
			mFileName = getImagePathByUri(data.getData());
		}
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 8;
		Bitmap bm = BitmapFactory.decodeFile(mFileName,opts);
		imgPic.setImageBitmap(bm);

		// if (requestCode == REQUEST_CODE_CAPTURE) {
		// 1.
		// if (data != null) {
		// Bitmap bm = data.getParcelableExtra("data");
		// imgPic.setImageBitmap(bm);
		// writeBitmapToStorage(bm);
		// }
		// } else if (requestCode == REQUEST_CODE_CHOOSE) {
		// Uri imageUri = data.getData();
		// mFileName = getImagePathByUri(imageUri);
		// Bitmap bm = BitmapFactory.decodeFile(imagePath);
		// imgPic.setImageBitmap(bm);
		// }

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void writeBitmapToStorage(Bitmap bm) {
		if (!mAppDir.exists()) {
			mAppDir.mkdirs();
		}
		String fileName = "img_" + System.currentTimeMillis() + ".jpg";
		File file = new File(mAppDir, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		OutputStream output = null;
		try {
			output = new FileOutputStream(file);
			bm.compress(CompressFormat.JPEG, 100, output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ����ͼƬ��uri����ȡ��ͼƬ����ʵ�Ĵ洢·��
	private String getImagePathByUri(Uri uri) {
		ContentResolver cr = getContentResolver();
		String[] projection = new String[] { MediaStore.Images.Media.DATA };
		Cursor c = cr.query(uri, projection, null, null, null);
		String path = "";
		if (c.moveToNext()) {
			path = c.getString(c
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
		}
		c.close();
		return path;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
