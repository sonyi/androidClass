package com.example.musicplayer.service;

import java.util.List;

import com.example.musicplayer.model.Music;

/**
 * ���ֲ��ŷ���ӿ�
 * 
 * @author user
 */
public interface IPlayBackService {
	/**
	 * 
	 */
	public void play();

	public void pause();

	public void stop();

	public void next();

	public void previouse();

	public void playAtIndex(int index);

	public void addToPlayQueue(List<Music> musicList);

	public void addToPlayQueue(Music music);

	public int getCurrentPlayState();

	public Music getCurrentMusic();
}
