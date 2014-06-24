package com.musicplay.service;

import java.util.List;

import com.musicplay.model.Music;
/**
 * ���ֲ��ŷ���
 * @author Yzy
 *
 */
public interface IPlayBackService {
	/**
	 * ���ŷ���
	 */
	public void play();
	/**
	 * ��ͣ����
	 */
	public void pause();
	/**
	 * ֹͣ����
	 */
	public void stop();
	/**
	 * ��һ������
	 */
	public void next();
	/**
	 * ��һ������
	 */
	public void previouse();
	/**
	 * ������һ������
	 */
	public void playAtIndex(int index);
	/**
	 * ���ȫ�����ֵ����Ŷ��з���
	 */
	public void addToPlayQueue(List<Music> musicList);
	/**
	 * ��ӵ������ֵ����Ŷ��з���
	 */
	public int addToPlayQueue(Music music);
	/**
	 * ��ȡ��ǰ����������
	 */
	public int getCurrenPlayState();
	/**
	 * ��ȡ��ǰ���ŵ�����
	 */
	public Music getCurrenMusic();
	/**
	 * ��ȡ��ǰ���ŵĵڼ�������
	 * 
	 */
	public int getCurrentMusicIndex(Music music);
}
