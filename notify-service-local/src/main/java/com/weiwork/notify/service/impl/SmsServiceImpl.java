package com.weiwork.notify.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.vko.common.base.impl.BaseServiceImpl;

import com.google.common.base.Strings;
import com.weiwork.notify.enums.SmsTypeEnum;
import com.weiwork.notify.mapper.ISmsMapper;
import com.weiwork.notify.model.Sms;
import com.weiwork.notify.service.remote.ISmsServiceRemote;
import com.weiwork.notify.support.YunPianSms;

/**
 * service接口实现.短信消息
 * @author 微课网
 */
@Service
public class SmsServiceImpl extends BaseServiceImpl<Sms,ISmsMapper> implements ISmsService, ISmsServiceRemote {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private ISmsMapper smsMapper;
	
	@Override
	public ISmsMapper getDefaulteMapper() {
		return smsMapper;
	}

	/**
	 * 给用户发送相同短信
	 *
	 * @param content 内容
	 * @param mobiles 手机号
	 * @throws UnsupportedEncodingException 
	 */
	public String send(final String content, final String... mobiles) {
		logger.info("send starting mobiles {} , content {} ", mobiles, content);
		String sms = "【微课网】" + content;
		String rt = ""; // 短信网关响应内容
		//会自动判断收好为空的情况
		rt = YunPianSms.send(sms, mobiles);
		this.logger.info("使用【云片】短信接口发送短信，手机号[" + ArrayUtils.toString(mobiles) + "];结果={}", rt);

		String cTime = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss");
		for (String mobile : mobiles) {
			Sms asms = new Sms();
			asms.setContent(sms);
			asms.setMobile(mobile);
			asms.setcTime(cTime);
			asms.setType(SmsTypeEnum.SEND.getKey());
			smsMapper.insert(asms);
		}
		logger.info("send result mobiles {} , content {} , result {} ", mobiles, content, rt);
		return rt;
	}

	/**
	 * 给用户发送语音验证码
	 * 
	 * @param code 验证码
	 * @param mobile 手机号
	 */
	public void sendVoice(final String code, final String mobile) {
		if (Strings.isNullOrEmpty(code) || Strings.isNullOrEmpty(mobile)) {
			this.logger.info("发送语音验证码，验证码或手机号空了:code=" + code + ",mobile=" + mobile);
			return;
		}

		String rt = YunPianSms.voice(code, mobile);
		this.logger.info("使用【云片】语音接口通知用户，手机号[" + mobile + "];结果={}", rt);
		String cTime = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss");
		Sms asms = new Sms();
		asms.setContent("语音验证码：" + code);
		asms.setMobile(mobile);
		asms.setcTime(cTime);
		asms.setType(SmsTypeEnum.SEND.getKey());
		smsMapper.insert(asms);
	}
	
	/***************************dubbo 远程接口实现 start **************************/

	@Override
	public String sendByRemote(String content, String... mobiles) {
		return this.send(content, mobiles);
	}
	
	/***************************dubbo 远程接口实现  end **************************/
	
}
