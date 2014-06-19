package com.mybookcollection.util;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * 存储设备读取辅助类
 * 
 * @author Li Bin
 */
public class StorageUtil {
	private static File APP_ROOT_DIR = Environment
			.getExternalStorageDirectory();

	/**
	 * 获取指定路径的图片，以一个Bitmap对象返回
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
	 * 判断当前外存储器是否可读
	 * 
	 * @return true表示可读
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
	 * 判断外存储器是否可写
	 * 
	 * @return true表示可写
	 */
	public static boolean isExternalStorageWriteable() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
}
