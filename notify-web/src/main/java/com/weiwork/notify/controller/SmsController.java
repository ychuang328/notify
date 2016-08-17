package com.weiwork.notify.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weiwork.notify.form.SmsForm;
import com.weiwork.notify.service.impl.ISmsService;

/**
 * 注册.控制器
 * @author 微课网
 */
@Controller
@RequestMapping("/sms")
public class SmsController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
	private ISmsService smsService;
    
    /**
     * 短信首页
     * @param model
     * @return
     */
	@RequestMapping("")
	public String index(Model model,HttpServletResponse response,HttpServletRequest request){
		
		return "sms/sms";
	}
	

	/**
	 * 发送短信
	 *
	 * @param mobile 手机号
	 * @param content 需要发送的内容（需要符合模板格式：如：发送验证码：您的验证码是+验证码）
	 * @return 返回发送结果
	 */
	@RequestMapping("send")
	@ResponseBody
	public void send(HttpServletResponse response,HttpServletRequest request, @ModelAttribute final SmsForm form) {
		writeJsonp(response, smsService.send(form.getContent(), form.getMobile()), form.getCallback());
	}
}
