package com.mybookcollection.util;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * �洢�豸��ȡ������
 * 
 * @author Li Bin
 */
public class StorageUtil {
	private static File APP_ROOT_DIR = Environment
			.getExternalStorageDirectory();

	/**
	 * ��ȡָ��·����ͼƬ����һ��Bitmap���󷵻�
	 * 
	 * @param artPath
	 * @return
	 */
	public static Bitmap getBookArt(String artPath) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 4;
		String path = APP_ROOT_DIR.getAbsolutePath() + File.separator + artPath;
		Bitmap bm = BitmapFactory.decodeFile(path, opts);
		return bm;
	}

	/**
	 * �жϵ�ǰ��洢���Ƿ�ɶ�
	 * 
	 * @return true��ʾ�ɶ�
	 */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)
				|| state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * �ж���洢���Ƿ��д
	 * 
	 * @return true��ʾ��д
	 */
	public static boolean isExternalStorageWriteable() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
}
