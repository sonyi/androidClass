package com.example.imageworkerdemo;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 本地图片加载工具类,异步加载图片，以优化内存占用的方式
 * 
 * @author Li Bin
 */
public class BitmapWorker {
	private Context mContext;
	
	public BitmapWorker(Context context) {
		mContext = context;
	}

	@SuppressWarnings("unchecked")
	public void fetch(String path, ImageView imageView) {
		final WeakReference<BitmapWorkerTask> oldWeakTask = (WeakReference<BitmapWorkerTask>) imageView
				.getTag();
		// 通过ImageView对象的Tag属性判断当前ImageView是不是AdapterView中重用的子项视图的控件
		// Tag是一个加载图片的异步任务
		if (oldWeakTask != null) {
			// 先取到图片加载异步任务的实际对象
			final BitmapWorkerTask task = oldWeakTask.get();
			if (task != null) {
				// 如果task不为空，说明当前ImageView正在加载或已经加载了之前的某张旧图片
				// 终止异步任务执行
				task.cancel(true);
			}
			// 把ImageView当中的旧图片置空
			imageView.setImageBitmap(null);
		}

		// 创建新的加载图片的异步任务，加载新图片
		BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		WeakReference<BitmapWorkerTask> newWeakTask = new WeakReference<BitmapWorker.BitmapWorkerTask>(
				task);
		// 把新建的异步任务实例作为ImageView的Tag
		imageView.setTag(newWeakTask);
		task.execute(path);
	}

	private class BitmapWorkerTask extends AsyncTask<String, Void, Drawable> {
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
		protected Drawable doInBackground(String... params) {
			String path = params[0];
			Bitmap bm = null;

			ImageCache cache = ImageCache.getInstance();
			if (!cache.isCached(path)) {
				bm = decodeFile(path, mReqWidth, mReqHeight);
				Drawable drawalbe = new RecyclingBitmapDrawable(mContext.getResources(), bm);
				cache.put(path, drawalbe);
			}

			return cache.get(path);
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result == null || mWeakImageView == null) {
				return;
			}

			ImageView imageView = mWeakImageView.get();
			if (imageView != null) {
				imageView.setImageDrawable(result);
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
