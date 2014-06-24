package com.musicplay.service;

import java.util.List;

import com.musicplay.model.Music;
/**
 * 音乐播放服务
 * @author Yzy
 *
 */
public interface IPlayBackService {
	/**
	 * 播放服务
	 */
	public void play();
	/**
	 * 暂停服务
	 */
	public void pause();
	/**
	 * 停止服务
	 */
	public void stop();
	/**
	 * 下一曲服务
	 */
	public void next();
	/**
	 * 上一曲服务
	 */
	public void previouse();
	/**
	 * 播放哪一曲服务
	 */
	public void playAtIndex(int index);
	/**
	 * 添加全部音乐到播放队列服务
	 */
	public void addToPlayQueue(List<Music> musicList);
	/**
	 * 添加单曲音乐到播放队列服务
	 */
	public int addToPlayQueue(Music music);
	/**
	 * 获取当前播放音乐数
	 */
	public int getCurrenPlayState();
	/**
	 * 获取当前播放的音乐
	 */
	public Music getCurrenMusic();
	/**
	 * 获取当前播放的第几首音乐
	 * 
	 */
	public int getCurrentMusicIndex(Music music);
}
