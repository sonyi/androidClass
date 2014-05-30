package com.example.listviewtest;

public class Feed {
	private long id;
	private int imgResId;
	private String title;
	private String description;
	
	public Feed(long id,int imgResId,String title,String description){
		this.id = id;
		this.imgResId = imgResId;
		this.title = title;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getImgResId() {
		return imgResId;
	}
	public void setImgResId(int imgResId) {
		this.imgResId = imgResId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
