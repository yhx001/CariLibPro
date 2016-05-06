/**
 * 
 */
package cn.com.cari.carilibrary.Util;

import android.util.Log;

import java.util.UUID;

/**
 * @Title: UUIDUtil
 * @Description: 
 * @Company: www.cari.com.cn
 * @author zhouzhou
 * @date 2015-11-4 下午3:14:23
 */
public class UUIDUtil {
	/**
	 * 获取32位UUID编号
	 * 
	 * @return
	 */
	public static String GetUUID() {
		String uuid = UUID.randomUUID().toString();
		Log.d("debug", "----->UUID" + uuid);
		return uuid;
	}

}
