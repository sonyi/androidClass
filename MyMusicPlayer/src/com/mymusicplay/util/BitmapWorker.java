package com.mymusicplay.util;

import java.lang.ref.WeakReference;

import com.mymusicplay.R;
import com.mymusicplay.data.AlbumDataAccess;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * ����ͼƬ���ع�����,�첽����ͼƬ�����Ż��ڴ�ռ�õķ�ʽ
 * 
 * @author Li Bin
 */
public class BitmapWorker {
	private Context mContext;

	public BitmapWorker(Context context) {
		mContext = context;
	}

	/**
	 * ����ͼƬ�ı��ش洢·������ͼƬ��ΪBitmap������ص��ڴ棬�����ֵ�ָ����imageView�ؼ�
	 * 
	 * @param path
	 * @param imageView
	 */
	public void fetch(String path, ImageView imageView) {
		// ������ListView���ÿؼ�ʱ����ֹ֮ǰ��ͼƬ�ļ�������Ȼ���ټ����µ�ͼƬ
		if (cancelPotentialWork(path, imageView)) {
			BitmapWorkerTask task = new BitmapWorkerTask(imageView);
			AsyncDrawable asyncDrawable = new AsyncDrawable(
					mContext.getResources(), null, task);
			imageView.setImageDrawable(asyncDrawable);
			task.execute(path);
		}
	}
	
	public void fetch(long albumID, ImageView imageView){
		String path = new AlbumDataAccess(mContext).getAlbumArtByAlbumId(albumID);
		fetch(path, imageView);
	}

	private boolean cancelPotentialWork(String path, ImageView imageView) {
		BitmapWorkerTask worker = getWorkerTask(imageView);
		if (worker != null && worker.mImageUrl != null) {
			if ((!"".equals(path) && path != null)
					&& (!worker.mImageUrl.equals(path))) {
				worker.cancel(true);
				imageView.setImageBitmap(null);
			} else {
				return false;
			}
		}
		return true;
	}

	private BitmapWorkerTask getWorkerTask(ImageView imageView) {
		if (imageView != null) {
			Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				AsyncDrawable ad = (AsyncDrawable) drawable;
				return ad.getBitmapWorkerTask();
			}
		}
		return null;
	}

	public static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<BitmapWorkerTask> mBitmapWorkerTask;

		public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask task) {
			super(res, bitmap);
			mBitmapWorkerTask = new WeakReference<BitmapWorker.BitmapWorkerTask>(
					task);
		}

		public BitmapWorkerTask getBitmapWorkerTask() {
			if (mBitmapWorkerTask != null) {
				return mBitmapWorkerTask.get();
			}
			return null;
		}
	}

	private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
		private WeakReference<ImageView> mWeakImageView;
		private String mImageUrl;
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
			mImageUrl = params[0];
			Bitmap bm = null;

			ImageCache cache = ImageCache.getInstance();
			if (!cache.isCached(mImageUrl)) {
				bm = decodeFile(mImageUrl, mReqWidth, mReqHeight);
				cache.put(mImageUrl, bm);
			}

			return cache.get(mImageUrl);
		}

		@Override
		protected void onPostExecute(Bitmap result) {

			if ((mWeakImageView == null)) {
				return;
			}
			ImageView imgView = mWeakImageView.get();
			if (imgView != null) {
				if (result != null) {
					imgView.setImageBitmap(result);
				} else {
					imgView.setImageResource(R.drawable.ic_default_art);
				}
			}
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