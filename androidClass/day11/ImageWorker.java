package com.gridviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
}
