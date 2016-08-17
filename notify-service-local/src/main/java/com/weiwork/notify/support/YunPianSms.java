package com.weiwork.notify.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.vko.common.utils.http.HttpParam;
import cn.vko.common.utils.http.HttpUtil;

/**
 * 云片网络-发送短信接口
 * 
 * @author	丁辰叶
 * @date	2015-9-13
 */
public class YunPianSms {

	private static final Logger log = LoggerFactory.getLogger(YunPianSms.class);

	// 接口在线说明：http://www.yunpian.com/4.0/api/sms.html

	// 接口调用签名
	private static final String API_KEY = "75f44c72a85a688e7882ce0e94b22b26";
	// 短信接口地址
	private static final String API_SMS = "http://yunpian.com/v1/sms/send.json";
	// 语音接口地址
	private static final String API_VOICE = "http://voice.yunpian.com/v1/voice/send.json";

	/**
	 * 发送短信
	 * 
	 * @param text 短信内容
	 * @param mobile 手机号
	 */
	public static String send(String text, final String... mobiles) {
		String json = "";
		HttpParam hp = HttpParam.init().addCommon("apikey", API_KEY);
		hp.addCommon("mobile", join(mobiles)).addCommon("text", text);
		try {
			json = HttpUtil.sendPost(API_SMS, hp);
		} catch (Exception e) {
			log.error("云片网-短信发送失败：" + e);
		}
		return json;
	}

	/**
	 * 发送语音短信
	 * 
	 * @param code 验证码
	 * @param mobile 手机号
	 * @return
	 */
	public static String voice(String code, String mobile) {
		String json = "";
		HttpParam hp = HttpParam.init().addCommon("apikey", API_KEY);
		hp.addCommon("mobile", mobile).addCommon("code", code);
		try {
			json = HttpUtil.sendPost(API_VOICE, hp);
		} catch (Exception e) {
			log.error("云片网-语音短信发送失败：" + e);
		}
		return json;
	}

	/**
	 * 以逗号分隔拼接手机号
	 * 
	 * @param mobile 手机号数组
	 * @return
	 */
	private static String join(String[] mobile) {
		if (null == mobile || mobile.length <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mobile.length; i++) {
			sb.append(mobile[i]).append(",");
		}

		return sb.substring(0, sb.lastIndexOf(","));
	}

}
