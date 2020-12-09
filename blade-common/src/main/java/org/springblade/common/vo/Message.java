package org.springblade.common.vo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class Message {

	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private String content;
	private ImageVo image;
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

	@XmlElement(name = "Content") 
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@XmlElement(name = "Image") 
	public ImageVo getImage() {
		return image;
	}

	public void setImage(ImageVo image) {
		this.image = image;
	}


	
	
	
}
