package com.musicplay.medel;

public class Contacts {
	private long id;
	private String name;
	private long phoneNumber;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return " 联系人姓名：" + name + ", 电话号码："
				+ phoneNumber;
	}
	
}
