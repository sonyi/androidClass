package com.mymusicplay.server;

import java.util.List;

import com.mymusicplay.model.Music;

public interface IPlayBackService {
	/**
	 * 播放音乐
	 */
	public void play();
	
	/**
	 * 暂停播放
	 */
	public void pause();
	
	/**
	 * 停止播放
	 */
	public void stop();
	
	/**
	 * 播放下一曲
	 */
	public void next();
	
	/**
	 * 播放上一曲
	 */
	public void previouse();
	
	/**
	 * 按索引播放
	 * @param index
	 */
	public void playAtIndex(int index);
	
	/**
	 * 添加所有曲目到播放队列中
	 * @param musicList
	 */
	public void addToPlayQuene(List<Music> musicList);
	
	/**
	 * 添加单首曲目到播放列表中
	 * @param music
	 */
	public void addToPlayQuene(Music music);
	
	/**
	 * 获取当前播放状态
	 * @return
	 */
	public int getCurrentPlayState();
	
	/**
	 * 获取当前播放对象
	 * @return
	 */
	public Music getCurrentMusic();
	
}
