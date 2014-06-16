package com.example.heimusic;

import java.io.Serializable;

public class Song implements Serializable
{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 6419921245498371442L;
		String uri;
	    String songname;
	    String author;
	    boolean isCheck;
	    public Song(String uri,String songname, String author)
	    {
	        this.uri = uri;
	        this.songname = songname;
	        this.author = author;
	    }
	    
}
