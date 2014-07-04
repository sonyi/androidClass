package com.mymusicplay.server;

public interface PlayStaticConst {
	/**
	 * 处于停止播放状态
	 */
	public static final int STATE_STOP = 0x00;

	/**
	 * 处于播放状态
	 */
	public static final int STATE_PLAYING = 0x01;

	/**
	 * 处于暂停播放状态
	 */
	public static final int STATE_PAUSE = 0x02;

	/**
	 * 随机播放
	 */
	public static final int STATE_RANDOM = 0x03;

	/**
	 * 列表循环
	 */
	public static final int STATE_LOOP = 0x04;

	/**
	 * 单曲循环
	 */
	public static final int STATE_CYCLE = 0x05;
	
	/**
	 * 播放下一曲
	 */
	public static final int PLAY_NEXT = 0x06;
	
	/**
	 * 播放上一曲
	 */
	public static final int PLAY_PREVIOUS = 0x07;
	
	/**
	 * 自动播放下一首(一首结束时)
	 */
	public static final int PLAY_AUTO_NEXT = 0x08;
	
	/**
	 * 通过索引播放(点击播放)
	 */
	public static final int PLAY_BY_INDEX = 0x09;

}
