package com.storagedemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText etText;
	private Button btnWrite;
	private Button btnRead;
	
	private ImageView imgPic;
	
	public static final String ROOT_DIR = "Demo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etText = (EditText) findViewById(R.id.editText1);
		btnWrite = (Button) findViewById(R.id.btn_write);
		btnRead = (Button) findViewById(R.id.btn_read);
		imgPic = (ImageView) findViewById(R.id.imageView1);
		
		// ����洢���ж�ȡͼƬ
		File dir = new File(Environment.getExternalStorageDirectory(),ROOT_DIR);
		File file = new File(dir,"img_01.jpg");
		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
		imgPic.setImageBitmap(bm);

		btnWrite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				writeToStorage();
			}
		});

		btnRead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				readFromStorage();
			}
		});
	}

	private void writeToStorage() {
		String text = etText.getText().toString();

		// ��ȡ��ǰӦ�ó����ڴ洢�Ķ�ռĿ¼
		// File dir = getFilesDir();
		// File file = new File(dir, "info.txt");
		
		// ��ȡ��ǰӦ�ó�����洢��Ŀ¼
		File externalRootDir = Environment.getExternalStorageDirectory();
		File dir = new File(externalRootDir,ROOT_DIR);
		// ��֤Ŀ¼����
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File (dir,"info.txt");

		OutputStream os = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			// ����1.
			// os = openFileOutput("info.txt",MODE_PRIVATE);

			// ����2.
			os = new FileOutputStream(file);
			os.write(text.getBytes());
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void readFromStorage() {
		InputStream is = null;
		try {
			File dir = new File(Environment.getExternalStorageDirectory(),ROOT_DIR);
			
			File file = new File(dir, "info.txt");
			is = new FileInputStream(file);
			
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			
			String text = new String(buffer);
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
