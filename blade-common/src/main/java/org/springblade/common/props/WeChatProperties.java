package org.springblade.common.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="wechat.config")
public class WeChatProperties {

    private String appId;

    private String appSecret;

    private String accessTokenUrl;

    private String userInfoUrl;

    //小程序 appId
    private String xcxAppId;
    //小程序 appSecret
    private String xcxAppSecret;

    private String xcxGrantType;

    private String jcode2SessionUrl;

}
