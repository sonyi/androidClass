package com.example.imageworkerdemo;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 本地图片加载工具类,异步加载图片，以优化内存占用的方式
 * 
 * @author Li Bin
 */
public class BitmapWorker {

	public void fetch(String path, ImageView imageView) {
		BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		task.execute(path);
	}

	private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
		private WeakReference<ImageView> mWeakImageView;
		private int mReqWidth;
		private int mReqHeight;

		BitmapWorkerTask(ImageView imageView) {
			mWeakImageView = new WeakReference<ImageView>(imageView);
			mReqWidth = DisplayUtil.px2Dip(imageView.getContext(),
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
				bm = decodeFile(path, mReqWidth, mReqHeight);
				cache.put(path, bm);
			}

			return cache.get(path);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result == null || mWeakImageView == null) {
				return;
			}

			ImageView imageView = mWeakImageView.get();
			if (imageView != null) {
				imageView.setImageBitmap(result);
			}

			super.onPostExecute(result);
		}
	}

	private Bitmap decodeFile(String path, int reqWidth, int reqHeight) {
		Options opts = new Options();
		// 先只解析图片的边界数据
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
		opts.inJustDecodeBounds = false;

		// 得到边界压缩之后的Bitmap对象
		Bitmap bm = BitmapFactory.decodeFile(path, opts);
		return bm;
	}

	/**
	 * 计算图片inSampleSize缩放比
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private int calculateInSampleSize(Options options, int reqWidth,
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
