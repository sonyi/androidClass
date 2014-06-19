package com.mybookcollection.model;

/**
 * 图书类别实体类
 * 
 * @author user
 */
public class BookCatagory {
	private long id;
	private String catagoryName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCatagoryName() {
		return catagoryName;
	}

	public void setCatagoryName(String catagoryName) {
		this.catagoryName = catagoryName;
	}

}
