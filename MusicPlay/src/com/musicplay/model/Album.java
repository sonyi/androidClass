package com.musicplay.model;

public class Album {

	private long id;
	private String album;
	private String albumArt;
	private String albumKey;
	private String artist;
	private int numbweOfSongs;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getAlbumArt() {
		return albumArt;
	}
	public void setAlbumArt(String albumArt) {
		this.albumArt = albumArt;
	}
	public String getAlbumKey() {
		return albumKey;
	}
	public void setAlbumKey(String albumKey) {
		this.albumKey = albumKey;
	}
	
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public int getNumbweOfSongs() {
		return numbweOfSongs;
	}
	public void setNumbweOfSongs(int numbweOfSongs) {
		this.numbweOfSongs = numbweOfSongs;
	}
	@Override
	public String toString() {
		return "专辑名称：" + album + ", 艺人：" + artist ;
	}
	
	
}
