package com.weiwork.notify.support;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class SmsDieFengUtil {

	private static final Logger log = LoggerFactory.getLogger(SmsDieFengUtil.class);

	private static final String snedUrl = "http://114.215.130.61:8082/SendMT/SendMessage";
	private static final String userName = "weike";
	private static final String UTF8 = "UTF-8";
	private static final String GBK = "GBK";
	private static final String password = "vko2015.cn";
	private static final Map<String, String> MSG_ERROR = Maps.newHashMap();
	static {
		MSG_ERROR.put("00", "多个手机号请求发送成功");
		MSG_ERROR.put("02", "IP限制");
		MSG_ERROR.put("03", "单个手机号请求发送成功 ");
		MSG_ERROR.put("04", "用户名错误   ");
		MSG_ERROR.put("05", "密码错误   ");
		MSG_ERROR.put("06", "编码参数 ");
		MSG_ERROR.put("07", "发送时间有误");
		MSG_ERROR.put("08", "参数错误  ");
		MSG_ERROR.put("09", "手机号有误 ");
		MSG_ERROR.put("10", "扩展号码有误 ");
		MSG_ERROR.put("11", "余额不足 ");
		MSG_ERROR.put("-1", "服务器内部异常");
		MSG_ERROR.put("REJECT", "非法消息内容 ");
	}

	public static void main(String[] arg) {
		try {
			String url = "http://m.vko.cn/reg.html?adid=1234567";

			String content = "【微课网】欢迎万晓生同学成为微课圈的新用户。微课圈将免费为您提供课程、测试、答疑、专题全方位帮助，竭尽全力帮您迅速提高学习成绩！我们用最热情的怀抱欢迎您的加入！微课圈下载地址："
					+ url;

			System.out.println(content.length());
			System.out.println(content);

			String m1 = "13426479431";
			try {
				System.out.println("结果：" + send(content, new String[] { "13426479431", "" }));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public static String send(String content, String[] mobile) {
		try {
			return SmsDieFengUtil.httpGetSend(URLEncoder.encode(content, UTF8), join(mobile), "", UTF8);
		} catch (Exception e) {
			log.error("碟信发送短信出错：" + e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public static String getSendInfo(String result) {
		return MSG_ERROR.get(result.split(",")[0]);
	}

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

	@Deprecated
	public static String send(String content, String mobile) throws UnsupportedEncodingException {
		return SmsDieFengUtil.httpGetSend(URLEncoder.encode(content, UTF8), mobile, "", UTF8);
	}

	public static String httpGetSend(String content, String mobile, String Subid, String encoded) {
		String urlPath = snedUrl + "?" + "UserName=" + userName + "&UserPass=" + password + "&Content=" + content
				+ "&Mobile=" + mobile + "&Subid=" + Subid;
		StringBuffer sbf = new StringBuffer("");
		BufferedReader reader = null;
		HttpURLConnection uc = null;

		try {
			URL url = new URL(urlPath);
			uc = (HttpURLConnection) url.openConnection();
			uc.setConnectTimeout(30000);
			uc.setReadTimeout(30000);
			uc.setRequestMethod("GET");
			uc.setDoOutput(true);
			uc.setDoInput(true);

			uc.connect();
			reader = new BufferedReader(new InputStreamReader(uc.getInputStream())); // 读取服务器响应信息
			String line;
			while ((line = reader.readLine()) != null) {
				sbf.append(line);
			}
			reader.close();
			uc.disconnect();
			return sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
}
