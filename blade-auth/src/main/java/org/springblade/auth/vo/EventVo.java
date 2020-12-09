package org.springblade.auth.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class EventVo {

	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private String event;
	private String eventKey;
	private String url;
	private String content;
	

	@XmlElement(name = "ToUserName") 
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	@XmlElement(name = "FromUserName") 
	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	@XmlElement(name = "CreateTime") 
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@XmlElement(name = "MsgType") 
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@XmlElement(name = "Event") 
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@XmlElement(name = "EventKey") 
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	@XmlElement(name = "URL") 
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@XmlElement(name = "Content") 
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
