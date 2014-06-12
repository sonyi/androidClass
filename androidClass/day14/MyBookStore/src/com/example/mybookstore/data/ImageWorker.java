package com.example.mybookstore.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageWorker {
	public void fetch(ImageView imgView,String path){
		ImageWorkerTask task = new ImageWorkerTask(imgView);
		task.execute(path);
	}
	
	private class ImageWorkerTask extends AsyncTask<String, Void, Bitmap>{
		private ImageView imgView;
		public ImageWorkerTask(ImageView imgView){
			this.imgView = imgView;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			String path = params[0];
			BitmapFactory.Options ops = new BitmapFactory.Options();// Ñ¹ËõÍ¼Æ¬
			ops.inSampleSize = 5;
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
