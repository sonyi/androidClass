package com.mymusicplay.server;

import java.util.List;

import android.media.MediaPlayer;

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
	public void addToPlayQueue(List<Music> musicList);
	
	/**
	 * 添加单首曲目到播放列表中
	 * @param music
	 */
	public int addToPlayQuene(Music music);
	
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
	
	/**
	 * 获取歌曲在队列中的位置
	 * @return music在队列中的位置，没找到返回-1
	 */
	public int getCurrentMusicIndex(Music music);
	
	/**
	 * 清除队列中的所有歌曲
	 */
	public void clearPlayQueue();
	
	/**
	 * 获取当前播放音乐的位置
	 */
	public int getMusicPlayPosition();
	
	/**
	 * 获取当前的MediaPlayer
	 * @return
	 */
	public MediaPlayer getMediaPlayer();
	
	/**
	 * 获取当前播放队列
	 * @return
	 */
	public List<Music> getCurrentMusicList();
	
	/**
	 * 获取当前的播放顺序
	 * @return
	 */
	public int getCurrentPlayOrder();
	
	/**
	 * 设置当前播放顺序
	 * @param playOrder
	 */
	public void setCurrentPlayOrder(int playOrder);
	
	/**
	 * 当前音乐到指定位置播放
	 * @param toPosition
	 */
	public void setMusicPlaySeekTo(int toPosition);
	
}
