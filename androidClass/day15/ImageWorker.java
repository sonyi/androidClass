package com.example.mybookstore.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageWorker {
	/**
	 * 开启工作线程，获取图片
	 * 根据图片绝对路径获取图片资源，并填充到imgView中，图片做相应压缩
	 * @param imgView 图片填充控件
	 * @param path 图片绝对路径
	 * @param inSampleSize 图片压缩比
	 */
	public void fetch(ImageView imgView,String path,int inSampleSize){
		ImageWorkerTask task = new ImageWorkerTask(imgView,inSampleSize);
		task.execute(path);
	}
	
	private class ImageWorkerTask extends AsyncTask<String, Void, Bitmap>{
		private ImageView imgView;
		int inSampleSize;
		public ImageWorkerTask(ImageView imgView,int inSampleSize){
			this.imgView = imgView;
			this.inSampleSize = inSampleSize;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			String path = params[0];
			BitmapFactory.Options ops = new BitmapFactory.Options();// 压缩图片
			ops.inSampleSize = inSampleSize;
			Bitmap bm = BitmapFactory.decodeFile(path, ops);
			return bm;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			if(imgView != null && result != null){
				imgView.setImageBitmap(result);
			}
			super.onPostExecute(result);
		}
	}
}
