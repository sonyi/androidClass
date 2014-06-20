package com.example.imageworkerdemo;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * ����ͼƬ���ع�����,�첽����ͼƬ�����Ż��ڴ�ռ�õķ�ʽ
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
			mReqWidth = DisplayUtil.px2Dip(imageView.getContext(), imageView.getWidth()) ;
		    mReqHeight = DisplayUtil.px2Dip(imageView.getContext(), imageView.getHeight());
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			String path = params[0];
			
			Options opts = new Options();
			// ��ֻ����ͼƬ�ı߽�����
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, opts);
			opts.inSampleSize = calculateInSampleSize(opts, mReqWidth, mReqHeight);
			opts.inJustDecodeBounds = false;
			
			// �õ��߽�ѹ��֮���Bitmap����
			Bitmap bm = BitmapFactory.decodeFile(path, opts);
			
			return bm;
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

	/**
	 * ����ͼƬinSampleSize���ű�
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
