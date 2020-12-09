package org.springblade.auth.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.auth.props.AssistProperties;
import org.springblade.common.tool.HttpClient;
import org.springblade.common.tool.WeChatUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class PushMessage {

    private final static Logger logger = LoggerFactory.getLogger(PushMessage.class);

    //服务地址
    private static String serverPath;

    @Value("${serverPath}")
    public void setServerPath(String serverPath) {
        PushMessage.serverPath = serverPath;
    }

    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 消息模板推送
     * @param openId
     * @param helpNum （1、4、8人助力）（2-3、5-7人助力）
     * @param scanUserName 助力人名
     */
    public static void sendMessage(String openId, Integer helpNum, String scanUserName, String templateId) {
        if (helpNum == AssistProperties.NUMBER_C1 || helpNum == AssistProperties.NUMBER_C2 || helpNum == AssistProperties.NUMBER_GIFT) {
            sendMessage1(openId, helpNum, templateId);
        } else {
            sendMessage2(openId, helpNum , scanUserName, templateId);
        }
    }

    //消息模板推送  （1、4、8人助力）
    private static void sendMessage1(String openId, Integer helpNum, String templateId) {
        String url = "";
        if (helpNum == AssistProperties.NUMBER_C1) {
            String sign = "?buried=1&course=1&loginSource=3";
            url = serverPath + "/other/1" + sign;
        } else if (helpNum == AssistProperties.NUMBER_C2) {
            String sign = "?buried=1&course=2&loginSource=3";
            url = serverPath + "/other/2" + sign;
        } else {
            url = serverPath + "/take?loginSource=3";
        }
        String touser = openId;

        //任务内容
        String content = "";
        if (helpNum == AssistProperties.NUMBER_C1) {
            content = "领69.9元“千字文动画课”";
        } else if (helpNum == AssistProperties.NUMBER_C2) {
            content = "领79.9元“古诗词动画课”";
        } else {
            content = "领200元经典故事绘本套装";
        }

        String remark = "";
        if (helpNum == AssistProperties.NUMBER_C1) {
            remark = "6-12岁识字敏感期孩子必看！\n" +
                    "【识字、认字、知识全面拓展】\n" +
                    "点击此处，马上看课！\n\n" +
                    "还差【" + (AssistProperties.NUMBER_C2 - AssistProperties.NUMBER_C1) +"位】好友助力，即可免费领取【古诗词动画课】\n" +
                    "还差【" + (AssistProperties.NUMBER_GIFT - AssistProperties.NUMBER_C1) + "位】好友助力，即可免费领取【20册经典故事绘本】";
        } else if (helpNum == AssistProperties.NUMBER_C2) {
            remark = "趣味学习，陶冶诗词底蕴\n" +
                    "【轻松拿下小学古诗词】\n" +
                    "点击此处，马上看课！\n\n" +
                    "还差【" + (AssistProperties.NUMBER_GIFT - AssistProperties.NUMBER_C2) + "位】好友助力，即可免费领取【20册经典故事绘本】";
        } else {
            remark = "点击此处，马上填写地址\n" +
                    "全国包邮，先到先得\n\n" +
                    "活动结束后一个月内我们会根据领取顺序尽快发货！";
        }

        Map title = new HashMap();
        title.put("value", "恭喜你成功邀请" + helpNum + "位好友！");
        Map map = new HashMap();
        Map valueKeyword1 = new HashMap();
        valueKeyword1.put("value", content);
        Map valueKeyword2 = new HashMap();
        valueKeyword2.put("value", content);
        Map valueKeyword3 = new HashMap();
        valueKeyword3.put("value", sdf.format(new Date()));
        Map remarkMap = new HashMap();
        remarkMap.put("value", remark);
        remarkMap.put("color", "#ff0000");

        map.put("first", title);
        map.put("keyword1", valueKeyword1);
        map.put("keyword2", valueKeyword2);
        map.put("keyword3", valueKeyword3);
        map.put("remark", remarkMap);
        //向教师推送
//        String result = WeChatUtil.sendTemplateMessages(touser, weChatProperties.getHelpTemplate1(), url, map);
        String result = WeChatUtil.sendTemplateMessages(touser, templateId, url, map);
        logger.info(result);
    }

    //消息模板推送  （2-3、5-7人助力）
    private static void sendMessage2(String openId, Integer helpNum, String scanUserName, String templateId) {
        String url = serverPath + "/my?loginSource=3";
        String touser = openId;

        String content = "";
        if (helpNum > AssistProperties.NUMBER_C2) {
            content = "你已经收到了【" + helpNum + "位】好友的助力~\n\n" +
                    "还差【" + (AssistProperties.NUMBER_GIFT - helpNum) + "位】好友助力，即可免费领取【20册经典故事绘本】";
        } else {
            content = "你已经收到了【" + helpNum + "位】好友的助力~\n\n" +
                    "还差【" + (AssistProperties.NUMBER_C2 - helpNum) + "位】好友助力，即可免费领取【古诗词动画课】\n" +
                    "还差【" + (AssistProperties.NUMBER_GIFT - helpNum) + "位】好友助力，即可免费领取【20册经典故事绘本】";
        }

        Map title = new HashMap();
        title.put("value", "你有一位新的好友帮你助力啦！");
        Map map = new HashMap();
        Map valueKeyword1 = new HashMap();
        valueKeyword1.put("value", scanUserName);
        Map valueKeyword2 = new HashMap();
        valueKeyword2.put("value", sdf.format(new Date()));
        Map remark = new HashMap();
        remark.put("value", content);
        remark.put("color", "#ff0000");

        map.put("first", title);
        map.put("keyword1", valueKeyword1);
        map.put("keyword2", valueKeyword2);
        map.put("remark", remark);
        //向教师推送
//        String result = WeChatUtil.sendTemplateMessages(touser, weChatProperties.getHelpTemplate2(), url, map);
        String result = WeChatUtil.sendTemplateMessages(touser, templateId, url, map);
        logger.info(result);
    }


    /**
     * 少年说报名成功消息模板
     * @param openId
     */
    public static void sendSnsApplySuccessMessage(String openId, String name, String templateId) {
        String url = serverPath + "/sns/apply";
        Map title = new HashMap();
        title.put("value", "恭喜你成功报名“少年说”");
        Map keyword1 = new HashMap();
        keyword1.put("value", name);
        Map keyword2 = new HashMap();
        keyword2.put("value", sdf.format(new Date()));
        Map remark = new HashMap();
        remark.put("value", "点击可查看报名信息");

        Map map = new HashMap<>();
        map.put("first", title);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        map.put("remark", remark);
        WeChatUtil.sendTemplateMessages(openId, templateId, url, map);
    }

    /**
     *  关键字回复
     * @param accessToken
     * @param openId
     * @param stringBuffer 消息内容
     */
    public static void keyword(String accessToken, String openId, StringBuffer stringBuffer) {
        try {
            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + stringBuffer.toString() + "\"}}";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("grant_type", "client_credential");

            com.alibaba.fastjson.JSONObject paramInner = com.alibaba.fastjson.JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("关键字回复： ", e);
        }
    }
    

    public static JSONObject reply2(String accessToken, String openId,String nickname) {
    	JSONObject json = null;
    	try {
            StringBuffer sb = new StringBuffer();
            sb.append("🎉@"+nickname+" 恭喜你获得绘本0元领特权！还能免费赠好友一门好课！\n");
            sb.append("🎁价值【349元】20册中国经典故事绘本+国学启蒙动画课\n");
            sb.append("🉐限时【0元】领！ 全国包邮\n\n");
            sb.append("👉 领取步骤 👈 \n");
            sb.append("分享上方海报，邀请新人好友【扫码并关注公众号】助力 \n");
            sb.append("1️⃣人助力得【千字文动画课】 \n");
            sb.append("3️⃣人助力得【古诗词动画课】 \n");
            sb.append("6️⃣人助力得【20册故事绘本】 \n");
            sb.append("❗好友为您成功助力可无门槛获取【69.9元千字文动画课】\n\n");
            sb.append("🔥仅剩【300】套，领完即止");

            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("grant_type", "client_credential");

            com.alibaba.fastjson.JSONObject paramInner = com.alibaba.fastjson.JSONObject.parseObject(content);
            json =  HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, false);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("客服消息2报错： ", e);
        }
		return json;
    }


    //活动助力人数提示
    public static void sendAssistMessage(String openId, Integer helpNum, String scanUserName, Integer codeType, String helpName) {
        try {
            StringBuffer sb = new StringBuffer();
            if (helpNum == AssistProperties.NUMBER_AWARD) {
                sb.append(scanUserName + "为你助力成功，恭喜你成功获得【" + getAwardName(codeType) +"】\n");
                sb.append("\uD83D\uDC47添加下方书法老师，立即领取奖品吧！\n");
            } else {
//                sb.append("\uD83D\uDD14" + scanUserName + "为你助力成功，还差" + (AssistProperties.NUMBER_AWARD - helpNum)
//                        +"人就能拿【" + getAwardName(codeType) +"】");
                if (StringUtils.isNotBlank(helpName)) {
                    sb.append("@" + helpName +" 家长  ");
                }
                sb.append("您的好友 " + scanUserName +" 已经关注大象智教学堂");
            }
            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\"," + "\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            String accessToken = WeChatUtil.getAccessToken();
            JSONObject jsonObject = JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, jsonObject, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAwardName(Integer codeType) {
        String name = "";
        switch (codeType) {
            case 11 : name = "畅销儿童驼背矫正仪"; break;
            case 12 : name = "20册小学必读国学故事"; break;
            case 13 : name = "透气网棉双肩小学书包"; break;
            case 14 : name = "儿童智能矫正矫正带"; break;
        }
        return name;
    }

}
