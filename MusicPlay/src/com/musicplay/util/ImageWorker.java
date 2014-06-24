package com.musicplay.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageWorker {

	/**
	 * 获取本地图片，显示在指定的ImageView控件中
	 * 
	 * @param imageView
	 * @param imgPath
	 */
	public void fetch(ImageView imageView, String imgPath) {
		ImageWorkerTask task = new ImageWorkerTask(imageView);
		task.execute(imgPath);
	}

	private class ImageWorkerTask extends AsyncTask<String, Void, Bitmap> {
		private ImageView mImageView;

		public ImageWorkerTask(ImageView imageView) {
			mImageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			String path = params[0];
			Bitmap bm = BitmapFactory.decodeFile(path);
			return bm;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (mImageView != null && result != null) {
				mImageView.setImageBitmap(result);
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
