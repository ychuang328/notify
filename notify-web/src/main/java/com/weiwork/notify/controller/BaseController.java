package com.weiwork.notify.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.vko.common.base.JsonMsg;
import cn.vko.common.utils.StringEscapeEditor;
import cn.vko.common.utils.json.JsonHelper;
import cn.vko.component.pageframework.pagination.Pagination;

/**
 * 所有控制器的父类
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-2
 */
@SuppressWarnings("rawtypes")
public abstract class BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final String UPLOAD_PATH = "/upload";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new StringEscapeEditor(false, true, true));
	}

	public String createClassNo() {
		return DateFormatUtils.format(new Date(), "yyyyMMddhhmmss");
	}

	public void writeJson(HttpServletResponse response, Pagination p) {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//禁止循环生产JSON
		
		String result =JsonHelper.object2JsonStr(p) ;

		response.setContentType("aplication/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			this.logger.error(e.getMessage());
		}
	}
	
	/**
	 * 重载writeJson方法，传入任何对象直接返回前端json字符串
	 * @param obj
	 * @return
	 */
	public String writeJson(Object obj){
		return JsonHelper.object2JsonStr(obj);
	}

	/**
	 * ajax - jsonp 兼容jsonp为空的情况
	 * @param response
	 * @param msg
	 * @param jsonp
	 */
	public void writeJsonp(HttpServletResponse response, JsonMsg msg, String jsonp) {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//禁止循环生产JSON
		JSONObject json = JSONObject.fromObject(msg, config);
		String result = json.toString();
		
		if(StringUtils.isBlank(jsonp)){
			writeContext(response, result);
			return ;
		}
		response.setContentType("aplication/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonp + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			this.logger.error(e.getMessage());
		}
	}

	/**
	 * ajax - jsonp 兼容jsonp为空的情况
	 * @param response
	 * @param msg
	 * @param jsonp
	 */
	public void writeJsonp(HttpServletResponse response, Object obj, String jsonp) {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//禁止循环生产JSON
		JSONObject json = JSONObject.fromObject(obj, config);
		String result = json.toString();

		if(StringUtils.isBlank(jsonp)){
			writeContext(response, result);
			return ;
		}
		response.setContentType("aplication/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonp + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			this.logger.error(e.getMessage());
		}
	}

	public void writeContext(HttpServletResponse response, String context) {
		response.setContentType("aplication/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(context);
			out.flush();
			out.close();
		} catch (IOException e) {
			this.logger.error(e.getMessage());
		}
	}

	public void writTemplate(String tempName, Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/plain; charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");

			Properties p = new Properties();
			//p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			p.put("input.encoding", "UTF-8");
			p.put("output.encoding", "UTF-8");
			p.put("file.resource.loader.path", request.getSession().getServletContext().getRealPath("/")
					+ "/WEB-INF/template/");

			VelocityEngine engine = new VelocityEngine();
			engine.init(p);

			Template template = engine.getTemplate(tempName, "UTF-8");
			VelocityContext context = new VelocityContext();

			if (map != null) {
				Set<String> set = map.keySet();
				for (String s : set) {
					context.put(s, map.get(s));
				}
			}

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"));
			template.merge(context, pw);
			pw.close();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}

	//获取用户Id
	public Long getUserId(HttpServletRequest request) {
//		LoginUser u = getLoginUser(request);
//		if (u != null && u.getId() != null && u.getId() > 0) {
//			return u.getId();
//		}
		return -1L;
	}
/*
	//获取用户
	public LoginUser getLoginUser(HttpServletRequest request) {
		try {
			SsoFetchUser.setContext(new SsoContext(request));
			return SsoFetchUser.getCurrentLoginUser();
		}
		catch (Exception e) {
			return null;
		}
	}
	*/
	//获取cookie
	public String getCookie(Cookie[] cookies,String cookieName){
		if(cookies != null && cookies.length >0){
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().trim().equals(cookieName)){
					
					String value = cookies[i].getValue();
					try {
						return  URLDecoder.decode(value, "utf-8");
						
					} catch (UnsupportedEncodingException e) {
						logger.error("urlDecoder:不支持的编码格式>",e);
					}
				}
			}
		}
		return null;
	}
	
	//删除cookie
	public void removeCookie(String cookieName,HttpServletResponse response){
		Cookie cookie = new Cookie(cookieName, null); 
		cookie.setMaxAge(-1);
		cookie.setDomain(".vko.cn");
		cookie.setPath("/");
		response.addCookie(cookie); 
	}
	
	//添加cookie
	public void addCookie(String cookieName,String cookieValue,HttpServletResponse response){
		Cookie cookie = new Cookie(cookieName, cookieValue); 
		cookie.setDomain(".vko.cn");
		cookie.setPath("/");
		response.addCookie(cookie); 
	}
}
