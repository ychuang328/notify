package com.weiwork.notify.service.remote;

import java.io.UnsupportedEncodingException;

/**
 * @Description:service接口.短信消息
 * @author: 微作
 */
public interface ISmsServiceRemote {
	
	/**
	 * 给用户发送相同短信接口
	 *
	 * @param content 需要发送的内容（需要符合模板格式：如：发送验证码：您的验证码是+验证码）
	 * @param mobiles 手机号
	 * @return json串   {"code":0,"msg":"OK","result":{"count":1,"fee":1,"sid":3818981384}}
	 * @throws UnsupportedEncodingException 
	 */
	public String sendByRemote(final String content, final String... mobiles) ;
}

