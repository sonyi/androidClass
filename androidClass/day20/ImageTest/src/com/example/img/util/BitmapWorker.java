package com.example.img.util;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapWorker {
	public void fetch(String path, ImageView imageView) {
		// BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		// task.execute(path);

		@SuppressWarnings("unchecked")
		final WeakReference<BitmapWorkerTask> oldWeakTask = (WeakReference<BitmapWorkerTask>) imageView
				.getTag();
		if (oldWeakTask != null) {
			final BitmapWorkerTask task = oldWeakTask.get();
			if (task != null) {
				task.cancel(true);
			}
			imageView.setImageBitmap(null);
		}
		BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		WeakReference<BitmapWorkerTask> newWeakTask = new WeakReference<BitmapWorkerTask>(
				task);
		imageView.setTag(newWeakTask);
		task.execute(path);
	}

	private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
		private WeakReference<ImageView> mWeakImageView;// 设置为弱引用
		private int mReqWith;
		private int mReqHeight;

		BitmapWorkerTask(ImageView imageView) {
			mWeakImageView = new WeakReference<ImageView>(imageView);
			mReqWith = DisplayUtil.px2Dip(imageView.getContext(),
					imageView.getLayoutParams().width);
			mReqHeight = DisplayUtil.px2Dip(imageView.getContext(),
					imageView.getLayoutParams().height);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			String path = params[0];

			Bitmap bm = null;
			ImageCache cache = ImageCache.getInstance();
			if (!cache.isCached(path)) {
				try {
					bm = getBitmapDecodeFile(path);
					cache.put(path, bm);
				} catch (OutOfMemoryError e) {
				}
			}
			return cache.get(path);
		}

		private Bitmap getBitmapDecodeFile(String path) throws OutOfMemoryError {
			// Bitmap bm = BitmapFactory.decodeFile(path);
			Options opts = new Options();
			// 先只解析边界，
			opts.inJustDecodeBounds = true;// 只解析图片边界(宽高等)，不解析图片数据内容
			BitmapFactory.decodeFile(path, opts);
			opts.inSampleSize = calculateInSampleSize(opts, mReqWith,
					mReqHeight);

			// 得到边界压缩之后的Bitmap对象
			opts.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeFile(path, opts);
			return bm;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			if (result == null || mWeakImageView == null) {
				return;
			}
			ImageView imageView = mWeakImageView.get();
			if (imageView != null) {// 弱引用，内存
				imageView.setImageBitmap(result);
			}

			super.onPostExecute(result);
		}
	}

	/**
	 * 计算图片inSampleSize缩放比
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(Options options, int reqWidth,
			int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

			final float totalPixels = width * height;
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;
			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}
}
