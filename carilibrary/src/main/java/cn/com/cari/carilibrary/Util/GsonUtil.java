/**
 * 
 */
package cn.com.cari.carilibrary.Util;

import com.google.gson.Gson;

/**
 * @Title: GsonUtil
 * @Description: 
 * @Company: www.cari.com.cn
 * @author zhouzhou
 * @date 2015-11-4 下午4:30:14
 */
public class GsonUtil {
	public static String toJson(Object o){
		Gson gson = new Gson();
		return gson.toJson(o);		
	}
}
