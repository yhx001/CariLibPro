/**
 * 
 */
package cn.com.cari.carilibrary.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;

/**
 * @Title: WifiUtil
 * @Description: 
 * @Company: www.cari.com.cn
 * @author zhouzhou
 * @date 2015-11-4 下午3:13:45
 */
public class WifiUtil {
	/**
	 * 打开或关闭wifi
	 * 
	 * @param context
	 * @param open
	 * @return
	 */
	public static boolean openWifi(Context context, boolean open) {
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wm.setWifiEnabled(open);
	}
	/**
	 * 判断wifi网络是否连接 is connect
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (ni.getState() == State.CONNECTED)
			return true;
		return false;
	}

}
