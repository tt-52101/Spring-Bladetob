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

    private String helpTemplate1;

    private String helpTemplate2;
    //客服素材id
    private String kFmediaId;
    
    //新海报
    private String newPosterId;
    
    private String successTemplate;

    /** 拼团成员加入提醒 */
    private String grouponJoinTemplate;
    /** 拼团成功通知 */
    private String grouponSuccessTemplate;
    /** 参团人数不足提醒 */
    private String grouponMemberLackTemplate;
    /** 少年说报名公众号appId */
    private String snsAppId;
    /** 少年说报名公众号appSecret */
    private String snsAppSecret;
    
    private String zSmediaId;

    private String LSmediaId;
    
    private String teacherTemplateId;

    //小程序 appId
    private String xcxAppId;
    //小程序 appSecret
    private String xcxAppSecret;

}
