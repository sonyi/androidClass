/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mymusicplay.lrc.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表示�?��歌的歌词对象,它可以以某种方式来画自己
 * 
 * @author hadeslee
 */
public class Lyric implements Serializable {

	private static final long serialVersionUID = 20071125L;
	private String HOME = "/sdcard/UmilePlayer/music/";
	private static Logger log = Logger.getLogger(Lyric.class.getName());
	private int width;// 表示歌词的显示区域的宽度
	private int height;// 表示歌词的显示区域的高度
	private long time;// 表示当前的时间是多少了�?以毫秒为单位
	private long tempTime;// 表示�?��暂时的时�?用于拖动的时�?确定应该到哪�?
	public List<Sentence> list = new ArrayList<Sentence>();// 里面装的是所有的句子
	private boolean isMoving;// 是否正在被拖�?
	private int currentIndex;// 当前正在显示的歌词的下标
	private boolean initDone;// 是否初始化完毕了
	private transient PlayListItem info;// 有关于这首歌的信�?
	private transient File file;// 该歌词所存在文件
	private boolean enabled = true;// 是否起用了该对象,默认是起用的
	private long during = Integer.MAX_VALUE;// 这首歌的长度
	private int offset;// 整首歌的偏移�?
	// 用于缓存的一个正则表达式对象
	private static final Pattern pattern = Pattern
			.compile("(?<=\\[).*?(?=\\])");

	/**
	 * 用ID3V1标签的字节和歌名来初始化歌词 歌词将自动在本地或�?网络上搜索相关的歌词并建立关�?
	 * 本地搜索将硬编码为user.home文件夹下面的Lyrics文件�?以后改为可以手动设置.
	 * 
	 * @param songName
	 *            歌名
	 * @param data
	 *            ID3V1的数�?
	 */
	public Lyric(final PlayListItem info) {
		this.offset = info.getOffset();
		this.info = info;
		// this.during = info.getDuration();
		this.file = info.getLyricFile();
		log.info("传进来的歌名�?" + info.toString());
		// 只要有关联好了的，就不用搜索了直接用就是�?
		if (file != null && file.exists()) {
			log.log(Level.INFO, "不用找了，直接关联到的歌词是：" + file);
			init(file);
			initDone = true;
			return;
		} else {
			// 否则就起�?��线程去找了，先是本地找，然后再是网络上找
			new Thread() {

				public void run() {
					doInit(info);
					initDone = true;
				}
			}.start();
		}

	}

	/**
	 * 读取某个指定的歌词文�?这个构�?函数�?��用于 拖放歌词文件到歌词窗口时调用�?拖放以后,两个自动关联
	 * 
	 * @param file
	 *            歌词文件
	 * @param info
	 *            歌曲信息
	 */
	public Lyric(File file, PlayListItem info) {
		System.out.println(" Lyric file" + file);
		this.offset = info.getOffset();
		this.file = file;
		this.info = info;
		init(file);
		initDone = true;
	}

	/**
	 * 根据歌词内容和播放项构�?�?�� 歌词对象
	 * 
	 * @param lyric
	 *            歌词内容
	 * @param info
	 *            播放�?
	 */
	public Lyric(String lyric, PlayListItem info) {
		this.offset = info.getOffset();
		this.info = info;
		this.init(lyric);
		initDone = true;
	}

	private void doInit(PlayListItem info) {
		init(info);

		Sentence temp = null;
		// 这个时�?就要去网络上找了
		if (list.size() == 1) {
			temp = list.remove(0);
			String lyric = "";
			if (lyric != null) {
				init(lyric);
				// saveLyric(lyric, info);
			} else {// 如果网络也没有找�?就要加回去了

			}
			list.add(temp);
		}
	}

	/**
	 * 把下载到的歌词保存起�?免得下次再去�?
	 * 
	 * @param lyric
	 *            歌词内容
	 * @param info
	 *            歌的信息
	 */
	private void saveLyric(String lyric, PlayListItem info) {
		try {
			// 如果歌手不为�?则以歌手�?歌曲名为�?��组合
			String name = info.getFormattedName() + ".lrc";
			File dir = new File(HOME, "Lyrics" + File.separator);
			// File dir = Config.getConfig().getSaveLyricDir();
			dir.mkdirs();
			file = new File(dir, name);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			bw.write(lyric);
			bw.close();
			info.setLyricFile(file);
			log.info("保存完毕,保存�?" + file);
		} catch (Exception exe) {
			log.log(Level.SEVERE, "保存歌词出错", exe);
		}
	}

	/**
	 * 设置此歌词是否起用了,否则就不动了
	 * 
	 * @param b
	 *            是否起用
	 */
	public void setEnabled(boolean b) {
		this.enabled = b;
	}

	/**
	 * 得到此歌词保存的地方
	 * 
	 * @return 文件
	 */
	public File getLyricFile() {
		return file;
	}

	/**
	 * 调整整体的时�?比如歌词统一快多�?或�?歌词统一慢多�?为正说明要快,为负说明要慢
	 * 
	 * @param time
	 *            要调的时�?单位是毫�?
	 */
	public void adjustTime(int time) {
		// 如果是只有一个显示的,那就说明没有�?��效对的意义了,直接返回
		if (list.size() == 1) {
			return;
		}
		offset += time;
		info.setOffset(offset);
	}

	/**
	 * 根据�?��文件�?和一个歌曲的信息 从本地搜到最匹配的歌�?
	 * 
	 * @param dir
	 *            目录
	 * @param info
	 *            歌曲信息
	 * @return 歌词文件
	 */
	private File getMathedLyricFile(File dir, PlayListItem info) {
		File matched = null;// 已经匹配的文�?
		File[] fs = dir.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				return pathname.getName().toLowerCase().endsWith(".lrc");
			}
		});
		for (File f : fs) {
			// 全部匹配或�?部分匹配都行
			if (matchAll(info, f) || matchSongName(info, f)) {
				matched = f;
				break;
			}
		}
		return matched;
	}

	/**
	 * 根据歌的信息去初始化,这个时�? 可能在本地找到歌词文�?也可能要去网络上搜索�?
	 * 
	 * @param info
	 *            歌曲信息
	 */
	private void init(PlayListItem info) {
		File matched = null;
		// 得到歌曲信息�?先搜索HOME文件�?
		// 如果还不存在的话,那建�?��目录,然后直接�?��不管�?

		File dir = new File(HOME, "Lyrics" + File.separator);
		if (!dir.exists()) {
			dir.mkdirs();
			// }
			matched = getMathedLyricFile(dir, info);
		}
		log.info("找到的是:" + matched);
		if (matched != null && matched.exists()) {
			info.setLyricFile(matched);
			file = matched;
			init(matched);
		} else {
			init("");
		}
	}

	/**
	 * 根据文件来初始化
	 * 
	 * @param file
	 *            文件
	 */
	private void init(File file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "utf-8"));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {

				sb.append(temp).append("\n");
			}
			init(sb.toString());
		} catch (Exception ex) {
			Logger.getLogger(Lyric.class.getName()).log(Level.SEVERE, null, ex);

		} finally {
			try {
				if(br != null){
					br.close();
				}
				
			} catch (Exception ex) {
				Logger.getLogger(Lyric.class.getName()).log(Level.SEVERE, null,
						ex);
			}
		}
	}

	/**
	 * 是否完全匹配,完全匹配是指直接对应到ID3V1的标�? 如果�?��,则完全匹配了,完全匹配的LRC的文件格式是: 阿木 - 有一种爱叫放�?lrc
	 * 
	 * @param info
	 *            歌曲信息
	 * @param file
	 *            侯�?文件
	 * @return 是否合格
	 */
	private boolean matchAll(PlayListItem info, File file) {
		String name = info.getFormattedName();
		String fn = file.getName()
				.substring(0, file.getName().lastIndexOf("."));
		if (name.equals(fn)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否匹配了歌曲名
	 * 
	 * @param info
	 *            歌曲信息
	 * @param file
	 *            侯�?文件
	 * @return 是否合格
	 */
	private boolean matchSongName(PlayListItem info, File file) {
		String name = info.getFormattedName();
		String rn = file.getName()
				.substring(0, file.getName().lastIndexOf("."));
		if (name.equalsIgnoreCase(rn) || info.getTitle().equalsIgnoreCase(rn)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �?��要的�?��方法，它根据读到的歌词内�?进行初始化，比如把歌词一句一句分�?��计算好时�?
	 * 
	 * @param content
	 *            歌词内容
	 */
	private void init(String content) {
		// 如果歌词的内容为�?则后面就不用执行�?
		// 直接显示歌曲名就可以�?
		if (content == null || content.trim().equals("")) {
			list.add(new Sentence(info.getFormattedName(), Integer.MIN_VALUE,
					Integer.MAX_VALUE));
			return;
		}
		try {
			BufferedReader br = new BufferedReader(new StringReader(content));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				parseLine(temp.trim());
			}
			br.close();
			// 读进来以后就排序�?
			Collections.sort(list, new Comparator<Sentence>() {

				public int compare(Sentence o1, Sentence o2) {
					return (int) (o1.getFromTime() - o2.getFromTime());
				}
			});
			// 处理第一句歌词的起始情况,无论怎么�?加上歌名做为第一句歌�?并把它的
			// 结尾为真正第�?��歌词的开�?
			if (list.size() == 0) {
				list.add(new Sentence(info.getFormattedName(), 0,
						Integer.MAX_VALUE));
				return;
			} else {
				Sentence first = list.get(0);
				list.add(
						0,
						new Sentence(info.getFormattedName(), 0, first
								.getFromTime()));
			}

			int size = list.size();
			for (int i = 0; i < size; i++) {
				Sentence next = null;
				if (i + 1 < size) {
					next = list.get(i + 1);
				}
				Sentence now = list.get(i);
				if (next != null) {
					now.setToTime(next.getFromTime() - 1);
				}
			}
			// 如果就是没有怎么�?那就只显示一句歌名了
			if (list.size() == 1) {
				list.get(0).setToTime(Integer.MAX_VALUE);
			} else {
				Sentence last = list.get(list.size() - 1);
				last.setToTime(info == null ? Integer.MAX_VALUE : info
						.getLength() * 1000 + 1000);
			}
		} catch (Exception ex) {
			Logger.getLogger(Lyric.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * 分析出整体的偏移�?
	 * 
	 * @param str
	 *            包含内容的字符串
	 * @return 偏移量，当分析不出来，则返回�?��的正�?
	 */
	private int parseOffset(String str) {
		String[] ss = str.split("\\:");
		if (ss.length == 2) {
			if (ss[0].equalsIgnoreCase("offset")) {
				int os = Integer.parseInt(ss[1]);
				System.err.println("整体的偏移量：" + os);
				return os;
			} else {
				return Integer.MAX_VALUE;
			}
		} else {
			return Integer.MAX_VALUE;
		}
	}

	/**
	 * 分析这一行的内容，根据这内容 以及标签的数量生成若干个Sentence对象 当此行中的时间标签分布不在一起时，也要能分析出来 �?��更改了一些实�?
	 * 20080824更新
	 * 
	 * @param line
	 *            这一�?
	 */
	private void parseLine(String line) {
		if (line.equals("")) {
			return;
		}
		Matcher matcher = pattern.matcher(line);
		List<String> temp = new ArrayList<String>();
		int lastIndex = -1;// �?���?��时间标签的下�?
		int lastLength = -1;// �?���?��时间标签的长�?
		while (matcher.find()) {
			String s = matcher.group();
			int index = line.indexOf("[" + s + "]");
			if (lastIndex != -1 && index - lastIndex > lastLength + 2) {
				// 如果大于上次的大小，则中间夹了别的内容在里面
				// 这个时�?就要分段�?
				String content = line.substring(lastIndex + lastLength + 2,
						index);
				for (String str : temp) {
					long t = parseTime(str);
					if (t != -1) {
						System.out.println("content = " + content);
						System.out.println("t = " + t);
						list.add(new Sentence(content, t));
					}
				}
				temp.clear();
			}
			temp.add(s);
			lastIndex = index;
			lastLength = s.length();
		}
		// 如果列表为空，则表示本行没有分析出任何标�?
		if (temp.isEmpty()) {
			return;
		}
		try {
			int length = lastLength + 2 + lastIndex;
			String content = line.substring(length > line.length() ? line
					.length() : length);
			// if (Config.getConfig().isCutBlankChars()) {
			// content = content.trim();
			// }
			// 当已经有了偏移量的时候，就不再分析了
			if (content.equals("") && offset == 0) {
				for (String s : temp) {
					int of = parseOffset(s);
					if (of != Integer.MAX_VALUE) {
						offset = of;
						info.setOffset(offset);
						break;// 只分析一�?
					}
				}
				return;
			}
			for (String s : temp) {
				long t = parseTime(s);
				if (t != -1) {
					list.add(new Sentence(content, t));
					System.out.println("content = " + content);
					System.out.println("t = " + t);
				}
			}
		} catch (Exception exe) {
		}
	}

	/**
	 * 把如00:00.00这样的字符串转化�?毫秒数的时间，比�?01:10.34就是�?��钟加�?0秒再加上340毫秒 也就是返�?0340毫秒
	 * 
	 * @param time
	 *            字符串的时间
	 * @return 此时间表示的毫秒
	 */
	private long parseTime(String time) {
		String[] ss = time.split("\\:|\\.");
		// 如果 是两位以后，就非法了
		if (ss.length < 2) {
			return -1;
		} else if (ss.length == 2) {// 如果正好两位，就算分�?
			try {
				// 先看有没有一个是记录了整体偏移量�?
				if (offset == 0 && ss[0].equalsIgnoreCase("offset")) {
					offset = Integer.parseInt(ss[1]);
					info.setOffset(offset);
					System.err.println("整体的偏移量：" + offset);
					return -1;
				}
				int min = Integer.parseInt(ss[0]);
				int sec = Integer.parseInt(ss[1]);
				if (min < 0 || sec < 0 || sec >= 60) {
					throw new RuntimeException("数字不合�?");
				}
				// System.out.println("time" + (min * 60 + sec) * 1000L);
				return (min * 60 + sec) * 1000L;
			} catch (Exception exe) {
				return -1;
			}
		} else if (ss.length == 3) {// 如果正好三位，就算分秒，十毫�?
			try {
				int min = Integer.parseInt(ss[0]);
				int sec = Integer.parseInt(ss[1]);
				int mm = Integer.parseInt(ss[2]);
				if (min < 0 || sec < 0 || sec >= 60 || mm < 0 || mm > 99) {
					throw new RuntimeException("数字不合�?");
				}
				// System.out.println("time" + (min * 60 + sec) * 1000L + mm *
				// 10);
				return (min * 60 + sec) * 1000L + mm * 10;
			} catch (Exception exe) {
				return -1;
			}
		} else {// 否则也非�?
			return -1;
		}
	}

	/**
	 * 设置其显示区域的高度
	 * 
	 * @param height
	 *            高度
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 设置其显示区域的宽度
	 * 
	 * @param width
	 *            宽度
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 设置时间,设置的时候，要把整体的偏移时间算�?
	 * 
	 * @param time
	 *            时间
	 */
	public void setTime(long time) {
		if (!isMoving) {
			tempTime = this.time = time + offset;
		}
	}

	/**
	 * 得到是否初始化完成了
	 * 
	 * @return 是否完成
	 */
	public boolean isInitDone() {
		return initDone;
	}

	/**
	 * 得到当前正在播放的那�?��的下�?不可能找不到，因为最�?��要加�?�� 自己的句�?，所以加了以后就不可能找不到�?
	 * 
	 * @return 下标
	 */
	int getNowSentenceIndex(long t) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isInTime(t)) {
				return i;
			}
		}
		// throw new RuntimeException("竟然出现了找不到的情况！");
		return -1;
	}

	/**
	 * 是否能拖�?只有有歌词才可以被拖�?否则没有意义�?
	 * 
	 * @return 能否拖动
	 */
	public boolean canMove() {
		return list.size() > 1 && enabled;
	}

	/**
	 * 得到当前的时�?�?��是由显示面板调用�?
	 */
	public long getTime() {
		return tempTime;
	}

	/**
	 * 在对tempTime做了改变之后,�?���?��它的 �?看是不是在有效的范围之内
	 */
	private void checkTempTime() {
		if (tempTime < 0) {
			tempTime = 0;
		} else if (tempTime > during) {
			tempTime = during;
		}
	}

	/**
	 * 告诉歌词,要开始移动了, 在此期间,�?��对歌词的直接的时间设置都不理�?
	 */
	public void startMove() {
		isMoving = true;
	}

	/**
	 * 告诉歌词拖动完了,这个时�?的时间改 变要理会,并做更改
	 */
	public void stopMove() {
		isMoving = false;
	}

}
