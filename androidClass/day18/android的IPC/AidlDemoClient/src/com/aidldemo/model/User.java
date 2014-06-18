package com.aidldemo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private long id;
	private String name;

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
	
	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
		
		@Override
		public User[] newArray(int size) {
			return null;
		}
		
		@Override
		public User createFromParcel(Parcel source) {
			User user = new User();
			user.id = source.readLong();
			user.name = source.readString();
			return user;
		}
	};
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(name);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
}
