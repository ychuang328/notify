package com.weiwork.notify.model.base;

import cn.vko.common.base.BaseEntity;

/**
 * 短信消息
 * @author 微作
 */
 @SuppressWarnings("serial")
public class SmsBase extends BaseEntity {
	 
    /** 短信商提供的短信编号  */
    private String smsNo;
    /** 消息类型( 0：接收,1：发送) */
    private Integer type;
    /** 手机号 */
    private String mobile;
    /** 处理状态 (0:未处理 ,1：处理完毕) */
    private Integer status;
    /** 消息内容 */
    private String content;
    /** 回复内容 */
    private String reply;
    /** 创建时间 */
    private String cTime;

    public String getSmsNo() {
		return smsNo;
	}
	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getcTime() {
		return cTime;
	}
	public void setcTime(String cTime) {
		this.cTime = cTime;
	}
	
	@Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("smsNo:").append(this.getSmsNo()).append(",");
        strBuff.append("mobile:").append(this.getMobile()).append(",");
        strBuff.append("type:").append(this.getType()).append(",");
        strBuff.append("status:").append(this.getStatus()).append(",");
        strBuff.append("content:").append(this.getContent()).append(",");
        strBuff.append("reply:").append(this.getReply()).append(",");
        strBuff.append("cTime:").append(this.getcTime());
        strBuff.append("}");
        return strBuff.toString();
    }
}
