package com.example.volleytest;

public class GoodsCategory {
	public int id;
	public String categoryName;
	public String description;
	public String imageUrl;
	public int parentId;
	public String displayName;
	public String updatedTime;
	public String createdTime;
	public int status;
	
	@Override
	public String toString() {
		return "GoodsCategory [id=" + id + ", categoryName=" + categoryName
				+ ", description=" + description + ", imageUrl=" + imageUrl
				+ ", parentId=" + parentId + ", displayName=" + displayName
				+ ", updatedTime=" + updatedTime + ", createdTime="
				+ createdTime + ", status=" + status + "]";
	}
	
	

}
