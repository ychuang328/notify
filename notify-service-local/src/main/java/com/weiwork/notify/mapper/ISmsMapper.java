package com.weiwork.notify.mapper;

import cn.vko.common.base.BaseMapper;

import com.weiwork.notify.model.Sms;

/**
 * Mapper接口.短信消息
 * @author 微课网
 */
public interface ISmsMapper extends BaseMapper<Sms> {

	Sms selectByLoginName(String loginName);  
}
