package com.weiwork.notify.form;

import java.io.Serializable;

/**
 * 发送短信的form信息
 * 
 * @author   杨闯
 * @Date	 2016-3-23 	 
 */
public class SmsForm implements Serializable {
	private static final long serialVersionUID = 5075954087390863635L;
	
	private String[] mobile; // 手机号
	private String content; // 需要发送的内容（需要符合模板格式：如：发送验证码：您的验证码是+验证码）
	private String callback; //jsonp
	
	public String[] getMobile() {
		return mobile;
	}
	public void setMobile(String[] mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
}

