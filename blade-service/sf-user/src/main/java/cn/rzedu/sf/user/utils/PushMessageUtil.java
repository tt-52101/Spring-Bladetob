package cn.rzedu.sf.user.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void sendSnsApplySuccessMessage(String openId, String name, String mobile, String templateId) {
        String url = serverPath + "/enroll";
        Map title = new HashMap();
        title.put("value", "恭喜您成功报名《少年说》");
        Map keyword1 = new HashMap();
        keyword1.put("value", "《少年说》");
        Map keyword2 = new HashMap();
        keyword2.put("value", name);
        Map keyword3 = new HashMap();
        keyword3.put("value", mobile);
        Map keyword4 = new HashMap();
        keyword4.put("value", sdf.format(new Date()));
        Map remark = new HashMap();
        remark.put("value", "您可点击【详情】查看详细报名信息");

        Map map = new HashMap<>();
        map.put("first", title);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        map.put("keyword3", keyword3);
        map.put("keyword4", keyword4);
        map.put("remark", remark);
        WeChatUtil.sendSNSTemplateMessages(openId, templateId, url, map);
    }

}
