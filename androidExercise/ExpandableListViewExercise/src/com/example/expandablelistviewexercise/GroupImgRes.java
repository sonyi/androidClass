package com.example.expandablelistviewexercise;

public class GroupImgRes {

	private int groupId;
	private int groupPicResId;
	private String groupPicTitle;
	public GroupImgRes(int groupId, int groupPicResId, String groupPicTitle) {
		super();
		this.groupId = groupId;
		this.groupPicResId = groupPicResId;
		this.groupPicTitle = groupPicTitle;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getGroupPicResId() {
		return groupPicResId;
	}
	public void setGroupPicResId(int groupPicResId) {
		this.groupPicResId = groupPicResId;
	}
	public String getGroupPicTitle() {
		return groupPicTitle;
	}
	public void setGroupPicTitle(String groupPicTitle) {
		this.groupPicTitle = groupPicTitle;
	}
}
