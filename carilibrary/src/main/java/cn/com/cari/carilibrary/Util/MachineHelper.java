package cn.com.cari.carilibrary.Util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class MachineHelper {

	/**
	 * 获取机器唯一识别码
	 * 
	 * @param cxt
	 * @return
	 */
	public static String GetMachineMiei(Context cxt) {
		String result = "";
		TelephonyManager telphony = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
		result = telphony.getDeviceId();
		return result;
	}

	/**
	 * 获取手机信息
	 * 
	 * @param cxt
	 */
	public void GetPhoneInfo(Context cxt) {
		TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);

		/*
		 * 电话状态： 1.tm.CALL_STATE_IDLE=0 无活动 2.tm.CALL_STATE_RINGING=1 响铃
		 * 3.tm.CALL_STATE_OFFHOOK=2 摘机
		 */
		tm.getCallState();// int

		/*
		 * 电话方位：
		 */
		tm.getCellLocation();// CellLocation

		/*
		 * 唯一的设备ID： GSM手机的 IMEI 和 CDMA手机的 MEID. Return null if device ID is not
		 * available.
		 */
		tm.getDeviceId();// String

		/*
		 * 设备的软件版本号： 例如：the IMEI/SV(software version) for GSM phones. Return
		 * null if the software version is not available.
		 */
		tm.getDeviceSoftwareVersion();// String

		/*
		 * 手机号： GSM手机的 MSISDN. Return null if it is unavailable.
		 */
		tm.getLine1Number();// String

		/*
		 * 附近的电话的信息: 类型：List<NeighboringCellInfo>
		 * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES
		 */
		tm.getNeighboringCellInfo();// List<NeighboringCellInfo>

		/*
		 * 获取ISO标准的国家码，即国际长途区号。 注意：仅当用户已在网络注册后有效。 在CDMA网络中结果也许不可靠。
		 */
		tm.getNetworkCountryIso();// String

		/*
		 * MCC+MNC(mobile country code + mobile network code) 注意：仅当用户已在网络注册时有效。
		 * 在CDMA网络中结果也许不可靠。
		 */
		tm.getNetworkOperator();// String

		/*
		 * 按照字母次序的current registered operator(当前已注册的用户)的名字 注意：仅当用户已在网络注册时有效。
		 * 在CDMA网络中结果也许不可靠。
		 */
		tm.getNetworkOperatorName();// String

		/*
		 * 当前使用的网络类型： 例如： NETWORK_TYPE_UNKNOWN 网络类型未知 0 NETWORK_TYPE_GPRS GPRS网络
		 * 1 NETWORK_TYPE_EDGE EDGE网络 2 NETWORK_TYPE_UMTS UMTS网络 3
		 * NETWORK_TYPE_HSDPA HSDPA网络 8 NETWORK_TYPE_HSUPA HSUPA网络 9
		 * NETWORK_TYPE_HSPA HSPA网络 10 NETWORK_TYPE_CDMA CDMA网络,IS95A 或 IS95B. 4
		 * NETWORK_TYPE_EVDO_0 EVDO网络, revision 0. 5 NETWORK_TYPE_EVDO_A EVDO网络,
		 * revision A. 6 NETWORK_TYPE_1xRTT 1xRTT网络 7
		 */
		tm.getNetworkType();// int

		/*
		 * 手机类型： 例如： PHONE_TYPE_NONE 无信号 PHONE_TYPE_GSM GSM信号 PHONE_TYPE_CDMA
		 * CDMA信号
		 */
		tm.getPhoneType();// int

		/*
		 * Returns the ISO country code equivalent for the SIM provider's
		 * country code. 获取ISO国家码，相当于提供SIM卡的国家码。
		 */
		tm.getSimCountryIso();// String

		/*
		 * Returns the MCC+MNC (mobile country code + mobile network code) of
		 * the provider of the SIM. 5 or 6 decimal digits.
		 * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字. SIM卡的状态必须是
		 * SIM_STATE_READY(使用getSimState()判断).
		 */
		tm.getSimOperator();// String

		/*
		 * 服务商名称： 例如：中国移动、联通 SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
		 */
		tm.getSimOperatorName();// String

		/*
		 * SIM卡的序列号： 需要权限：READ_PHONE_STATE
		 */
		tm.getSimSerialNumber();// String

		/*
		 * SIM的状态信息： SIM_STATE_UNKNOWN 未知状态 0 SIM_STATE_ABSENT 没插卡 1
		 * SIM_STATE_PIN_REQUIRED 锁定状态，需要用户的PIN码解锁 2 SIM_STATE_PUK_REQUIRED
		 * 锁定状态，需要用户的PUK码解锁 3 SIM_STATE_NETWORK_LOCKED 锁定状态，需要网络的PIN码解锁 4
		 * SIM_STATE_READY 就绪状态 5
		 */
		tm.getSimState();// int

		/*
		 * 唯一的用户ID： 例如：IMSI(国际移动用户识别码) for a GSM phone. 需要权限：READ_PHONE_STATE
		 */
		tm.getSubscriberId();// String

		/*
		 * 取得和语音邮件相关的标签，即为识别符 需要权限：READ_PHONE_STATE
		 */
		tm.getVoiceMailAlphaTag();// String

		/*
		 * 获取语音邮件号码： 需要权限：READ_PHONE_STATE
		 */
		tm.getVoiceMailNumber();// String

		/*
		 * ICC卡是否存在
		 */
		tm.hasIccCard();// boolean

		/*
		 * 是否漫游: (在GSM用途下)
		 */
		tm.isNetworkRoaming();//

	}

	/**
	 * 获取设备MAC
	 * 
	 * @param context
	 * @return
	 */
	public static String getMac(Context context) {
		String macAddress = null;
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			macAddress = info.getMacAddress();
		}
		if (macAddress == null)
			return null;
		macAddress = macAddress.replace(":", "").toUpperCase();
		macAddress = macAddress.replace(".", "");
		return macAddress.toUpperCase();
	}

	/**
	 * 获取软件版本号
	 *
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			//versionCode = context.getPackageManager().getPackageInfo("vegegpro.com.cn", 0).versionCode;
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
}
