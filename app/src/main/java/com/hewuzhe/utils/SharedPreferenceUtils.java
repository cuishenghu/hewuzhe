package com.hewuzhe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Administrator �����û���Ϣ���䱾��
 */
public class SharedPreferenceUtils {

	private static SharedPreferenceUtils mUtil;
	private static final String PREFERENCE_NAME = "_ZYKJMJ";
	private static SharedPreferences mSharedPreference;
	private SharedPreferences.Editor mEditor;
	private static final String DEVICEID = "deviceId";
	private static final String SEX = "sex";
	private static final String BIRTH = "birth";
	private static final String PROFESSION = "profession";
	private static final String USERID = "userid";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String AVATAR = "avatar";
	private static final String MOBILE = "mobile";
	private static final String MONEY = "money";
	private static final String INTEGRAL = "integral";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String SIGN = "sign";

	private SharedPreferenceUtils(Context context) {
		mSharedPreference = context.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
		mEditor = mSharedPreference.edit();
	}

	public static synchronized SharedPreferenceUtils init(Context context) {
		if (mUtil == null) {
			mUtil = new SharedPreferenceUtils(context);
		}
		return mUtil;
	}

	public String getDeviceId() {
		return mSharedPreference.getString(DEVICEID, null);
	}

	public String getSex() {
		return mSharedPreference.getString(SEX, null);
	}

	public String getBirth() {
		return mSharedPreference.getString(BIRTH, null);
	}

	public String getPrefession() {
		return mSharedPreference.getString(PROFESSION, null);
	}

	public String getUserid() {
		return mSharedPreference.getString(USERID, null);
	}

	public String getUsername() {
		return mSharedPreference.getString(USERNAME, null);
	}

	public String getPassword() {
		return mSharedPreference.getString(PASSWORD, null);
	}

	public String getAvatar() {
		return mSharedPreference.getString(AVATAR, null);
	}

	public String getMobile() {
		return mSharedPreference.getString(MOBILE, null);
	}

	public String getMoney() {
		return mSharedPreference.getString(MONEY, null);
	}

	public String getIntegral() {
		return mSharedPreference.getString(INTEGRAL, null);
	}

	public String getLatitude() {
		return mSharedPreference.getString(LATITUDE, null);
	}

	public String getLongitude() {
		return mSharedPreference.getString(LONGITUDE, null);
	}

	public String getSign() {
		return mSharedPreference.getString(SIGN, null);
	}
	
	public String getIsNew() {
		return mSharedPreference.getString("isNew", null);
	}
	
	public String getIsOver() {
		return mSharedPreference.getString("isOver", null);
	}
	
	public String getIsNewFirst() {
		return mSharedPreference.getString("isNewFirst", null);
	}
	
	public void setIsNewFirst(String isNewFirst) {
		mEditor.putString("isNewFirst", isNewFirst);
		mEditor.commit();
	}
	
	public void setIsOver(String isOver) {
		mEditor.putString("isOver", isOver);
		mEditor.commit();
	}
	
	public void setIsNew(String isNew) {
		mEditor.putString("isNew", isNew);
		mEditor.commit();
	}

	public void setSex(String sex) {
		mEditor.putString(SEX, sex);
		mEditor.commit();
	}

	public void setBirth(String birth) {
		mEditor.putString(BIRTH, birth);
		mEditor.commit();
	}

	public void setPrefession(String prefession) {
		mEditor.putString(PROFESSION, prefession);
		mEditor.commit();
	}

	public void setDeviceId(String deviceId) {
		mEditor.putString(DEVICEID, deviceId);
		mEditor.commit();
	}

	public void setUserid(String userid) {
		mEditor.putString(USERID, userid);
		mEditor.commit();
	}

	public void setUsername(String username) {
		mEditor.putString(USERNAME, username);
		mEditor.commit();
	}

	public void setPassword(String password) {
		mEditor.putString(PASSWORD, password);
		mEditor.commit();
	}

	public void setAvatar(String avatar) {
		mEditor.putString(AVATAR, avatar);
		mEditor.commit();
	}

	public void setMobile(String mobile) {
		mEditor.putString(MOBILE, mobile);
		mEditor.commit();
	}

	public void setMoney(String money) {
		mEditor.putString(MONEY, money);
		mEditor.commit();
	}

	public void setIntegral(String integral) {
		mEditor.putString(INTEGRAL, integral);
		mEditor.commit();
	}

	public void setLatitude(String latitude) {
		mEditor.putString(LATITUDE, latitude);
		mEditor.commit();
	}

	public void setLongitude(String longitude) {
		mEditor.putString(LONGITUDE, longitude);
		mEditor.commit();
	}

	public void setSign(String sign) {
		mEditor.putString(SIGN, sign);
		mEditor.commit();
	}

	public void clear() {
		mEditor.clear();
	}
}
