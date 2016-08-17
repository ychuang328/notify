/**
 * SmsTypeEnum.java
 * cn.vko.ucenter.enums
 * Copyright (c) 2015, 北京微课创景教育科技有限公司版权所有.
*/

package com.weiwork.notify.enums;

/**
 * Sms消息类别
 * 
 * <p>
 * @author   ychuang328
 * @Date	 2015-11-17 	 
 */
public enum SmsTypeEnum {
	SEND(0, "发送"), REC(1, "接收");
	
	private Integer key;
	private String val;
	
	SmsTypeEnum(Integer key, String val){
		this.key=key;
		this.val=val;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
	
	/**
	 * 查询指定key的SmsTypeEnum
	 *
	 * @param key
	 * @return 指定key的SmsTypeEnum
	 */
	public static SmsTypeEnum getSmsTypeEnum(Integer key){
		for (SmsTypeEnum smsTypeEnum : SmsTypeEnum.values()) {
			if(smsTypeEnum.getKey().intValue() == key.intValue()){
				return smsTypeEnum;
			}
		}
		return null;
	}
}

