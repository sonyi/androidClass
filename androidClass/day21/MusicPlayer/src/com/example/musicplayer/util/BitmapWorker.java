package com.example.musicplayer.util;

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

	@SuppressWarnings("unchecked")
	public void fetch(String path, ImageView imageView) {
		final WeakReference<BitmapWorkerTask> oldWeakTask = (WeakReference<BitmapWorkerTask>) imageView
				.getTag();
		// ͨ��ImageView�����Tag�����жϵ�ǰImageView�ǲ���AdapterView�����õ�������ͼ�Ŀؼ�
		// Tag��һ������ͼƬ���첽����
		if (oldWeakTask != null) {
			// ��ȡ��ͼƬ�����첽�����ʵ�ʶ���
			final BitmapWorkerTask task = oldWeakTask.get();
			if (task != null) {
				// ���task��Ϊ�գ�˵����ǰImageView���ڼ��ػ��Ѿ�������֮ǰ��ĳ�ž�ͼƬ
				// ��ֹ�첽����ִ��
				task.cancel(true);
			}
			// ��ImageView���еľ�ͼƬ�ÿ�
			imageView.setImageBitmap(null);
		}

		// �����µļ���ͼƬ���첽���񣬼�����ͼƬ
		BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		WeakReference<BitmapWorkerTask> newWeakTask = new WeakReference<BitmapWorker.BitmapWorkerTask>(
				task);
		// ���½����첽����ʵ����ΪImageView��Tag
		imageView.setTag(newWeakTask);
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
			if (mWeakImageView == null) {
				return;
			}

			ImageView imageView = mWeakImageView.get();
			if (imageView != null) {
				if (result != null) {
					imageView.setImageBitmap(result);
				} else {
					// ����Ĭ�Ϸ���
				}
			}

			super.onPostExecute(result);
		}
	}

	private Bitmap decodeFile(String path, int reqWidth, int reqHeight) {
		Options opts = new Options();
		// ��ֻ����ͼƬ�ı߽�����
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
		opts.inJustDecodeBounds = false;

		// �õ��߽�ѹ��֮���Bitmap����
		Bitmap bm = BitmapFactory.decodeFile(path, opts);
		return bm;
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