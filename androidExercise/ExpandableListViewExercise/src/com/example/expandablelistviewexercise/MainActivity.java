package com.example.expandablelistviewexercise;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ArrayList<GroupImgRes> mGroupImgRes;
	private ArrayList<ArrayList<ChildImgRes>> mChildImgRes;
	ExpandableListView expandListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		expandListView = (ExpandableListView) findViewById(R.id.expand_listview);
		expandListView.setAdapter(adapter);
	}
	
	ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
		int count = 1,index = 1;
		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
			// TODO Auto-generated method stub
			View view = arg2;
			ViewHolderGroup vh = null;
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.view_group_item, null);
				vh = new ViewHolderGroup();
				vh.gImg = (ImageView) view.findViewById(R.id.iv_group);
				vh.gTitle = (TextView) view.findViewById(R.id.tv_group);
				Log.i("view", "groundView------------------------" + (index++));
				view.setTag(vh);
			}else{
				vh = (ViewHolderGroup) view.getTag();
			}

			GroupImgRes data = mGroupImgRes.get(arg0);
			vh.gImg.setImageResource(data.getGroupPicResId());
			vh.gTitle.setText(data.getGroupPicTitle());
			return view;
		}
		
		class ViewHolderGroup{
			ImageView gImg;
			TextView gTitle;
		}
		
		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return mGroupImgRes.get(arg0).getGroupId();
		}
		
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return mGroupImgRes.size();
		}
		
		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			return mGroupImgRes.get(arg0);
		}
		
		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			return mChildImgRes.get(arg0).size();
		}
		
		
		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			// TODO Auto-generated method stub	
			View view = arg3;
			ViewHolderChild vh = null;
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.view_child_item, null);
				vh = new ViewHolderChild();
				vh.cImg = (ImageView) view.findViewById(R.id.iv_child_pic);
				vh.cTitle = (TextView) view.findViewById(R.id.tv_child_title);
				vh.cDescription = (TextView) view.findViewById(R.id.tv_child_description);
				vh.cShare = (ImageButton) view.findViewById(R.id.ib_child_share);
				view.setTag(vh);
				Log.i("view", "childView-----" + (count++));
			}else{
				vh = (ViewHolderChild) view.getTag();
			}
			
			ChildImgRes data = mChildImgRes.get(arg0).get(arg1);
			vh.cImg.setImageResource(data.getChildPicResId());
			vh.cTitle.setText(data.getPicTitle());
			vh.cDescription.setText(data.getPicDescription());
			
			final String msg = data.getPicTitle();
			vh.cShare.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this, "����" + msg, Toast.LENGTH_SHORT).show();
				}
			});
			return view;
		}
		
		class ViewHolderChild{
			ImageView cImg;
			TextView cTitle;
			TextView cDescription;
			ImageButton cShare;
		}
		
		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return mChildImgRes.get(arg0).get(arg1).getChildPicResId();
		}
		
		@Override
		public Object getChild(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return mChildImgRes.get(arg0).get(arg1);
		}
	};
	
	
	public void initData(){
		mGroupImgRes = new ArrayList<GroupImgRes>();
		mChildImgRes = new ArrayList<ArrayList<ChildImgRes>>();
		
		GroupImgRes gImg1 = new GroupImgRes(1, R.drawable.img_peri_01, "��ŮͼƬ");
		GroupImgRes gImg2 = new GroupImgRes(2, R.drawable.img_animal_01, "����ͼƬ");
		GroupImgRes gImg3 = new GroupImgRes(3, R.drawable.img_scenery_01, "�羰ͼƬ");
		mGroupImgRes.add(gImg1);
		mGroupImgRes.add(gImg2);
		mGroupImgRes.add(gImg3);
		
		ArrayList<ChildImgRes> peri = new ArrayList<ChildImgRes>();
		ChildImgRes cImgPeri1 = new ChildImgRes(1, R.drawable.img_peri_01, "��Ů1", "Ư����Ů������");
		ChildImgRes cImgPeri2 = new ChildImgRes(2, R.drawable.img_peri_02, "��Ů2", "Ư����Ů������");
		ChildImgRes cImgPeri3 = new ChildImgRes(3, R.drawable.img_peri_03, "��Ů3", "Ư����Ů������");
		ChildImgRes cImgPeri4 = new ChildImgRes(4, R.drawable.img_peri_04, "��Ů4", "Ư����Ů������");
		ChildImgRes cImgPeri5 = new ChildImgRes(5, R.drawable.img_peri_05, "��Ů5", "Ư����Ů������");
		peri.add(cImgPeri1);
		peri.add(cImgPeri2);
		peri.add(cImgPeri3);
		peri.add(cImgPeri4);
		peri.add(cImgPeri5);
		ArrayList<ChildImgRes> animal = new ArrayList<ChildImgRes>();
		ChildImgRes cImgAnimal1 = new ChildImgRes(1, R.drawable.img_animal_01, "����1", "�ɰ����������");
		ChildImgRes cImgAnimal2 = new ChildImgRes(2, R.drawable.img_animal_02, "����2", "�ɰ����������");
		ChildImgRes cImgAnimal3 = new ChildImgRes(3, R.drawable.img_animal_03, "����3", "�ɰ����������");
		ChildImgRes cImgAnimal4 = new ChildImgRes(4, R.drawable.img_animal_04, "����4", "�ɰ����������");
		ChildImgRes cImgAnimal5 = new ChildImgRes(5, R.drawable.img_animal_05, "����5", "�ɰ����������");
		animal.add(cImgAnimal1);
		animal.add(cImgAnimal2);
		animal.add(cImgAnimal3);
		animal.add(cImgAnimal4);
		animal.add(cImgAnimal5);
		ArrayList<ChildImgRes> scenery = new ArrayList<ChildImgRes>();
		ChildImgRes cImgScenery1 = new ChildImgRes(1, R.drawable.img_scenery_01, "�羰1", "�����羰������");
		ChildImgRes cImgScenery2 = new ChildImgRes(2, R.drawable.img_scenery_02, "�羰2", "�����羰������");
		ChildImgRes cImgScenery3 = new ChildImgRes(3, R.drawable.img_scenery_03, "�羰3", "�����羰������");
		ChildImgRes cImgScenery4 = new ChildImgRes(4, R.drawable.img_scenery_04, "�羰4", "�����羰������");
		ChildImgRes cImgScenery5 = new ChildImgRes(5, R.drawable.img_scenery_05, "�羰5", "�����羰������");
		ChildImgRes cImgScenery6 = new ChildImgRes(6, R.drawable.img_scenery_06, "�羰6", "�����羰������");
		scenery.add(cImgScenery1);
		scenery.add(cImgScenery2);
		scenery.add(cImgScenery3);
		scenery.add(cImgScenery4);
		scenery.add(cImgScenery5);
		scenery.add(cImgScenery6);
		mChildImgRes.add(peri);
		mChildImgRes.add(animal);
		mChildImgRes.add(scenery);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}