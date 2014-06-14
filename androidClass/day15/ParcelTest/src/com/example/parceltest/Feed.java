package com.example.parceltest;

import android.os.Parcel;
import android.os.Parcelable;

public class Feed implements Parcelable{
	private int id;
	private String title;
	private String author;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(author);
	}
	
	public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {

		@Override
		public Feed createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Feed feed = new Feed();
			feed.setId(source.readInt());
			feed.setTitle(source.readString());
			feed.setAuthor(source.readString());
			return feed;
		}

		@Override
		public Feed[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
