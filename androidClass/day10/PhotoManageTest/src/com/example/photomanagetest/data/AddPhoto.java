package com.example.photomanagetest.data;

import java.io.File;

import com.example.photomanagetest.model.PhotoInformation;

import android.content.Context;
import android.os.Environment;

public class AddPhoto {
	private static final String dirName = "HomeActivity";
	private Context context;
	public AddPhoto(Context context){
		this.context = context;
		init();
	}
	
	private void init(){
		PhotoInformation img1 = new PhotoInformation(1, "ÃÀÅ®Í¼Æ¬", fileAbsolutePath("img_peri_01.jpg", dirName), "13:28");
		PhotoInformation img2 = new PhotoInformation(2, "¶¯ÎïÍ¼Æ¬", fileAbsolutePath("img_animal_01.jpg", dirName), "15:22");
		PhotoInformation img3 = new PhotoInformation(3, "·ç¾°Í¼Æ¬", fileAbsolutePath("img_scenery_01.jpg", dirName), "17:58");
		PhotoInformation img4 = new PhotoInformation(4, "¶¯ÂþÍ¼Æ¬", fileAbsolutePath("img_animation_01.jpg", dirName), "3:31");
		PhotoInformation img5 = new PhotoInformation(5, "ÉãÓ°Í¼Æ¬", fileAbsolutePath("img_photo_01.jpg", dirName), "7:31");
		PhotoManageDataAccess photeData = new PhotoManageDataAccess(context);
		photeData.insertPhoto(img1);
		photeData.insertPhoto(img2);
		photeData.insertPhoto(img3);
		photeData.insertPhoto(img4);
		photeData.insertPhoto(img5);

	}
	
	
	private String fileAbsolutePath(String fileName, String dirName){
		File dir = new File(Environment.getExternalStorageDirectory(), dirName);
		File file = new File(dir, fileName);
		return file.getAbsolutePath();
	}
}
