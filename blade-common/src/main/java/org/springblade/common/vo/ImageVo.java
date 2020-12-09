package org.springblade.common.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class ImageVo {

		private String mediaId;

		@XmlElement(name = "MediaId") 
		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
	}
}
