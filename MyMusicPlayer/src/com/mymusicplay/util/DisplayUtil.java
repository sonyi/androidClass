package com.mymusicplay.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
	/**
	 * 鑾峰彇灞忓箷瀹藉害鐨勫儚绱犲�
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 鑾峰彇灞忓箷楂樺害鍍忕礌鍊�
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * 鎶奷p鍗曚綅鐨勫昂瀵稿�杞崲涓簆x鍗曚綅
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2Px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 鎶妏x鍗曚綅鐨勫昂瀵稿�杞崲涓篸p鍗曚綅
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2Dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
