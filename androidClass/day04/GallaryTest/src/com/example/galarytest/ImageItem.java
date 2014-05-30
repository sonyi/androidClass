package com.example.galarytest;

public class ImageItem {
	private long _id;
	private String imgName;
	private int imgResId;
	
	public ImageItem(long _id,String imgName,int imgResId){
		this._id = _id;
		this.imgName = imgName;
		this.imgResId = imgResId;
	}
	
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public int getImgResId() {
		return imgResId;
	}
	public void setImgResId(int imgResIn) {
		this.imgResId = imgResIn;
	}
	
}
