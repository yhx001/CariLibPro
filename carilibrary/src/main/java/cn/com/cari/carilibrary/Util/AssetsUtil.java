/**
 * 
 */
package cn.com.cari.carilibrary.Util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Title: AssetsUtil
 * @Description: 
 * @Company: www.cari.com.cn
 * @author zhouzhou
 * @date 2015-11-4 下午3:17:59
 */
public class AssetsUtil {
	/**
	 * 从Assets中读取图片
	 */
	public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
