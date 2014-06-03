package com.example.gridviewtest;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GridAdapter extends BaseAdapter{
	private Context context;
	private List<Feed> mDataSource;
	int count = 0;

	
	public GridAdapter(Context context, List<Feed> mDataSource) {
		super();
		this.context = context;
		this.mDataSource = mDataSource;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDataSource.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDataSource.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return mDataSource.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		// convertView是重用的子项视图对象，如果不为空，就不比执行inflate和findViewById等消耗操作
		View view = arg1;
		ViewHolder vh = null;

		if (view == null) {
			// convertView为空，inflate子项视图，加载控件实例
			view = LayoutInflater.from(context).inflate(R.layout.view_grid_item,
					null);
			vh = new ViewHolder();
			vh.imgAvater = (ImageView) view.findViewById(R.id.img_avater);
			vh.tvTitle = (TextView) view.findViewById(R.id.tv_title);
			vh.tvDescription = (TextView) view
					.findViewById(R.id.tv_description);
			vh.btnAction = (ImageButton) view.findViewById(R.id.btn_action);
			// 将ViewHolder对象与子项视图对象关联
			view.setTag(vh);

			Log.i("view", "创建了第" + (++count) + "个view");
		} else {
			vh = (ViewHolder) view.getTag();
		}

		Feed data = mDataSource.get(arg0);
		
		
//		String fileName = feed.getImgPath();
//		File dir = new File(Environment.getExternalStorageDirectory(),"Demo");
//		File file = new File(dir,fileName);
//		
//		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
//		vh.imgPic.setImageBitmap(bm);
		
		//从外存储中读取图片文件
		String fileName = data.getImgPath();
		File dir = new File(Environment.getExternalStorageDirectory(),"Demo");
		File file = new File(dir,fileName);
		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
		vh.imgAvater.setImageBitmap(bm);
		
		vh.tvTitle.setText(data.getTitle());
		vh.tvDescription.setText(data.getDescription());

		final int index = arg0;
		vh.btnAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(context,
						"这是单击了第" + (index + 1) + "行的按钮", Toast.LENGTH_SHORT)
						.show();
			}
		});

		return view;
	}

	class ViewHolder {
		ImageView imgAvater;
		TextView tvTitle;
		TextView tvDescription;
		ImageButton btnAction;
	}

}
