package com.example.imageworkerdemo;

import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;

public class ImageCache {
	private static ImageCache mInstance;

	private LruCache<String, Drawable> mCache;

	private ImageCache() {
		long maxSize = ((Runtime.getRuntime().maxMemory() / 1024) / 8);
		mCache = new LruCache<String, Drawable>((int) maxSize) {
			protected void entryRemoved(boolean evicted, String key, Drawable oldValue, Drawable newValue) {
				if (oldValue instanceof RecyclingBitmapDrawable) {
					((RecyclingBitmapDrawable) oldValue).setIsCached(false);
				}
			};
		};
	}

	public synchronized static ImageCache getInstance() {
		if (mInstance == null) {
			mInstance = new ImageCache();
		}
		return mInstance;
	}

	public synchronized Drawable put(String key, Drawable value) {
		if (key != null && !"".equals(key)) {
			if (value != null) {
				if (value instanceof RecyclingBitmapDrawable) {
					((RecyclingBitmapDrawable) value).setIsCached(true);
				}
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

	public synchronized Drawable get(String key) {
		if (key != null && !"".equals(key)) {
			return mCache.get(key);
		}
		return null;
	}

	public synchronized Drawable remove(String key) {
		Drawable value = mCache.remove(key);
		if (value instanceof RecyclingBitmapDrawable) {
			((RecyclingBitmapDrawable) value).setIsCached(false);
		}
		return value;
	}

}
