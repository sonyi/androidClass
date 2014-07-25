package com.mymusicplay.model;

public class Lrc {
	
	private String musicName;
	private String lrcUrl;
	
	
	
	public Lrc() {
		super();
	}

	public Lrc(String musicName, String lrcUrl) {
		super();
		this.musicName = musicName;
		this.lrcUrl = lrcUrl;
	}

	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getLrcUrl() {
		return lrcUrl;
	}
	public void setLrcUrl(String lrcUrl) {
		this.lrcUrl = lrcUrl;
	}

	@Override
	public String toString() {
		return "Lrc [musicName=" + musicName + ", lrcUrl=" + lrcUrl + "]";
	}
	

}
