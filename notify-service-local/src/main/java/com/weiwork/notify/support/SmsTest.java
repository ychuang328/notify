package com.weiwork.notify.support;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试发短信
 * 
 * @author	杨闯
 * @date	2016-6-26
 */
public class SmsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String mobile, sms;

//		mobile = "13521212516";// 移动-张鹏飞
//		mobile = "13120378297";// 联通-张济超
//		mobile = "13321160953";// 电信-小畅
//		mobile = "18601086859";// 联通-运营
//		mobile = "13426479431";// 移动-丁辰叶
//		mobile = "18201013656";// 产品-老王
		mobile = "13051278175";// 联通-杨闯
		//		mobile = "18320086747,15613719410";

		sms = "【微课网】尊敬的用户，因苹果IOS9系统于9月16日上线推广，苹果公司开始对企业版应用软件进行调整，大批企业版平台发布的应用均受到影响。近期，持苹果手机的用户，在使用微课圈应用时，会出现无法下载、登录闪退等现象（安卓版不受影响），微课网正在与其沟通并按其流程解决此事，预计10天左右恢复使用，到时我们会即刻通知用户，对此给您带来的不便深表歉意";
		sms = "【微课网】1455，这是您的验证码";
		sms = "【微课网】您的验证码是1455";


				YunPianSms.send(sms, mobile);
		//		YunPianSms.voice("150913", mobile);

		//		sms = "http://m.vko.cn/reg.html?adid=1136174";
		//		System.out.println(sms.length());
		//		mobile = "12513625841";
		//		System.out.println(isMobile(mobile));
	}

	/**
	 * 校验是否是手机号
	 *
	 * @param mobile 待校验的手机号
	 * @return 符合手机号的规则为真
	 */
	public static boolean isMobile(final String mobile) {
		if (mobile == null) {
			return false;
		}
		String str = "^1[3,4,5,7,8][0-9]{9}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public static void qunfa(String sms) {
		List<String> mobileList = parseMobilesFromTxt();
		int size = mobileList.size();
		int per = 100;
		int time = size / per + 1;
		for (int i = 0; i < time; i++) {
			int start = per * i;
			int end = per * (i + 1);
			end = (end > size) ? size : end;

			String mbs = mobileList.subList(start, end).toString();
			mbs = mbs.replaceAll(", ", ",");
			mbs = mbs.substring(1, mbs.length() - 1);

			YunPianSms.send(sms, mbs);
			System.out.println("短信已发送：" + mbs);
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void danfa(String sms) {
		List<String> mobileList = parseMobilesFromTxt();
		for (String mb : mobileList) {
			YunPianSms.send(sms, mb);
			System.out.println("短信已发送：" + mb);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static List<String> parseMobilesFromTxt() {
		List<String> mobileList = new ArrayList<String>();
		BufferedReader r = null;
		try {
			r = new BufferedReader(new FileReader("d:/home/lost_user.txt"));
			String line = r.readLine();
			while (line != null) {
				//				mobileList.add(line);

				mobileList.addAll(Arrays.asList(line.split(",")));
				line = r.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (Exception e2) {
			}
		}

		return mobileList;
	}

}
