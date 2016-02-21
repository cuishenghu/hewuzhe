package com.hewuzhe.model;

import com.hewuzhe.utils.SharedPreferenceUtils;

import android.content.Context;

public class AppModel {

	/**
	 * 当前帐号是否已经登录的标识
	 */
	public static boolean is_login = false;
	public static String NAME = "name";
	public static String PHONE = "phone";

	private String deviceId;// 设备ID
	private String username;// 登录账号
	private String password;// 登录密码
	private String userid;// 用户Id
	private String avatar;// 头像
	private String mobile;// 手机
	private String money;// 我的钱包
	private String integral;// 积分
	private String latitude;// 经度
	private String longitude;// 纬度
	private String sign;
	private String sex;// 性别
	private String birth;// 生日
	private String profession;// 职业

	private static SharedPreferenceUtils utils;

	public static AppModel init(Context context) {
		AppModel model = new AppModel();
		utils = SharedPreferenceUtils.init(context);

		if (utils.getDeviceId() != null) {
			model.deviceId = utils.getDeviceId();
		}
		if (utils.getUsername() != null) {
			model.username = utils.getUsername();
		}
		if (utils.getBirth() != null) {
			model.birth = utils.getBirth();
		}
		if (utils.getSex() != null) {
			model.sex = utils.getSex();
		}
		if (utils.getPrefession() != null) {
			model.profession = utils.getPrefession();
		}
		if (utils.getPassword() != null) {
			model.password = utils.getPassword();
		}

		if (utils.getUserid() != null) {
			model.userid = utils.getUserid();
		}

		if (utils.getAvatar() != null) {
			model.avatar = utils.getAvatar();
		}

		if (utils.getMobile() != null) {
			model.mobile = utils.getMobile();
		}

		if (utils.getMoney() != null) {
			model.money = utils.getMoney();
		}

		if (utils.getIntegral() != null) {
			model.integral = utils.getIntegral();
		}

		if (utils.getLatitude() != null) {
			model.latitude = utils.getLatitude();
		}

		if (utils.getLongitude() != null) {
			model.longitude = utils.getLongitude();
		}

		if (utils.getSign() != null) {
			model.sign = utils.getSign();
		}
		return model;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
		utils.setDeviceId(deviceId);
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
		utils.setSex(sex);
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
		utils.setBirth(birth);
	}

	public String getPrefession() {
		return profession;
	}

	public void setPrefession(String prefession) {
		this.profession = prefession;
		utils.setPrefession(prefession);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		utils.setUsername(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		utils.setPassword(password);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
		utils.setUserid(userid);
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
		utils.setAvatar(avatar);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
		utils.setMobile(mobile);
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
		utils.setMoney(money);
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
		utils.setIntegral(integral);
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void clear() {
		this.setDeviceId("");
		this.setUsername("");
		this.setPassword("");
		this.setUserid("");
		this.setAvatar("");
		this.setMobile("");
		this.setMoney("");
		this.setIntegral("");
		utils.clear();
	}
}
