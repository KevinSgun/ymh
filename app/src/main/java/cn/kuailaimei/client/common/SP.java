package cn.kuailaimei.client.common;

import cn.kuailaimei.client.BeautApplication;

/**
 * Created by liurenhui on 15/3/27. SharedPreference 封装
 */
public class SP {

	public String spName;
	private SharedPreferenceManager spManager;

	/**
	 *
	 * @param name
	 *            作为key的前缀
	 */
	public SP(String name) {
		this.spName = name;
		spManager = SharedPreferenceManager.get(BeautApplication.getInstance());
	}

	public static SP getDefaultSP() {
		return new SP("Default_SP");
	}

	public static SP getSP(String spName) {
		return new SP(spName);
	}

	private String mappingKey(String key) {
		return spName + "_" + key;
	}

	public String getString(String key, String defaultValue) {
		return spManager.get(mappingKey(key), defaultValue);
	}

	public void putString(String key, String value) {
		spManager.put(mappingKey(key), value);
	}

	public void putInt(String key, int value) {
		spManager.put(mappingKey(key), value);
	}

	public int getInt(String key, int defaultValue) {
		return spManager.get(mappingKey(key), defaultValue);
	}

	public void putLong(String key, long value) {
		spManager.put(mappingKey(key), value);
	}

	public long getLong(String key, long defaultValue) {
		return spManager.get(mappingKey(key), defaultValue);
	}

	public void putFloat(String key, float value) {
		spManager.put(mappingKey(key), value);
	}

	public float getFloat(String key, float defaultValue) {
		return spManager.get(mappingKey(key), defaultValue);
	}

	public void putBoolean(String key, boolean value) {
		spManager.put(mappingKey(key), value);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return spManager.get(mappingKey(key), defaultValue);
	}

	public void remove(String key) {
		spManager.remove(mappingKey(key));
	}
	
}
