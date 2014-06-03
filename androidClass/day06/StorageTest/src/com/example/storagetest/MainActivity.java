package com.example.storagetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText mEditText;
	private Button mInput, mOutput;
	final String ROOT_DIR = "StorageTest";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mEditText = (EditText) findViewById(R.id.et_text);
		mInput = (Button) findViewById(R.id.btn_input);
		mOutput = (Button) findViewById(R.id.btn_output);
		mInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				writeToStorage();
			}
		});

		mOutput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				readFromStorage();
			}
		});

	}

	private void writeToStorage() {
		
		//外存储
		String line = mEditText.getText().toString();
		File externalRootDir = Environment.getExternalStorageDirectory();
		File dir = new File(externalRootDir,ROOT_DIR);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		File file = new File(dir,"info.txt");
		Log.i("io", line);
		
		OutputStream os = null;
		
		//内存储
		//OutputStream os = null;
		// 方式一：
//		try {
//			os = openFileOutput("info.txt", MODE_PRIVATE);
//			os.write(line.getBytes());
//			os.flush();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				if (os != null) {
//					os.close();
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}

		// 方式二：
		// File dir = getFilesDir();
		// File file = new File(dir,"info.txt");
		//
		 try {
			 if(!file.exists()){
				 file.createNewFile();
			 }
		 	os = new FileOutputStream(file);
		 	os.write(line.getBytes());
			os.flush();
			
		 } catch (FileNotFoundException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 } catch (IOException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }finally{
			 try {
				 if(os != null)
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}

	public void readFromStorage() {
		
		//外存储读取
		InputStream is = null;
		try {
			//is = openFileInput("info.txt");
			File dir = new File(Environment.getExternalStorageDirectory(),ROOT_DIR);
			File file = new File(dir,"info.txt");
			is = new FileInputStream(file);
			byte[] buf = new byte[is.available()];
			is.read(buf);
			String text = new String(buf);
			Log.i("io", text);
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//内存储读取
		// 方式二：
		// File file = new File(getFilesDir(),"info.txt");
		// try {
		// is = new FileInputStream(file);
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
