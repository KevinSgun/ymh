package com.zitech.framework;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.zitech.framework.utils.NetworkUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 
 * The Client Seesion Object for XiangLa client, contains some necessary
 * information.
 * 
 * @author Ludaiqian
 * 
 */
public class Session extends Observable {

	/** Application Context */
	private Context mContext;

	/** The version of OS */
	private int osVersion;

	/** The user login status */
	private boolean isLogin;

	/** The Application Version Code */
	private int versionCode;

	/** The Application package name */
	private String packageName;

	/** The Application version name */
	private String versionName;

	/** The Application version name */
	private String appName;

	/** GSM手机的 IMEI 和 CDMA手机的 MEID. */
	private String imei;

	private String imsi;
	/** SIM卡的序列号： */
	private String sim;

	/** The mobile mac address */
	private String macAddress;

	/** The user-visible version string. E.g., "1.0" */
	private String buildVersion;
	/**
	 * The mobile model such as "Nexus One" Attention: some model type may have
	 * illegal characters
	 */
	private String model;
	/** The singleton instance */

	private String androidId;
	private String phoneNumber;

	private List<NetworkChangedListener> networkChangedListeners;


	@SuppressLint("NewApi")
	Session(Context context) {
		osVersion = Build.VERSION.SDK_INT;
		buildVersion = Build.VERSION.RELEASE;
		try {
			model = URLEncoder.encode(Build.MODEL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		networkChangedListeners = new ArrayList<NetworkChangedListener>();
		try {

			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			context.registerReceiver(mReceiver, filter);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mContext = context;
	}

	// public ImageCache getImageCache() {
	// return bitmapCache;
	//
	// }

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				// synchronized (Session.this) {
				boolean networkAvailable = NetworkUtil.isNetworkAvailable(mContext);
				// }
				for (NetworkChangedListener networkChangedListener : networkChangedListeners) {
					networkChangedListener.onNetworkStateChanged(networkAvailable);
				}
			}
		}
	};

	public boolean isNetworkAvailable() {
		// 变量判定存在延迟
		// synchronized (this) {
		// return networkAvailable;
		// }
		return NetworkUtil.isNetworkAvailable(mContext);
	}

	public void addNetworkChangedListener(NetworkChangedListener networkChangedListener) {
		this.networkChangedListeners.add(networkChangedListener);
	}

	public void removeNetworkChangedListener(NetworkChangedListener networkChangedListener) {
		networkChangedListeners.remove(networkChangedListener);
	}

	public static interface NetworkChangedListener {
		public void onNetworkStateChanged(boolean networkAvailable);
	}

	public static Session getInstance() {
		// if (mInstance == null)
		// get(XianglaApplication.getInstance());
		return BaseApplication.getInstance().getSession();
	}

	public int getOsVersion() {
		return osVersion;
	}

	public String getBuildVersion() {
		return buildVersion;
	}

	private void getApplicationInfo() {

		final PackageManager pm = (PackageManager) mContext.getPackageManager();
		try {
			final PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
			versionName = pi.versionName;
			versionCode = pi.versionCode;

			final ApplicationInfo ai = pm.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);

			appName = String.valueOf(ai.loadLabel(pm));
			packageName = mContext.getPackageName();

			TelephonyManager telMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
			imei = telMgr.getDeviceId();
			imsi = telMgr.getSubscriberId();
			sim = telMgr.getSimSerialNumber();
			phoneNumber = telMgr.getLine1Number();
			androidId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
		} catch (NameNotFoundException e) {
			// Log.d(TAG, "met some error when get application info");
		}
	}

	public String getVersionName() {
		if (TextUtils.isEmpty(versionName)) {
			getApplicationInfo();
		}
		return versionName;
	}

	public int getVersionCode() {
		if (versionCode <= 0) {
			getApplicationInfo();
		}
		return versionCode;
	}

	public String getIMEI() {
		if (TextUtils.isEmpty(imei)) {
			getApplicationInfo();
		}
		return imei;
	}

	public String getPackageName() {
		if (TextUtils.isEmpty(packageName)) {
			getApplicationInfo();
		}
		return packageName;
	}

	public String getSim() {
		if (TextUtils.isEmpty(sim)) {
			getApplicationInfo();
		}
		return sim;
	}

	public String getIMSI() {
		if (TextUtils.isEmpty(imsi)) {
			getApplicationInfo();
		}
		return imsi;
	}

	/**
	 * 检索找到设备的Wi-Fi或蓝牙硬件的Mac地址。但是，不推荐使用Mac地址作为唯一的标识号。因为设备要具备Wi-Fi功能（并非所有的设备都有Wi-
	 * Fi功能） 如果设备目前正在使用Wi-Fi，则不能报告Mac地址
	 * 
	 * @return mac地址
	 */
	public String getMac() {
		if (TextUtils.isEmpty(macAddress)) {
			WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			macAddress = info.getMacAddress();
		}
		return macAddress;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public String getAppName() {
		return appName;
	}

	public String getModel() {
		return model;
	}

	/**
	 * GSM手机的 IMEI 和 CDMA手机的 MEID. Return null if device ID is not available.
	 * 
	 * @return 唯一的设备ID
	 */
	/**
	 * Settings.Secure.ANDROID_ID
	 * 是一串64位的编码（十六进制的字符串），是随机生成的设备的第一个引导，其记录着一个固定值，通过它可以知道设备的寿命
	 * （在设备恢复出厂设置后，该值可能会改变）。 ANDROID_ID也可视为作为唯一设备标识号的一个好选择。如要检索用于设备ID
	 * 的ANDROID_ID
	 * 
	 * <pre>
	 *  缺点
	 * • 对于Android 2.2（“Froyo”）之前的设备不是100％的可靠
	 * • 此外，在主流制造商的畅销手机中至少存在一个众所周知的错误，每一个实例都具有相同的ANDROID_ID。
	 * </pre>
	 * 
	 * @return
	 */
	public String getAndroidId() {
		if (androidId == null)
			getApplicationInfo();
		return androidId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	private String deviceId;

//	public String getDeviceId() {
//		if (TextUtils.isEmpty(deviceId))
//			deviceId = Utils.md5(getIMEI(), getMac(), getAndroidId());
//		return deviceId;
//	}

}