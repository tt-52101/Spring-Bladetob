package cn.rzedu.sc.goods.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.common.props.WeChatProperties;
import org.springblade.common.tool.HttpClient;
import org.springblade.common.tool.WeChatUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class PushMessageUtil {

    private final static Logger logger = LoggerFactory.getLogger(PushMessageUtil.class);

    /** 服务地址 */
    private static String serverPath;


    @Value("${serverPath}")
    public void setServerPath(String serverPath) {
        PushMessageUtil.serverPath = serverPath;
    }

    /** 发送消息的接口地址 */
    public static final String WX_SEND_MESSAGES_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void sendJoinGrouponMessage(String templateId, String openId, String name, String courseName,
                                       Integer grouponId, Integer grouponGroupId) {
        String url = serverPath +"/group?groupId=" + grouponGroupId +"&isMakeUp=2&id=" + grouponId + "&loginSource=3";
//        String templateId = weChatProperties.getGrouponJoinTemplate();

        Map title = new HashMap();
        title.put("value", "您有一位朋友加入您的《" + courseName + "》的拼团啦！");
        Map keyword1 = new HashMap();
        keyword1.put("value", name);
        Map keyword2 = new HashMap();
        keyword2.put("value", sdf.format(new Date()));
        Map remark = new HashMap();
        remark.put("value", "还差1人即可拼团成功，点击此处，立即查询课程拼团进度");
        remark.put("color", "#41598E");

        Map map = new HashMap<>();
        map.put("first", title);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        map.put("remark", remark);
        WeChatUtil.sendTemplateMessages(openId, templateId, url, map);
    }

    public static void sendFinishGrouponMessage(String templateId, String openId, String name, String courseName, String price) {
        String url = serverPath + "/my?loginSource=3";
//        String templateId = weChatProperties.getGrouponSuccessTemplate();

        Map title = new HashMap();
        title.put("value", "恭喜恭喜！最后一名团员【" + name + "】已加入！您已获得上课资格啦");
        Map keyword1 = new HashMap();
        keyword1.put("value", courseName);
        Map keyword2 = new HashMap();
        keyword2.put("value", price);
        Map keyword3 = new HashMap();
        keyword3.put("value", "成功拼团！点击此处马上去上课！");
        keyword3.put("color", "#41598E");
//        Map remark = new HashMap();
//        remark.put("value", "点击这里去上课，了解上课须知");
//        remark.put("color", "#FF0000");

        Map map = new HashMap<>();
        map.put("first", title);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        map.put("keyword3", keyword3);
//        map.put("remark", remark);
        WeChatUtil.sendTemplateMessages(openId, templateId, url, map);
    }

    public static void sendGrouponMemberLackMessage(String templateId, String openId, String name, String courseName,
                                                     Integer leftCount, Integer grouponId, Integer grouponGroupId) {
        String url = serverPath +"/group?groupId=" + grouponGroupId +"&isMakeUp=2&id=" + grouponId + "&loginSource=3";
//        String templateId = weChatProperties.getGrouponMemberLackTemplate();

        Map title = new HashMap();
        title.put("value", "您拼团的课程《" + courseName + "》还差" + leftCount + "人拼团成功，成功后才有上课资格哦~");
        Map keyword1 = new HashMap();
        keyword1.put("value", courseName);
        Map keyword2 = new HashMap();
        keyword2.put("value", name);
        Map remark = new HashMap();
        remark.put("value", "点击此处，转发家长好友群或朋友圈，成功机会提升100%~");
        remark.put("color", "#41598E");

        Map map = new HashMap<>();
        map.put("first", title);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        map.put("remark", remark);
        WeChatUtil.sendTemplateMessages(openId, templateId, url, map);
    }

    /**
     * 发送消息
     * @param openId
     * @param stringBuffer  消息内容
     */
    public static void sendMessage(String openId, StringBuffer stringBuffer) {
        try {
            if (StringUtils.isBlank(openId)) {
                System.out.println("------openId为空，无法发送消息------");
                return;
            }
            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + stringBuffer.toString() + "\"}}";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("grant_type", "client_credential");

            JSONObject paramInner = JSONObject.parseObject(content);
            String accessToken = WeChatUtil.getAccessToken();
            HttpClient.httpPost(WX_SEND_MESSAGES_URL.replace("ACCESS_TOKEN", accessToken), paramInner, true);
        } catch (Exception e) {
            logger.error("拼团消息回复： ", e);
        }
    }

}
