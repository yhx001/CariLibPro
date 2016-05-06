/**
 * 
 */
package cn.com.cari.carilibrary.Util;

import android.widget.TextView;

/**
 * @Title: TextViewHelper
 * @Description:
 * @Company: www.cari.com.cn
 * @author zhouzhou
 * @date 2015-9-25 上午8:44:41
 */
public class TextViewHelper {
	/**
	 * 设置标题栏标题 如果文字数目过多，则文字变小
	 * 
	 * @param titleStr
	 * @param tv
	 */
	public static void SetTitle(String titleStr, TextView tv) {
		if (titleStr.length() > 5) { // 如果路线名称过长，则字体缩小
			tv.setTextSize(16);
		} else {
			tv.setTextSize(22);
		}
		tv.setText(titleStr);
	}
}
