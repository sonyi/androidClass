package com.example.photomanagetest.model;

import com.example.photomanagetest.data.DataContract.PhoteDataContract;

public class PhotoInformation {
	private int id;
	private String imgTitle;
	private String imgResPath;
	private String imgTime;
	public PhotoInformation(){
		
	}
	public PhotoInformation(int id, String imgTitle, String imgResPath, String imgTime) {
		super();
		this.id = id;
		this.imgTitle = imgTitle;
		this.imgResPath = imgResPath;
		this.imgTime = imgTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImgTitle() {
		return imgTitle;
	}
	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}
	public String getImgResPath() {
		return imgResPath;
	}
	public void setImgResPath(String imgResPath) {
		this.imgResPath = imgResPath;
	}
	public String getImgTime() {
		return imgTime;
	}
	public void setImgTime(String imgTime) {
		this.imgTime = imgTime;
	}

}
