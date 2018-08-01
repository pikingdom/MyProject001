package com.zteguidedemo.v0840.wifi.Business;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.net.wifi.WifiConfiguration;

import com.zteguidedemo.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WifiHelper {

	public enum KeyMgmt {
		// 定义几种加密方式
		SECURITY_NONE,
		SECURITY_WPA_PSK,
		SECURITY_WEP,
		SECURITY_WPA2_PSK,
		SECURITY_WPA_WPA2_PSK,
		SECURITY_WPA2_EAP,
		SECURITY_WPA2_PSK_USE_WPS
	}

	public enum PskType {
		WEP, WPA_PSK, NOPASS, WPA_EAP
	}

	/**
	 * 获取加密方式,用于设置
	 * @param @param capabilities
	 * @return PskType
	 * @throws
	 */
	public static PskType getSecurityType(String capabilities) {
		if (capabilities.contains("WEP")) {
			return PskType.WEP;
		} else if (capabilities.contains("WPA-PSK")
				|| capabilities.contains("WPA2-PSK")) {   //capabilities.contains("PSK")
			return PskType.WPA_PSK;
		} else if (capabilities.contains("WPA2-EAP")) {
			return PskType.WPA_EAP;
		}
		return PskType.NOPASS;
	}

	/**
	 * 获取加密方式,用于显示
	 * @param @param capabilities
	 * @return KeyMgmt
	 * @throws
	 */
	public static KeyMgmt getKeyMgmt(String capabilities) {
		if (capabilities == null) return KeyMgmt.SECURITY_NONE;
		//通过WPA进行保护
		boolean wpa = capabilities.contains("WPA-PSK");
		//通过WPA2进行保护
		boolean wpa2 = capabilities.contains("WPA2-PSK");
		boolean wep = capabilities.contains("WEP");
		//通过WPA2进行保护（可使用wps）
		boolean wps = capabilities.contains("WPS");
		//通过802.1x进行保护
		boolean wpa2_eap = capabilities.contains("WPA2-EAP");

		//通过WPA/wpa2进行保护
		if (wpa2 && wpa) {
			return KeyMgmt.SECURITY_WPA_WPA2_PSK;
		} else if (wpa2 && wps) {
			return KeyMgmt.SECURITY_WPA2_PSK_USE_WPS;
		} else if (wpa) {
			return KeyMgmt.SECURITY_WPA_PSK;
		} else if (wep) {
			return KeyMgmt.SECURITY_WEP;
		} else if (wpa2) {
			return KeyMgmt.SECURITY_WPA2_PSK;
		}else if (wpa2_eap) {
			return KeyMgmt.SECURITY_WPA2_EAP;
		} else {
			return KeyMgmt.SECURITY_NONE;
		}
	}

	/**
	 * 设置ip分配
	 * @param assign
	 * @param wifiConf
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static void setIpAssignment(String assign, WifiConfiguration wifiConf) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		setEnumField(wifiConf, assign, "ipAssignment");
	}

	/**
	 * 设置ip地址
	 * @param addr
	 * @param prefixLength
	 * @param wifiConf
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setIpAddress(InetAddress addr, int prefixLength, WifiConfiguration wifiConf) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException {
		Object linkProperties = getField(wifiConf, "linkProperties");
		if (linkProperties == null)
			return;
		Class laClass = Class.forName("android.net.LinkAddress");
		Constructor laConstructor = laClass.getConstructor(new Class[] { InetAddress.class, int.class });
		Object linkAddress = laConstructor.newInstance(addr, prefixLength);

		ArrayList<Object> mLinkAddresses = (ArrayList) getDeclaredField(linkProperties, "mLinkAddresses");
		mLinkAddresses.clear();
		mLinkAddresses.add(linkAddress);
	}

	/**
	 * 设置网关
	 * @param gateway
	 * @param wifiConf
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setGateway(InetAddress gateway, WifiConfiguration wifiConf) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
		Object linkProperties = getField(wifiConf, "linkProperties");
		if (linkProperties == null)
			return;
		Class routeInfoClass = Class.forName("android.net.RouteInfo");
		Constructor routeInfoConstructor = routeInfoClass.getConstructor(new Class[] { InetAddress.class });
		Object routeInfo = routeInfoConstructor.newInstance(gateway);

		ArrayList mRoutes = (ArrayList) getDeclaredField(linkProperties, "mRoutes");
		mRoutes.clear();
		mRoutes.add(routeInfo);
	}

	/**
	 * 设置DNS
	 * @param dns
	 * @param wifiConf
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static void setDNS(InetAddress[] dns, WifiConfiguration wifiConf) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		Object linkProperties = getField(wifiConf, "linkProperties");
		if (linkProperties == null)
			return;

		ArrayList<InetAddress> mDnses = (ArrayList<InetAddress>) getDeclaredField(linkProperties, "mDnses");
		mDnses.clear();
		for (InetAddress inetAddress : dns) {
			mDnses.add(inetAddress);
		}
	}

	private static Object getField(Object obj, String name) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field f = obj.getClass().getField(name);
		Object out = f.get(obj);
		return out;
	}

	private static Object getDeclaredField(Object obj, String name) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field f = obj.getClass().getDeclaredField(name);
		f.setAccessible(true);
		Object out = f.get(obj);
		return out;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void setEnumField(Object obj, String value, String name) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field f = obj.getClass().getField(name);
		f.set(obj, Enum.valueOf((Class<Enum>) f.getType(), value));
	}

	/**
	 * 去除ssid首尾的引号
	 * @param ssid
	 * @return
	 */
	public static String dealWithSSID(String ssid) {
		if (null == ssid || "".equals("ssid")) {
			return "";
		}
		if (ssid.startsWith("\"")) {
			ssid = ssid.substring(1);
		}
		if (ssid.endsWith("\"")) {
			ssid = ssid.substring(0, ssid.length() - 1);
		}
		return ssid;
	}

	/**
	 * 改变加密信息
	 * @param ctx
	 * @param type
	 * @return
	 */
	public static String changeName(Context ctx, KeyMgmt type) {
		String name = "";
		switch (type) {
		case SECURITY_NONE:
			name = ctx.getString(R.string.wifi_none);
			break;
		case SECURITY_WPA_PSK:
			name = ctx.getString(R.string.wifi_wpa);
			break;
		case SECURITY_WEP:
			name = ctx.getString(R.string.wifi_wep);
			break;
		case SECURITY_WPA2_PSK:
			name = ctx.getString(R.string.wifi_wpa2);
			break;
		case SECURITY_WPA_WPA2_PSK:
			name = ctx.getString(R.string.wifi_wpa_wpa2);
			break;
		case SECURITY_WPA2_EAP:
			name = ctx.getString(R.string.wifi_802);
			break;
		case SECURITY_WPA2_PSK_USE_WPS:
			name = ctx.getString(R.string.wifi_wpa2_use_wps);
			break;
		default:
			break;
		}
		return name;
	}

	/**
	 * @Description: 将ip数字转换成可读的ip字符串
	 * @param @param ip
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String intIpToStringIp(int ip) {
		if (ip == 0)
			return null;
		return ((ip & 0xff) + "." + (ip >> 8 & 0xff) + "." + (ip >> 16 & 0xff) + "." + (ip >> 24 & 0xff));
	}

	/**点击正在使用的连接 返回 不保存 断开**/
	public static final int IN_USE_POINT_CLICK = 0;
	/**点击不在使用范围的连接 返回 不保存 修改**/
	public static final int NOT_IN_SCAPE_POINT_CLICK = 1;
	/**点击不在使用的连接，但是可用的  返回 不保存 连接 **/
	public static final int NOT_IN_USE_BUT_SAVED_POINT_CLICK = 2;

	public static void showActionDialog(final Context ctx, int type, int iconId, String title, String msg, String first_tag, OnClickListener first_listen, String second_tag, OnClickListener second_listen, String third_tag, OnClickListener third_listen) {
		Builder builder = new Builder(ctx)   //AlertDialog.THEME_DEVICE_DEFAULT_DARK
				.setIcon(iconId)
				.setTitle(title)
				.setMessage(msg)
				.setPositiveButton(first_tag, first_listen)
				.setNegativeButton(second_tag, second_listen);
		if (third_listen != null && !"".equals(third_tag)) {
			builder.setNeutralButton(third_tag, third_listen);
		}
		Dialog dialog = builder.create();
		dialog.show();
	}

	public static void showActionDialog(final Context ctx, int type, int iconId, String title, String msg, String first_tag, OnClickListener first_listen, String second_tag, OnClickListener second_listen) {
		showActionDialog(ctx, type, iconId, title, msg, first_tag, first_listen, second_tag, second_listen, "", null);
	}

	public static void showAlertDialog(Context ctx, String title, String message
			, String positiveText, OnClickListener positionListener
			, String neutraliveText, OnClickListener neutralListener
			, String negativeText, OnClickListener negativeListener) {

//		ZTEWifiConnectedDialogView.Builder builder = new ZTEWifiConnectedDialogView.Builder(ctx);
//		builder.setMessage(message);
//		builder.setTitle(title);
//		builder.setPositiveButton(positiveText, positionListener);
//		if (neutraliveText.length() > 0)
//			builder.setNeutralButton(neutraliveText, neutralListener);
//
//		builder.setNegativeButton(negativeText, negativeListener);
//		builder.create().show();

	}

	public static boolean isValidIp(String ip) {
		if (null == ip || "".equals(ip)) {
			return false;
		}
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	// public static ConnectType getSecurity(WifiConfiguration config) {
	// if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_PSK)) {
	// return ConnectType.WPA;
	// }
	// if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_EAP) ||
	// config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.IEEE8021X)) {
	// return KeyMgmt.SECURITY_EAP;
	// }
	// return (config.wepKeys[0] != null) ? KeyMgmt.SECURITY_WEP :
	// KeyMgmt.SECURITY_NONE;
	// }

}
