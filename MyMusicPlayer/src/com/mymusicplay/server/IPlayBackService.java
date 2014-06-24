package com.mymusicplay.server;

import java.util.List;

import com.mymusicplay.model.Music;

public interface IPlayBackService {
	/**
	 * ��������
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
	 * ������һ��
	 */
	public void next();
	
	/**
	 * ������һ��
	 */
	public void previouse();
	
	/**
	 * ����������
	 * @param index
	 */
	public void playAtIndex(int index);
	
	/**
	 * ���������Ŀ�����Ŷ�����
	 * @param musicList
	 */
	public void addToPlayQuene(List<Music> musicList);
	
	/**
	 * ��ӵ�����Ŀ�������б���
	 * @param music
	 */
	public int addToPlayQuene(Music music);
	
	/**
	 * ��ȡ��ǰ����״̬
	 * @return
	 */
	public int getCurrentPlayState();
	
	/**
	 * ��ȡ��ǰ���Ŷ���
	 * @return
	 */
	public Music getCurrentMusic();
	
	/**
	 * ��ȡ�����ڶ����е�λ��
	 * @return music�ڶ����е�λ�ã�û�ҵ�����-1
	 */
	public int getCurrentMusicIndex(Music music);
	
}
