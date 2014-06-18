package com.example.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
	private int id;
	private String name;
	
	public User() {
		super();
	}
	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(name);
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

		@Override
		public User createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			User user = new User();
			user.id = source.readInt();
			user.name = source.readString();
			return user;
		}

		@Override
		public User[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
}
