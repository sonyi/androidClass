package com.mymusicplay.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache {
	private static ImageCache mInstance;

	private LruCache<String, Bitmap> mCache;

	private ImageCache() {
		long maxSize = ((Runtime.getRuntime().maxMemory() / 1024) / 8);
		mCache = new LruCache<String, Bitmap>((int) maxSize);
	}

	public synchronized static ImageCache getInstance() {
		if (mInstance == null) {
			mInstance = new ImageCache();
		}
		return mInstance;
	}

	public synchronized Bitmap put(String key, Bitmap value) {
		if (key != null && !"".equals(key)) {
			if (value != null) {
				return mCache.put(key, value);
			}
		}
		return null;
	}

	public synchronized boolean isCached(String key) {
		if (key != null && !"".equals(key)) {
			return mCache.get(key) != null ? true : false;
		}
		return false;
	}

	public synchronized Bitmap get(String key) {
		if (key != null && !"".equals(key)) {
			return mCache.get(key);
		}
		return null;
	}

	public synchronized Bitmap remove(String key) {
		return mCache.remove(key);
	}

}
