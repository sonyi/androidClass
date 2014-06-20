package com.example.imageworkerdemo;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache {
	private static ImageCache mInstance;
	private LruCache<String, SoftReference<Bitmap>> mCache;

	private ImageCache() {
		long maxSize = (Runtime.getRuntime().maxMemory() / 1024) / 8;
		mCache = new LruCache<String, SoftReference<Bitmap>>((int) maxSize);
	}

	public synchronized static ImageCache getInstance() {
		if (mInstance == null) {
			mInstance = new ImageCache();
		}
		return mInstance;
	}

	public synchronized boolean isCached(String key) {
		if (key != null && !"".equals(key)) {
			return mCache.get(key) != null ? true : false;
		}

		return false;
	}

	public synchronized SoftReference<Bitmap> put(String key, Bitmap value) {
		if (key != null && !"".equals(key)) {
			if (value != null) {
				SoftReference<Bitmap> srValue = new SoftReference<Bitmap>(value);
				return mCache.put(key, srValue);
			}
		}
		return null;
	}

	public synchronized Bitmap get(String key) {
		if (key != null && !"".equals(key)) {
			SoftReference<Bitmap> srBm = mCache.get(key);
			return srBm.get();
		}
		return null;
	}

	public synchronized SoftReference<Bitmap> remove(String key) {
		return mCache.remove(key);
	}
}
