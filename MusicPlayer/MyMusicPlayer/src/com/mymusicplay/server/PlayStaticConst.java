package com.mymusicplay.server;

public interface PlayStaticConst {
	/**
	 * ����ֹͣ����״̬
	 */
	public static final int STATE_STOP = 0x00;

	/**
	 * ���ڲ���״̬
	 */
	public static final int STATE_PLAYING = 0x01;

	/**
	 * ������ͣ����״̬
	 */
	public static final int STATE_PAUSE = 0x02;

	/**
	 * �������
	 */
	public static final int STATE_RANDOM = 0x03;

	/**
	 * �б�ѭ��
	 */
	public static final int STATE_LOOP = 0x04;

	/**
	 * ����ѭ��
	 */
	public static final int STATE_CYCLE = 0x05;
	
	/**
	 * ������һ��
	 */
	public static final int PLAY_NEXT = 0x06;
	
	/**
	 * ������һ��
	 */
	public static final int PLAY_PREVIOUS = 0x07;
	
	/**
	 * �Զ�������һ��(һ�׽���ʱ)
	 */
	public static final int PLAY_AUTO_NEXT = 0x08;
	
	/**
	 * ͨ����������(�������)
	 */
	public static final int PLAY_BY_INDEX = 0x09;

}
