package com.example.photomanagetest.data;

import java.io.File;

import com.example.photomanagetest.model.ImgInformation;

import android.content.Context;
import android.os.Environment;

public class AddImage {
	private static final String dirName = "HomeActivity";
	private Context context;
	public AddImage(Context context){
		this.context = context;
		init();
	}
	
	private void init(){
		ImgInformation img1 = new ImgInformation(1, "ÃÀÅ®Í¼Æ¬", fileAbsolutePath("img_peri_01.jpg", dirName), "13:28");
		ImgInformation img2 = new ImgInformation(2, "¶¯ÎïÍ¼Æ¬", fileAbsolutePath("img_animal_01.jpg", dirName), "15:22");
		ImgInformation img3 = new ImgInformation(3, "·ç¾°Í¼Æ¬", fileAbsolutePath("img_scenery_01.jpg", dirName), "17:58");
		ImgInformation img4 = new ImgInformation(4, "¶¯ÂþÍ¼Æ¬", fileAbsolutePath("img_animation_01.jpg", dirName), "3:31");
		ImgInformation img5 = new ImgInformation(5, "ÉãÓ°Í¼Æ¬", fileAbsolutePath("img_photo_01.jpg", dirName), "7:31");
		ImgManageDataAccess photeData = new ImgManageDataAccess(context);
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
