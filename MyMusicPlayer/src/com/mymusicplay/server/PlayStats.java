package com.mymusicplay.server;

public interface PlayStats {
	/**
	 *  Í£Ö¹×´Ì¬
	 */
	public static final int STATE_STOP = 0x00;
	
	/**
	 * ²¥·Å×´Ì¬
	 */
	public static final int STATE_PLAYING = 0x01;
	
	/**
	 * ÔÝÍ£×´Ì¬
	 */
	public static final int STATE_PAUSE = 0x02;
	
}
