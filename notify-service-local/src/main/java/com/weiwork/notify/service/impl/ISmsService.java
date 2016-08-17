package com.weiwork.notify.service.impl;

import java.io.UnsupportedEncodingException;

import cn.vko.common.base.BaseService;

import com.weiwork.notify.mapper.ISmsMapper;
import com.weiwork.notify.model.Sms;

/**
 * @Description:service接口.短信消息
 * @author: 微课网
 */
public interface ISmsService  extends BaseService<Sms,ISmsMapper>{
	
	/**
	 * 给用户发送相同短信
	 *
	 * @param content 内容
	 * @param mobiles 手机号
	 * @return json串   {"code":0,"msg":"OK","result":{"count":1,"fee":1,"sid":3818981384}}
	 * @throws UnsupportedEncodingException 
	 */
	public String send(final String content, final String... mobiles) ;
}

