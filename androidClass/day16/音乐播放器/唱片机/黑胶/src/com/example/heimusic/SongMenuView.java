package com.example.heimusic;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.example.heimusic.R;


@SuppressLint("ViewConstructor")
public class SongMenuView extends View
{
	private Paint mPaint = new Paint();
	// stone列表
	private Note[] mStones;
	// 数目
	private int STONE_COUNT;
	// 圆心坐标
	private int mPointX = 0, mPointY = 0;
	// 半径
	private int mRadius = 0;
	// 每两个点间隔的角度
	private float mDegreeDelta;
	private Context context;
	private List<Song> list;
	
	public SongMenuView(Context context, int STONE_COUNT, List<Song> list)
	{
		super(context);
		mPaint.setAntiAlias(true);
		mPointX = -120;
		mPointY = 300;
		mRadius = 400;
		this.context = context;
		this.list = list;
		this.STONE_COUNT = STONE_COUNT;
		setupStones();
		computeCoordinates();
	}
	
	public SongMenuView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mPaint.setAntiAlias(true);
		mPointX = -120;
		mPointY = 300;
		mRadius = 400;
		this.context = context;
	}
	
	public void setSongList(List<Song> list)
	{
		this.list = list;
		this.STONE_COUNT = list.size();
		setupStones();
		computeCoordinates();
	}
	
	public String getCurrentItem()
	{
		for (int index = 0; index < STONE_COUNT; index++)
		{
			Note stone = mStones[index];
			if (stone.x > 260)
			{
				if (stone.y > 270 & stone.y < 340)
				{
					Log.d("lc", "stone.x " + stone.x + "stone.y " + stone.y);
					Log.d("lc", "stone.a " + stone.angle);
					return stone.name;
				}
			}
		}
		return null;
	}
	
	/**
	 * 找到离选定框最近的item
	 * 
	 * @return
	 */
	public int getNearItem()
	{
		for (int index = 0; index < STONE_COUNT; index++)
		{
			Note stone = mStones[index];
			if (stone.x > 260)
			{
				if (stone.y > 270 & stone.y < 345)
				{
					Log.d("lc", "stone.x " + stone.x + "stone.y " + stone.y);
					Log.d("lc", "stone.a " + stone.angle);
					return index;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 初始化每个点
	 */
	private void setupStones()
	{
		mStones = new Note[STONE_COUNT];
		Note stone;
		float angle = 0;
		mDegreeDelta = 360.0f / STONE_COUNT;
		for (int index = 0; index < STONE_COUNT; index++)
		{
			stone = new Note();
			stone.angle = angle;
			Song info = list.get(index);
			stone.author = info.author;
			stone.name = info.songname;
			angle += mDegreeDelta;
			mStones[index] = stone;
		}
	}
	
	/**
	 * 重新计算每个点的角度
	 */
	private void resetStonesAngle(float x, float y)
	{
		float angle = computeCurrentAngle(x, y);
		for (int index = 0; index < STONE_COUNT; index++)
		{
			mStones[index].angle = angle;
			angle += mDegreeDelta;
		}
	}
	
	/**
	 * 计算每个点的坐标
	 */
	private void computeCoordinates()
	{
		Note stone;
		for (int index = 0; index < STONE_COUNT; index++)
		{
			stone = mStones[index];
			stone.x = mPointX
					+ (float) (mRadius * Math.cos(stone.angle * Math.PI / 180));
			stone.y = mPointY
					+ (float) (mRadius * Math.sin(stone.angle * Math.PI / 180));
		}
	}
	
	/**
	 * 计算第一个点的角度
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int computeCurrentAngle(float x, float y)
	{
		float distance = (float) Math
				.sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY)
						* (y - mPointY)));
		int degree = (int) (Math.acos((x - mPointX) / distance) * 180 / Math.PI);
		if (y < mPointY)
		{
			degree = -degree;
		}
		return degree;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch (e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			Log.d("lc", "e.getX() :" + e.getX() + "e.getY() :" + e.getY());
			findTheOne(e.getX(), e.getY());
			break;
		case MotionEvent.ACTION_MOVE:
			resetStonesAngle(e.getX(), e.getY());
			computeCoordinates();
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			catchCurrent();
		}
		return true;
	}
	
	/**
	 * 在选定框内居中
	 */
	private void catchCurrent()
	{
		int index = getNearItem();
		mStones = reset(index);
		float angle = 361;
		for (int i = 0; i < STONE_COUNT; i++)
		{
			mStones[i].angle = angle;
			angle += mDegreeDelta;
		}
		computeCoordinates();
		SystemClock.sleep(220);
		invalidate();
	}
	
	/**
	 * 找到离点击位置最近的那个图标，作为第一个图标
	 * 
	 * @param x
	 * @param y
	 */
	private void findTheOne(float x, float y)
	{
		Note stone;
		float minDistance = 0;
		int minIndex = 0;
		for (int index = 0; index < STONE_COUNT; index++)
		{
			stone = mStones[index];
			if (stone.x > 0)
			{
				float distance = countDistance(x, y, mPointX, mPointY, stone.x,
						stone.y);
				if (index == 0)
				{
					minDistance = distance;
					minIndex = index;
				}
				if (distance < minDistance)
				{
					minDistance = distance;
					minIndex = index;
				}
			}
		}
		mStones = reset(minIndex);
	}
	
	/**
	 * 将minIndex的图标作为第一个图标，重新排列
	 * 
	 * @param minIndex
	 */
	private Note[] reset(int minIndex)
	{
		Note[] newStones = new Note[mStones.length];
		int right = mStones.length - minIndex;
		for (int i = 0; i < mStones.length; i++)
		{
			if (i < right)
			{
				newStones[i] = mStones[minIndex + i];
			}
			if (i >= right)
			{
				newStones[i] = mStones[(minIndex + i) - mStones.length];
			}
		}
		return newStones;
	}
	
	/**
	 * 计算点到线的距离
	 * 
	 * @param x1
	 *            直线的第一个点
	 * @param y1
	 * @param x2
	 *            直线的第二个点
	 * @param y2
	 * @param x
	 *            要计算的点
	 * @param y
	 * @return
	 */
	private float countDistance(float x1, float y1, float x2, float y2,
			float x, float y)
	{
		float k = countK(x1, y1, x2, y2);
		float b = countB(x1, y1, x2, y2);
		float distance = (float) (Math.abs(k * x - y + b) / Math
				.sqrt(k * k + 1));
		return distance;
	}
	
	/**
	 * 计算两点直线的斜率k
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private float countK(float x1, float y1, float x2, float y2)
	{
		float k = (y2 - y1) / (x2 - x1);
		return k;
	}
	
	/**
	 * 计算两点直线的斜率k
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private float countB(float x1, float y1, float x2, float y2)
	{
		float b = (y1 * x2 - y2 * x1) / (x2 - x1);
		return b;
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		// canvas.drawPoint(mPointX, mPointY, mPaint);
		if (STONE_COUNT != 0)
		{
			for (int index = 0; index < STONE_COUNT; index++)
			{
				if (!mStones[index].isVisible)
					continue;
				drawText(canvas, mStones[index].name, mStones[index].x,
						mStones[index].y, mStones[index].author);
			}
		}
	}
	
	 
	void drawText(Canvas canvas, String name, float left, float top,
			String author)
	{
//		canvas.drawPoint(left, top, mPaint);
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(30);
		canvas.drawText(name, left, top, mPaint);
		// mPaint.setTextSize(10);
		// canvas.drawText(author, left, top + 15, mPaint);
	}
	
	class Note
	{
		// 图片
		String name;
		// 角度
		float angle;
		// x坐标
		float x;
		// y坐标
		float y;
		// 是否可见
		String author;
		boolean isVisible = true;
	}
}
