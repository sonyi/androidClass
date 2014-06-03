package com.example.gridviewtest;

public class Feed {
	private long id;
	private String imgPath;
	private String title;
	private String description;
	
	public Feed(long id,String imgPath,String title,String description){
		this.id = id;
		this.setImgPath(imgPath);
		this.title = title;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
