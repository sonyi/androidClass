/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mymusicplay.lrc.control;

import java.io.Serializable;

/**
 * �?��用来表示每一句歌词的�?它封装了歌词的内容以及这句歌词的起始时间 和结束时间，还有�?��实用的方�?
 * 
 * @author Admin
 */
public class Sentence implements Serializable {

	private static final long serialVersionUID = 20071125L;
	private long fromTime;// 这句的起始时�?时间是以毫秒为单�?
	private long toTime;// 这一句的结束时间
	private String content;// 这一句的内容
	private final static long DISAPPEAR_TIME = 1000L;// 歌词从显示完到消失的时间

	public Sentence(String content, long fromTime, long toTime) {
		this.content = content;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	public Sentence(String content, long fromTime) {
		this(content, fromTime, 0);
	}

	public Sentence(String content) {
		this(content, 0, 0);
	}

	public long getFromTime() {
		return fromTime;
	}

	public void setFromTime(long fromTime) {
		this.fromTime = fromTime;
	}

	public long getToTime() {
		return toTime;
	}

	public void setToTime(long toTime) {
		this.toTime = toTime;
	}

	/**
	 * �?��某个时间是否包含在某句中�?
	 * 
	 * @param time
	 *            时间
	 * @return 是否包含�?
	 */
	public boolean isInTime(long time) {
		return time >= fromTime && time <= toTime;
	}

	/**
	 * 得到这一句的内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 得到这个句子的时间长�?毫秒为单�?
	 * 
	 * @return 长度
	 */
	public long getDuring() {
		return toTime - fromTime;
	}

	public String toString() {
		return "{" + fromTime + "(" + content + ")" + toTime + "}";
	}
}
