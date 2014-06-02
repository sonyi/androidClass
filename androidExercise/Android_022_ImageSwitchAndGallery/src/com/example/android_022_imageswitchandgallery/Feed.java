package com.example.android_022_imageswitchandgallery;

public class Feed {
	private int id;
	private int imgres;

	public Feed(int id, int imgres) {
		super();
		this.id = id;
		this.imgres = imgres;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getImgres() {
		return imgres;
	}
	public void setImgres(int imgres) {
		this.imgres = imgres;
	}
	
}
