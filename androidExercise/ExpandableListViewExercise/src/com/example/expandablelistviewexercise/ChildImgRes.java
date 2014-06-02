package com.example.expandablelistviewexercise;

public class ChildImgRes {

	private int childId;
	private int childPicResId;
	private String childPicTitle;
	private String childPicDescription;
	public ChildImgRes(int id, int childPicResId, String picTitle,
			String picDescription) {
		super();
		this.childId = id;
		this.childPicResId = childPicResId;
		this.childPicTitle = picTitle;
		this.childPicDescription = picDescription;
	}
	public int getId() {
		return childId;
	}
	public void setId(int id) {
		this.childId = id;
	}
	public int getChildPicResId() {
		return childPicResId;
	}
	public void setChildPicResId(int childPicResId) {
		this.childPicResId = childPicResId;
	}
	public String getPicTitle() {
		return childPicTitle;
	}
	public void setPicTitle(String picTitle) {
		this.childPicTitle = picTitle;
	}
	public String getPicDescription() {
		return childPicDescription;
	}
	public void setPicDescription(String picDescription) {
		this.childPicDescription = picDescription;
	}
	
}
