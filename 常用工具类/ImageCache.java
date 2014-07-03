package com.example.img.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * ¿ªÆôÄÚ´æ»º´æ
 * @author Administrator
 *
 */
public class ImageCache {
	private LruCache<String, Bitmap> mCache;
	private static ImageCache mInstance;

	private ImageCache() {
		long maxSize = (Runtime.getRuntime().maxMemory() / 1024) / 4;
		Log.i("maxSixe", "maxSize-------" + maxSize);
		mCache = new LruCache<String, Bitmap>((int) maxSize);
	}

	public static ImageCache getInstance() {
		if (mInstance == null) {
			synchronized(ImageCache.class){
				if(mInstance == null){
					mInstance = new ImageCache();
				}
			}
		}
		return mInstance;
	}

	public synchronized boolean isCached(String key){
		if(key != null && key.equals("")){
			return mCache.get(key) != null ? true:false;
		}
		return false;
	}
	
	public synchronized Bitmap put(String key, Bitmap value) {
		if (key != null && !key.equals("")) {
			if (value != null) {
				return mCache.put(key, value);
			}
		}
		return null;
	}

	public synchronized Bitmap get(String key) {
		if(key != null && !key.equals("")){
			return mCache.get(key);
		}
		return null;
	}
	
	public synchronized Bitmap remove(String key){
		return mCache.remove(key);
	}
}
