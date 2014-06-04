package com.example.homeactivity;

public class Feed {
	private int id;
	private String imgTitle;
	private int imgResId;
	private String imgTime;
	public Feed(int id, String imgTitle, int imgResId, String imgTime) {
		super();
		this.id = id;
		this.imgTitle = imgTitle;
		this.imgResId = imgResId;
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
	public int getImgResId() {
		return imgResId;
	}
	public void setImgResId(int imgResId) {
		this.imgResId = imgResId;
	}
	public String getImgTime() {
		return imgTime;
	}
	public void setImgTime(String imgTime) {
		this.imgTime = imgTime;
	}

}
