/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.auth.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.rzedu.sc.goods.feign.IGrouponClient;
import cn.rzedu.sf.user.vo.SnsUserVO;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.auth.props.AssistProperties;
import org.springblade.auth.utils.HttpUtils;
import org.springblade.auth.utils.PushMessage;
import org.springblade.auth.utils.SignUtil;
import org.springblade.auth.vo.ArticleJSON;
import org.springblade.auth.vo.Message;
import org.springblade.auth.vo.NewsJSONN;
import org.springblade.auth.vo.NewsMessageJSON;
import org.springblade.common.props.WeChatProperties;
import org.springblade.common.tool.HttpClient;
import org.springblade.common.tool.JSSignUtil;
import org.springblade.common.tool.WeChatUtil;
import org.springblade.common.vo.EventVo;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.producer.feign.TopicClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rzedu.sf.activity.feign.ActivityClient;
import cn.rzedu.sf.resource.feign.ITextbookClient;
import cn.rzedu.sf.resource.vo.CharLinkVO;
import cn.rzedu.sf.user.feign.IActivityClient;
import cn.rzedu.sf.user.feign.ISFUserClient;
import cn.rzedu.sf.user.feign.IUserCourseClient;
import cn.rzedu.sf.user.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 认证模块
 *
 * @author Chill
 */
@Controller
@RequiredArgsConstructor
@Api(value = "微信认证", tags = "授权接口")
@RefreshScope
public class WechatController {

    private final static Logger logger = LoggerFactory.getLogger(WechatController.class);
    private final ITextbookClient textbookClient;
    private final WeChatProperties weChatProperties;
    private final ISFUserClient userClient;
    //活动埋点
    private final IActivityClient iActivityClient;
    //活动调用类
    private final ActivityClient activityClient;

    private final IGrouponClient grouponClient;

    private final TopicClient topicClient;

    private final IUserCourseClient userCourseClient;

    private final RedisUtil redisUtil;
    @Value("${activity.endTime}")
    private Date endTime;
    @Value("${activity.startTime}")
    private Date startTime;

    //一小时
    @Value("${activity.oneHour}")
    private Long oneHour;
    //两小时
    @Value("${activity.twoHour}")
    private Long twoHour;
    //五分钟
    @Value("${activity.fiveMinutes}")
    private Long fiveMinutes;
    //十分钟
    @Value("${activity.tenMinutes}")
    private Long tenMinutes;
    //半小时
    @Value("${activity.halfHour}")
    private Long halfHour;
    //两分钟
    @Value("${activity.twoMinutes}")
    private Long twoMinutes;

    //二十四小时
    @Value("${activity.fiveMinutes}")
    private Long twentyFourHour;


    @Value("${activity.isAll}")
    private Integer isAll;


    private final static String KEYWORD = "活动已结束，请期待下次活动!";
    
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信oauth-微信授权", notes = "传入code，微信oauth的code值")
    public String subscribe(HttpServletRequest request) {
        System.out.println(("----------------------------这是微信验证方法。。。。。。。。。。。。。。。。。。。。。"));
        logger.info("----------------------------");
        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            System.out.println("signature=========================" + signature);
            System.out.println(timestamp);
            System.out.println(nonce);
            System.out.println(request.getInputStream());
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                EventVo eventVo = WeChatUtil.getPullMessage(request);
                logger.info("Event---------------------------- " + eventVo.getEvent());
                logger.info("EventKey---------------------------- " + eventVo.getEventKey());
                logger.info("endTime---------------------------- " + endTime);
                if (eventVo != null) {
                    String accessToken = activityClient.getAccessToken();
                    logger.info("accessToken: " + accessToken);
                    //当前扫描人员 即 助力人员 openId
                    String openId = eventVo.getFromUserName();
                    String eventKey = eventVo.getEventKey();
                    //被助力用户openId
                    String helpOpenId = "";

                    if (eventVo.getEvent() != null && eventVo.getEvent().equals("unsubscribe")) { //取关
                        R<UserVO> userVOR = userClient.detailByOpenIdV1(openId);
                        UserVO data = userVOR.getData();
                        Integer id = data.getId();
                        iActivityClient.userFocus(id, 0);
                        return "success";
                    }

                    logger.info("accessToken: " + accessToken);
                    net.sf.json.JSONObject jsonObject = HttpUtils.httpsRequest("https://api.weixin.qq" +
                            ".com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId, "POST", null);
                    logger.info("jsonObject: " + JSON.toJSONString(jsonObject));

                    if ("礼包".equalsIgnoreCase(eventVo.getContent())) {
                        StringBuffer sb = new StringBuffer();
                        sb.append("[勾引]点击以下链接获取礼包哦~\n");
                        sb.append("链接: <a href='https://pan.baidu.com/s/1IhL5Pc9oNqUB118RMV6oXg'>https://pan.baidu" +
                                ".com/s/1IhL5Pc9oNqUB118RMV6oXg </a>提取码: 63jj \n");
                        PushMessage.keyword(accessToken, openId, sb);
                    } else if ("客服".equalsIgnoreCase(eventVo.getContent())) {
                        //推送客服消息
                        activityClient.sendImageKFMessage(accessToken, openId, weChatProperties.getKFmediaId());
//                    	PushMessage.reply3(accessToken, eventVo, weChatProperties.getKFmediaId());
                    } else if ("获取锦盒".equalsIgnoreCase(eventVo.getContent())) {
                        //推送客服消息
                        activityClient.sendActivityMessage(openId);

                        //更新发送次数
                        //  iActivityClient.updateSendCount(4, 1);
                    }else if ("图文".equalsIgnoreCase(eventVo.getContent())) {
                        //推送客服消息
                       // activityClient.sendActivityMessage(openId);
                    	sendTW(eventVo);
                        //更新发送次数
                        //  iActivityClient.updateSendCount(4, 1);
                    }  else if ("群发海报".equalsIgnoreCase(eventVo.getContent())) {
                        //userCourseClient.createCourse(11, 1, 4);
                        //userCourseClient.createCourse(11, 2, 4);
                        //custonMsgSend11(eventVo);
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                sendMsg(accessToken, isAll);
                            }
                        }).start();


                        return "success";
                        //custonMsgSend(eventVo,2);
                    } else if ("马上加入".equalsIgnoreCase(eventVo.getContent())) {
                        //发送新海报
                        activityClient.sendImageKFMessage(accessToken, openId, weChatProperties.getNewPosterId());
                        iActivityClient.updateSendCount(4, 1);
                        
                    } else if ("领畅销儿童驼背矫正仪".equalsIgnoreCase(eventVo.getContent())) {
                        //activityClient.sendNewActivityPoster(openId, AssistProperties.ER_CODE_POSTER_N1);
                        //iActivityClient.updateSendCount(7,1);
                       // grouponClient.updateBuriedPointCountV2(3, 11, openId);
                        
                    } else if ("领20册小学必读国学故事".equalsIgnoreCase(eventVo.getContent())) {
                        //activityClient.sendNewActivityPoster(openId, AssistProperties.ER_CODE_POSTER_N2);
                        //iActivityClient.updateSendCount(8,1);
                        //grouponClient.updateBuriedPointCountV2(3, 12, openId);

                    } else if ("领透气网棉双肩小学书包".equalsIgnoreCase(eventVo.getContent())) {
                       // activityClient.sendNewActivityPoster(openId, AssistProperties.ER_CODE_POSTER_N3);
                        //iActivityClient.updateSendCount(9,1);
                        //grouponClient.updateBuriedPointCountV2(3, 13, openId);

                    } else if ("获取助力海报14".equalsIgnoreCase(eventVo.getContent())) {
                       // activityClient.sendNewActivityPoster(openId, AssistProperties.ER_CODE_POSTER_N4);
                       // iActivityClient.updateSendCount(10,1);
                       // grouponClient.updateBuriedPointCountV2(3, 14, openId);
                    }

                    if (eventKey != null) {

                        Integer sourceType = 0; //用户来源
                        int codeType = 0;     //奖品海报类型
                        if (eventKey.contains("ACTIVITY_A")) { //助力活动A
                            sourceType = 1;
                            codeType = 1;
                            if (new Date().after(startTime) && new Date().before(endTime)) { //活动未结束
                                //获取eventKey 中的被助力 openId
                                int index = eventKey.indexOf("ACTIVITY_A");
                                helpOpenId = eventKey.substring(index + 11);
                            }
                        } else if (eventKey.contains("ACTIVITY_B")) { //助力活动B
                            sourceType = 1;
                            codeType = 1;
                            if (new Date().after(startTime) && new Date().before(endTime)) { //活动未结束
                                int index = eventKey.indexOf("ACTIVITY_B");
                                helpOpenId = eventKey.substring(index + 11);
                            }
                        } else if (eventKey.contains("ACTIVITY_C")) { //助力活动B
                            sourceType = 1;
                            codeType = 1;
                            if (new Date().after(startTime) && new Date().before(endTime)) { //活动未结束
                                int index = eventKey.indexOf("ACTIVITY_C");
                                helpOpenId = eventKey.substring(index + 11);
                            }
                        } else if (eventKey.contains("ACTIVITY_11")) {
                            //助力活动奖品1
                            sourceType = 1;
                            codeType = 11;
                            int index = eventKey.indexOf("ACTIVITY_11");
                            helpOpenId = eventKey.substring(index + 12);
                        } else if (eventKey.contains("ACTIVITY_12")) {
                            //助力活动奖品2
                            sourceType = 1;
                            codeType = 12;
                            int index = eventKey.indexOf("ACTIVITY_12");
                            helpOpenId = eventKey.substring(index + 12);
                        } else if (eventKey.contains("ACTIVITY_13")) {
                            //助力活动奖品3
                            sourceType = 1;
                            codeType = 13;
                            int index = eventKey.indexOf("ACTIVITY_13");
                            helpOpenId = eventKey.substring(index + 12);
                        } else if (eventKey.contains("ACTIVITY_14")) {
                            //助力活动奖品4
                            sourceType = 1;
                            codeType = 14;
                            int index = eventKey.indexOf("ACTIVITY_14");
                            helpOpenId = eventKey.substring(index + 12);
                        } else if (eventKey.contains(("qrscene_")) && (eventKey.contains(("_1")) || eventKey.contains(("_2")))) { //扫课程二维码
                            sourceType = 3;
                            //点击菜单栏发送海报
                        } else if ("GET_KECHEN".equals(eventKey)) {
                            activityClient.sendImageKFMessage(accessToken, openId, weChatProperties.getNewPosterId());
                            iActivityClient.updateSendCount(4, 1);
                            
                        } else if ("GET_KEFU".equals(eventKey)) {
                            activityClient.sendImageKFMessage(accessToken, openId, weChatProperties.getKFmediaId());
                        } else if ("GET_SHUANG11".equals(eventKey)) { //双11获取菜单回复海报 + 文案。
                            activityClient.sendActivityPoster(openId, 3);
                        } else if ("GET_ZHUSHOU".equals(eventKey)) { //双11获取菜单回复海报 + 文案。
                            // activityClient.sendActivityPoster(openId, 3);
                            activityClient.sendImageKFMessage(accessToken, openId, weChatProperties.getZSmediaId());
                            System.out.println("发送助手图片。。。。");
                            return "success";
                        } else if ("GET_CHUXIN".equals(eventKey)) { //双11获取菜单回复海报 + 文案。
                            // activityClient.sendActivityPoster(openId, 3);
                            custonMsgSendChuXin(eventVo);
                            System.out.println("发送初心文案。。。。");
                            return "success";
                        } else if ("GET_ZERO_BUY".equals(eventKey)) {
                            //0元购活动文案
                            activityClient.sendNewActivity(openId);
                            return "success";
                        } else if ("GET_SHEQUN".equals(eventKey)) { //双11获取菜单回复海报 + 文案。
                           
                        	custonMsgSend(eventVo);
                        	activityClient.sendImageKFMessage(accessToken, openId, weChatProperties.getTeacherTemplateId());
                        	
                            return "success";
                        }else if (eventKey.contains("PUBLIC")) {
                            sourceType = 4;
                        } else if (eventKey.contains("THIRDPARTY")) { //活动关注，推文关注
                            sourceType = 5;
                        } else if (eventKey.contains("MESSAGE")) { //短信关注
                            sourceType = 6;
                        } else if (eventKey.contains("SHOW")) { //炫耀关注
                            sourceType = 7;
                        } else if (eventKey.contains("GROUP")) { //三人成团关注
                            sourceType = 8;
                        } else if (eventKey.contains("AI")) { //三人成团关注
                            sourceType = 9;
                        }else if (eventKey.contains("GUANGGAO")) { //20201125投放广告
                            sourceType = 10;
                        }else if (eventKey.contains("GET_TBJZY")) {
                        	
                        	 if (new Date().after(startTime) && new Date().before(endTime)) {
                        		 activityClient.sendNewActivityPoster(openId, AssistProperties.ER_CODE_POSTER_N1);
                        		 iActivityClient.updateSendCount(7,1);
                        		 grouponClient.updateBuriedPointCountV2(3, 11, openId);
                        		 return "success";
                        	 }else{
                        		 
                        		 StringBuffer stringBuffer = new StringBuffer();
                        		 stringBuffer.append(KEYWORD);
                        		 PushMessage.keyword(accessToken, openId, stringBuffer);
                        		 return "success";
                        	 }
                        	
                        } else if (eventKey.contains("GET_XXGSS")) {
                        	if (new Date().after(startTime) && new Date().before(endTime)) {
                        		activityClient.sendNewActivityPoster(openId, AssistProperties.ER_CODE_POSTER_N2);
                                iActivityClient.updateSendCount(8,1);
                                grouponClient.updateBuriedPointCountV2(3, 12, openId);
                                return "success";
                       	 }else{
                       		 
                       		 StringBuffer stringBuffer = new StringBuffer();
                       		 stringBuffer.append(KEYWORD);
                       		 PushMessage.keyword(accessToken, openId, stringBuffer);
                       		 return "success";
                       	 }
                        	
                        	
                        	
                            
                        } else if (eventKey.contains("GET_SHUBAO")) {
                        	
                        if (new Date().after(startTime) && new Date().before(endTime)) {
                        	 activityClient.sendNewActivityPoster(openId, AssistProperties.ER_CODE_POSTER_N3);
                             iActivityClient.updateSendCount(9,1);
                             grouponClient.updateBuriedPointCountV2(3, 13, openId);
                             return "success";
                       	 }else{
                       		 
                       		 StringBuffer stringBuffer = new StringBuffer();
                       		 stringBuffer.append(KEYWORD);
                       		 PushMessage.keyword(accessToken, openId, stringBuffer);
                       		 return "success";
                       	 }
                        	
                           
                        }

                        //查询创建当前扫码用户
                        String nickname = jsonObject.getString("nickname");
                        String headimgurl = jsonObject.getString("headimgurl");
                        R<UserVO> userVOR = userClient.detailByOpenIdV3(openId, nickname, headimgurl, sourceType);
                        UserVO scanUserId = userVOR.getData();
                        Integer focusStatus = scanUserId.getFocusStatus();


                        if (focusStatus == null || focusStatus == 0) {
                            iActivityClient.userFocus(scanUserId.getId(), 1);
                        }

                        if (eventVo.getEvent().equals("subscribe")) { //未关注，关注后进入
                            getNewMessage(eventVo, nickname); //欢迎文案推送
                            
                            if (sourceType == 10) {
                                
                            	//文案：
                            	custonMsgSend(eventVo);
                            	
                            	//老师二维码
                            	activityClient.sendImageKFMessage(accessToken, openId, weChatProperties.getTeacherTemplateId());
                            	
                            }
                            
                        	/*if(sourceType == 1){ //助力用户发送原文案
                        		getMessage(eventVo,nickname);
                        	}else{ //其他用户发新文案
                        	}*/


                            //短信关注用户 固定发两门课
                            if (sourceType == 6) {
                                userCourseClient.createCourse(userVOR.getData().getId(), 1, 4);
                                userCourseClient.createCourse(userVOR.getData().getId(), 2, 4);
                                custonMsgSend(eventVo, 2);
                            }


                            //助力用户发送千字文课程链接
                            if (new Date().after(startTime) && new Date().before(endTime)) {
                                if (sourceType == 1 && codeType == 1) {
                                    userCourseClient.createCourse(userVOR.getData().getId(), 1, 4);
                                    custonMsgSend11(eventVo);
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#1", oneHour);
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#2", fiveMinutes);

                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#3", twentyFourHour);
                                    iActivityClient.updateSendCount(4, 1);
                                } else if (sourceType != 1 && sourceType != 3 && sourceType != 5) {
                                    //延迟发送
//                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#1", oneHour);
                                    //2分钟后，弹出社群课程链接
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getTeacherTemplateId() + "#7", twoMinutes);
                                    //10分钟后，弹出课程卡片：书法名家故事，跳到新拼团页，团长是当前用户
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#6", tenMinutes);
                                    //30分钟后，弹出0元领活动区文案
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#4", halfHour);
                                } else if (sourceType == 1){
                                    //活动  模板：4=活动 6=拼团 7=社群
                                    //10分钟，弹出社群课程链接
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getTeacherTemplateId() + "#7", tenMinutes);
                                    //30分钟后，弹出课程卡片：书法名家故事，跳到新拼团页，团长是当前用户
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#6", halfHour);
                                } else if (sourceType == 5) {
                                    //投放
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getTeacherTemplateId() + "#7", twoMinutes);
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#6", tenMinutes);
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#4", halfHour);
                                } else if (sourceType == 3) {
                                    //扫课本
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getTeacherTemplateId() + "#7", twoMinutes);
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#6", tenMinutes);
                                    topicClient.sendMessage(openId + "#" + weChatProperties.getSuccessTemplate() + "#4", halfHour);
                                }
                            }

                        }

                        if (sourceType == 5) {
                            activityClient.sendActivityPoster(openId, 3);
                        }

                        
                        
                        
                        if (sourceType == 3 && eventVo.getEvent().equals("subscribe")) { //扫描课程二维码 未关注，关注后进入

                            custonSend(eventVo, 1);
                            // topicClient.sendMessage(openId, delayTime);
                            //延迟推送
                            // topicClient.sendMessage(openId, delayTime);
                        } else if (eventVo.getEvent().equals("SCAN") && (eventKey.contains(("_1")) || eventKey.contains(("_2")))) { //已关注
                            custonSend(eventVo, 2);
                        }




                        if (new Date().after(startTime) && new Date().before(endTime)) { //活动未结束 且 不属于扫课程二维码关注的
                            //查找或生成用户海报二维码 -- 埋点
                            iActivityClient.findOrAddUserErCode(scanUserId.getId(), AssistProperties.ER_CODE_POSTER);
                            iActivityClient.findOrAddUserErCode(scanUserId.getId(), AssistProperties.ER_CODE_POSTER_N1);
                            iActivityClient.findOrAddUserErCode(scanUserId.getId(), AssistProperties.ER_CODE_POSTER_N2);
                            iActivityClient.findOrAddUserErCode(scanUserId.getId(), AssistProperties.ER_CODE_POSTER_N3);
                            iActivityClient.findOrAddUserErCode(scanUserId.getId(), AssistProperties.ER_CODE_POSTER_N4);

                            //更新二维码扫码次数/人数
                            if (sourceType == 1) {
                                //被助力用户
                                userVOR = userClient.detailByOpenIdV1(helpOpenId);
                                UserVO helped = userVOR.getData();

                                iActivityClient.modifyErCodeCount(codeType, helped.getId(), scanUserId.getId());

                                //新用户进入弹出文案
                                activityClient.sendNewActivity(openId);

                                if (!openId.equals(helpOpenId)) {
                                    if (codeType == 1) {
                                        //助力流程 - 返回助力人数  0=已助力过/或助力人数超过8人 -->改为6人
                                        R<Integer> helpNumR = iActivityClient.assistantProcess(helped.getId(), scanUserId.getId(), AssistProperties.ER_CODE_POSTER);
                                        Integer helpNum = helpNumR.getData();
                                        if (helpNum != null && helpNum != 0) {
                                            if (helpNum == AssistProperties.NUMBER_C1) {
                                                PushMessage.sendMessage(helpOpenId, helpNum, nickname, weChatProperties.getHelpTemplate1());
                                                //更新发送次数
                                                iActivityClient.updateSendCount(1, 1);
                                            } else if (helpNum == AssistProperties.NUMBER_C2) {
                                                PushMessage.sendMessage(helpOpenId, helpNum, nickname, weChatProperties.getHelpTemplate1());
                                                //更新发送次数
                                                iActivityClient.updateSendCount(2, 1);
                                            } else if (helpNum == AssistProperties.NUMBER_GIFT) {
                                                PushMessage.sendMessage(helpOpenId, helpNum, nickname, weChatProperties.getHelpTemplate1());
                                                //更新发送次数
                                                iActivityClient.updateSendCount(3, 1);
                                            } else {
                                                PushMessage.sendMessage(helpOpenId, helpNum, nickname, weChatProperties.getHelpTemplate2());
                                            }
                                        }
                                    } else if (codeType != 0) {
                                        //新助力活动，只要3人
                                        R<Integer> helpNumR = iActivityClient.assistantProcess(helped.getId(), scanUserId.getId(), codeType);
                                        Integer helpNum = helpNumR.getData();
                                        if (helpNum != null && helpNum != 0) {
                                            if (helpNum == AssistProperties.NUMBER_AWARD) {
                                                //发送成功助力消息
                                                PushMessage.sendAssistMessage(helpOpenId, helpNum, nickname, codeType, helped.getName());
                                                activityClient.sendImageKFMessage(accessToken, helpOpenId, weChatProperties.getLSmediaId());
                                            } else {
                                                //发送有好友助力消息
                                                PushMessage.sendAssistMessage(helpOpenId, helpNum, nickname, codeType, helped.getName());
                                            }
                                        }
                                    }
                                }
                            } else {
                                iActivityClient.modifyErCodeCount(sourceType, 0, scanUserId.getId());
                                iActivityClient.assistantProcess(0, scanUserId.getId(), AssistProperties.ER_CODE_POSTER);
                            }
                        }

                    }
                }
            } else {
                System.err.println("签名检验失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    @ResponseBody
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        } else {
            return "fail";
        }

    }


    @RequestMapping(value = "/subscribe/sns", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信oauth-微信授权（少年说）", notes = "传入code，微信oauth的code值")
    public String subscribeSns(HttpServletRequest request) {
        System.out.println(("----------------------------这是微信验证方法。。。。。。。。。。。。。。。。。。。。。"));
        logger.info("----------------------------");
        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            System.out.println("signature=========================" + signature);
            System.out.println(timestamp);
            System.out.println(nonce);
            System.out.println(request.getInputStream());
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                EventVo eventVo = WeChatUtil.getPullMessage(request);
                logger.info("Event---------------------------- " + eventVo.getEvent());
                logger.info("EventKey---------------------------- " + eventVo.getEventKey());
                if (eventVo != null) {

                    String accessToken = WeChatUtil.getSnsAccessToken();
                    logger.info("snsAccessToken: " + accessToken);

                    String openId = eventVo.getFromUserName();
                    String eventKey = eventVo.getEventKey();

                    net.sf.json.JSONObject jsonObject = HttpUtils.httpsRequest("https://api.weixin.qq" +
                            ".com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId, "POST", null);
                    logger.info("jsonObject: " + JSON.toJSONString(jsonObject));

                    //创建登录用户
                    String nickname = jsonObject.getString("nickname");
                    String headimgurl = jsonObject.getString("headimgurl");
                    R<SnsUserVO> snsUserVOR = userClient.detailUserForSns(openId, nickname, headimgurl);


                    //未关注用户，发送欢迎语 和 报名链接
                    if (eventVo.getEvent().equals("subscribe")) {
                        sendSNSWelcomeMessage(eventVo, nickname);
                    }

                    //点击菜单栏发送报名链接
                    if (StringUtils.isNotBlank(eventKey)) {
                        if (eventKey.contains("APPLY")) {

                        }
                    }
                }
            } else {
                System.err.println("签名检验失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "/subscribe/sns", method = RequestMethod.GET)
    @ResponseBody
    public String validateSns(@RequestParam(value = "signature", required = false) String signature,
                              @RequestParam(value = "timestamp", required = false) String timestamp,
                              @RequestParam(value = "nonce", required = false) String nonce,
                              @RequestParam(value = "echostr", required = false) String echostr) {
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        } else {
            return "fail";
        }

    }

    private void getMessage(EventVo eventVo, String nickname) {
        //if (SecretUtil.getTextMessageEnable()) {
        Message message = new Message();
        message.setFromUserName(eventVo.getToUserName());
        message.setToUserName(eventVo.getFromUserName());
        message.setCreateTime(new Date().toString());

        /*Hi，用户昵称

	        欢迎来到大象智教书法学堂，我们致力于为1~6年级孩子提供专业、有趣的【软硬笔书法及大语文课程】
	
	        🏻点击下方菜单栏中【我的课堂】马上进行体验
	
	        还给你准备了新人限时礼包！速抢🏻🏻🏻
	        【0元】领取价值239元学霸锦盒*/

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("😘Hi，" + nickname + "\n\n");
            sb.append("🎉欢迎来到大象智教书法学堂，我们致力于为1~6年级孩子提供专业、有趣的【软硬笔书法及大语文课程】 \n\n");
            sb.append("👉点击下方菜单栏中【我的课堂】马上进行体验 \n\n");
            sb.append("🎁还给你准备了新人限时礼包！速抢 👇 👇 👇 \n");
            sb.append("<a href='weixin://bizmsgmenu?msgmenucontent=获取锦盒&msgmenuid=1'>【0元】领取价值239元学霸锦盒</a>");
			/* sb.append("[奸笑]更多免费艺考必备真题、正谱以及伴奏，回复【0】加我微信即可获取！\n\n");
	        sb.append("[吃瓜] 我们还偷偷把很多“宝藏”塞在了【艺考工具】\n\n");
	        sb.append("[旺柴]快戳下方菜单栏看看吧~\n");
		 	sb.append("欢迎来到大象智教书法学堂");*/

            //  sb.append(SecretUtil.getTextMessageContent());
            //message.setContent(sb.toString());
            String content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                    "\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            String accessToken = activityClient.getAccessToken();
            JSONObject paramInner = JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken,
                    paramInner, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //message.setMsgType("text");
        //return WeChatUtil.convertToXml(message, "UTF-8");
        //}
        //return null;
    }


    private void getChuXinMessage(EventVo eventVo) {
        //if (SecretUtil.getTextMessageEnable()) {
        Message message = new Message();
        message.setFromUserName(eventVo.getToUserName());
        message.setToUserName(eventVo.getFromUserName());
        message.setCreateTime(new Date().toString());

        /*Hi，用户昵称

	        欢迎来到大象智教书法学堂，我们致力于为1~6年级孩子提供专业、有趣的【软硬笔书法及大语文课程】

	        🏻点击下方菜单栏中【我的课堂】马上进行体验

	        还给你准备了新人限时礼包！速抢🏻🏻🏻
	        【0元】领取价值239元学霸锦盒*/

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("❤谢谢你的关注和支持\n\n");

            sb.append("“大象智教书法学堂”是由20年研发教学经验的北大书法老师武韶海教授倡导创立的语文国学学习分享平台\n\n");
            sb.append("武教授在教学的时候，发现孩子们对优质语文国学的学习并不够，而且近年来中高考语文的命题方式进行很大改革，阅读题量增加5-8%，国学类阅读题占比增加40-60%！\n\n");
            sb.append("对于阅读知识不够充分的孩子，做题速度、理解水平会受到影响，甚至连题都没做完，最怕高考的时候被拉开十几分的差距\n\n");
            sb.append("我们初衷是希望将最优质的的国学语文课程资源，分享给每一位孩子，帮助他们提升对国学和语文的兴趣，也能提高自身的文化修养，为中高考积累丰富知识量.");


            String content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                    "\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            String accessToken = activityClient.getAccessToken();
            JSONObject paramInner = JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken,
                    paramInner, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //message.setMsgType("text");
        //return WeChatUtil.convertToXml(message, "UTF-8");
        //}
        //return null;
    }


    private void getNewMessage(EventVo eventVo, String nickname) {
        //if (SecretUtil.getTextMessageEnable()) {
        Message message = new Message();
        message.setFromUserName(eventVo.getToUserName());
        message.setToUserName(eventVo.getFromUserName());
        message.setCreateTime(new Date().toString());

        /*Hi，用户昵称

	        欢迎来到大象智教书法学堂，我们致力于为1~6年级孩子提供专业、有趣的【软硬笔书法及大语文课程】

	        🏻点击下方菜单栏中【我的课堂】马上进行体验

	        还给你准备了新人限时礼包！速抢🏻🏻🏻
	        【0元】领取价值239元学霸锦盒*/

        
        
        
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("❤@"+nickname+"家长您好，欢迎关注~\n");
            sb.append("我们团队由北京大学、中南大学等985高校教授领头，专注创新结合AI+语文教育，围绕小中学生练字、书法、阅读、写作、国学等，帮助孩子提高语文核心素质");
          /*  sb.append("“大象智教书法学堂”是由20年教学经验的北大书法老师武韶海教授倡导创立的语文国学学习分享平台 \n\n");
            sb.append("武教授在教学的时候，发现孩子们对优质语文国学的学习并不够，而且近年来中高考语文的命题方式进行很大改革，阅读题量增加5-8%，国学类阅读题占比增加40-60%！ \n\n");
            sb.append("对于阅读知识不够充分的孩子，做题速度、理解水平会受到影响，甚至连题都没做完，最怕高考的时候被拉开十几分的差距 \n\n");
            sb.append("我们初衷是希望将最优质的的国学语文课程资源，分享给每一位孩子，帮助他们提升对国学和语文的兴趣，也能提高自身的文化修养，为中高考积累丰富知识量 \n\n");
            sb.append("关于提升孩子对于语文国学的教育，可以添加教授的小助手一起交流：微信14745266990");*/


            // sb.append("<a href='weixin://bizmsgmenu?msgmenucontent=马上加入&msgmenuid=1'>🎊原价129元《5天写字特训营》专属老师1V1
            // 点评，限时0元领！</a>");
			/* sb.append("[奸笑]更多免费艺考必备真题、正谱以及伴奏，回复【0】加我微信即可获取！\n\n");
	        sb.append("[吃瓜] 我们还偷偷把很多“宝藏”塞在了【艺考工具】\n\n");
	        sb.append("[旺柴]快戳下方菜单栏看看吧~\n");
		 	sb.append("欢迎来到大象智教书法学堂");*/

            //  sb.append(SecretUtil.getTextMessageContent());
            //message.setContent(sb.toString());
            String content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                    "\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            String accessToken = activityClient.getAccessToken();
            JSONObject paramInner = JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken,
                    paramInner, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //message.setMsgType("text");
        //return WeChatUtil.convertToXml(message, "UTF-8");
        //}
        //return null;
    }


    private void sendSNSWelcomeMessage(EventVo eventVo, String nickname) {
        Message message = new Message();
        message.setFromUserName(eventVo.getToUserName());
        message.setToUserName(eventVo.getFromUserName());
        message.setCreateTime(new Date().toString());
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("😘Hi，" + nickname + "\n\n");
            sb.append("🎉欢迎关注广州精粹教育 \n\n");
            sb.append("<a href='https://saberfrontrelease.rz-edu.cn/enroll'>点击进入“少年说”报名</a>");

            String content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                    "\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            String accessToken = WeChatUtil.getSnsAccessToken();
            JSONObject paramInner = JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken,
                    paramInner, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String getSfMessage(EventVo eventVo, String evenKey, Integer index) {
        String name = "";
        Message message = new Message();
        if (evenKey != null && !"".equals(evenKey)) {
            String[] str = evenKey.split("_");
            String code = str[0];
            String type = str[1];
            if (index == 1) {
                code = str[1];
                type = str[2];
            }

            R<Map<String, Object>> character = textbookClient.getLessonCharacterInfo(code);
            String s = "未知";

            if ("1".equals(type)) { //软笔
                s = character.getData().get("characterName") + "";
                name = "软笔字" + "“ " + s + " ”" + "字教学";
            } else if ("2".equals(type)) { //硬笔笔
                String[] ss = s.split("、");
                s = character.getData().get("lessonName") + "";
                name = "硬笔" + "“ " + s + " ”" + "教学";
            }

            message.setFromUserName(eventVo.getToUserName());
            message.setToUserName(eventVo.getFromUserName());
            message.setCreateTime(new Date().toString());
            StringBuffer sb = new StringBuffer();
            sb.append("<a href='http://saberfront.rz-edu.cn/course/" + code + "/" + type + "'>" + name + "</a>");
            message.setContent(sb.toString());
            message.setMsgType("text");
        }

        return WeChatUtil.convertToXml(message, "UTF-8");

    }


    private void custonSend(EventVo eventVo, Integer index) {
        String name = "";
        String evenKey = eventVo.getEventKey();
        try {
            if (eventVo.getEventKey() != null && !"".equals(eventVo.getEventKey())) {
                String[] str = evenKey.split("_");
                String code = str[0];
                String type = str[1];
                if (index == 1) {
                    code = str[1];
                    type = str[2];
                }

//			R<Map<String,Object>> character = textbookClient.getLessonCharacterInfo(code);
//			String s = "未知";
//
//			if("1".equals(type)){ //软笔
//				s = character.getData().get("characterName")+"";
//				name = "软笔字"+"“ "+s+" ”"+"字教学";
//			}else if("2".equals(type)){ //硬笔笔
//				s = character.getData().get("lessonName")+"";
//				if(s.contains("、")){
//					String ss[]= s.split("、");
//					name = "硬笔" + "“ "+ ss[1] +" ”"+"教学";
//				}else{
//					name = "硬笔" + "“ "+ s +" ”"+"教学";
//				}
//			}
                StringBuffer sb = new StringBuffer();
//			sb.append("<a href='http://saberfront.rz-edu.cn/course/"+code+"/"+type+"'>"+name+"</a>");

                R<List<CharLinkVO>> result = textbookClient.findByCodeAndType(type, code);
                List<CharLinkVO> charLinkVOList = result.getData();
                if (charLinkVOList != null && !charLinkVOList.isEmpty()) {
                    String title = "";
                    String href = "";
                    for (CharLinkVO vo : charLinkVOList) {
                        title = getCharacterTitle(type, vo);
                        href = "<a href='https://saberfrontrelease.rz-edu.cn/course/" + vo.getTextbookId()
                                + "闯关/" + vo.getLessonId() + "?subject=" + vo.getSubject()
                                + "&gradeCode=" + vo.getGradeCode() + "&characterId=" + vo.getCharacterId()
                                + "&loginSource=1" + "'>" + title +
                                "</a>";
                        //sb.append(href);

                        String content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                                "\"text\":{\"content\":\"" + href + "\"}}";

                        String accessToken = activityClient.getAccessToken();
                        JSONObject paramInner = JSONObject.parseObject(content);
                        HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);

                        //更新课程链接发送次数
                        iActivityClient.updateLessonSendCount(vo.getLessonId());
                    }

                }

		/*	String content = "{\"touser\":\""+eventVo.getFromUserName()+"\",\"msgtype\":\"text\",
		\"text\":{\"content\":\""+sb.toString()+"\"}}";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("grant_type", "client_credential");
			map.put("appid", weChatProperties.getAppId());
			map.put("secret", weChatProperties.getAppSecret());
			JSONObject o = null;
			o = HttpClient.httpGet("https://api.weixin.qq.com/cgi-bin/token", map);
			String accessToken = o.getString("access_token");
			JSONObject paramInner = JSONObject.parseObject(content);
			HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken,
			paramInner, true);*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void custonMsgSend(EventVo eventVo) {
        try {

            StringBuffer sb = new StringBuffer();
            sb.append("字写得丑，真的影响孩子学习！\n\n");
            sb.append("❌拿笔姿势错误，影响写字速度\n\n");
            sb.append("❌写字速度太慢，作业写不完\n\n");
            sb.append("❌字迹潦草，作文卷面扣3-4分\n\n");
            sb.append("💯【北大名师练字课】科学规范孩子写字，1对1老师点评指导，每天15分钟高效练字！\n\n");
            sb.append("✅ 抓住孩子学习敏感期，帮孩子规范写字！\n\n");
            sb.append("扫描下方二维码领取课程👇👇");
            String accessToken = activityClient.getAccessToken();
            String content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                    "\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            JSONObject paramInner = JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken,
                    paramInner, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    private void custonMsgSendChuXin(EventVo eventVo) {
        try {

            StringBuffer sb = new StringBuffer();
            sb.append("❤谢谢你的关注和支持\n\n");

            sb.append("“大象智教书法学堂”是由20年研发教学经验的北大书法老师武韶海教授倡导创立的语文国学学习分享平台\n\n");
            sb.append("武教授在教学的时候，发现孩子们对优质语文国学的学习并不够，而且近年来中高考语文的命题方式进行很大改革，阅读题量增加5-8%，国学类阅读题占比增加40-60%！\n\n");
            sb.append("对于阅读知识不够充分的孩子，做题速度、理解水平会受到影响，甚至连题都没做完，最怕高考的时候被拉开十几分的差距\n\n");
            sb.append("我们初衷是希望将最优质的的国学语文课程资源，分享给每一位孩子，帮助他们提升对国学和语文的兴趣，也能提高自身的文化修养，为中高考积累丰富知识量.");
            String accessToken = activityClient.getAccessToken();
            String content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                    "\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            JSONObject paramInner = JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken,
                    paramInner, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void custonMsgSend(EventVo eventVo, int num) {
        String href = "";
        try {
            String accessToken = activityClient.getAccessToken();
            String content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                    "\"text\":{\"content\":\"" + href + "\"}}";
            JSONObject paramInner = JSONObject.parseObject(content);
            href = "<a href='https://saberfrontrelease.rz-edu.cn/other/1'>【千字文动画课】</a>";
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken,
                    paramInner, true);
            if (num == 2) {
                accessToken = activityClient.getAccessToken();
                href = "<a href='https://saberfrontrelease.rz-edu.cn/other/2'>【小学必背古诗词课程】</a>";
                content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                        "\"text\":{\"content\":\"" + href + "\"}}";
                paramInner = JSONObject.parseObject(content);
                HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void sendTW(EventVo eventVo){
    	String accessToken = activityClient.getAccessToken();
    	String toUser = eventVo.getFromUserName();
    	String title = "仅差2人成团！23个历史圣贤励志故事，足以影响孩子一生！";
    	String description = "用小故事讲大道理，给孩子树立品德学习的好榜样！";
    	String picurl = "http://mmbiz.qpic.cn/mmbiz_jpg/9tGRF21cpKXlSxU9ksmnT8Z5btibbRlJzhmMmPMKvJ9vDIjaM2xalWu6iaShI24eGeojA0BXt9JA7lekILAN5ibgQ/0?wx_fmt=jpeg";
    	String url = "https://saberfrontrelease.rz-edu.cn/group?groupId=426&isMakeUp=2&id=4";
    	
    	List<ArticleJSON> articleJSONS = new ArrayList<ArticleJSON>();
		ArticleJSON articleJSON = new ArticleJSON();
		articleJSON.setDescription(description);
		articleJSON.setPicurl(picurl);
		articleJSON.setTitle(title);
		articleJSON.setUrl(url);
		articleJSONS.add(articleJSON);
		NewsJSONN newsJSONN = new NewsJSONN();
		newsJSONN.setArticles(articleJSONS);
		NewsMessageJSON newsMessageJSON = new NewsMessageJSON();
		newsMessageJSON.setNews(newsJSONN);
		newsMessageJSON.setTouser(toUser);
		
		String param = JSONObject.toJSONString(newsMessageJSON);
		JSONObject paramInner = JSONObject.parseObject(param);
        try {
			HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    
    private void custonMsgSend11(EventVo eventVo) {
        String href = "";
        try {

            StringBuffer sb = new StringBuffer();
            href = "<a href='https://saberfrontrelease.rz-edu.cn/other/1'>👉为6-12岁识字敏感期孩子定制的【千字文动画课】</a>";
            sb.append("😘感谢你的热情助力，您的好友送给你一门好课 \n");
            sb.append(href + "\n");
            sb.append("❗限时领取~24小时后失效哦");
            String accessToken = activityClient.getAccessToken();
            String content = "{\"touser\":\"" + eventVo.getFromUserName() + "\",\"msgtype\":\"text\"," +
                    "\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            JSONObject paramInner = JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken,
                    paramInner, true);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
   /* 
    为6-12岁识字敏感期孩子定制的【千字文动画课】(链接）
    限时领取~24小时后失效哦*/


    private String getCharacterTitle(String type, CharLinkVO vo) {
        String title = "";
        if (vo == null) {
            return title;
        }
        if ("1".equals(type)) {
            title = vo.getTextbookName() + " 软笔 【" + vo.getLessonName() + "】 " + "“ " + vo.getCharacterName() + " ”" + "字教学 ";
        } else if ("2".equals(type)) {
            title = vo.getTextbookName() + " 硬笔 【" + vo.getLessonName() + "】 书法教学 ";
        }
        return title;
    }

    private JSONObject extractOpenId(String code) {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthClientRequest accessTokenRequest = null;
        try {
            accessTokenRequest = OAuthClientRequest
                    .tokenLocation(weChatProperties.getAccessTokenUrl())
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setParameter("appId", weChatProperties.getAppId())
                    .setParameter("secret", weChatProperties.getAppSecret())
                    .setCode(code)
                    .buildQueryMessage();
            //获取access token
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
            String openId = oAuthResponse.getParam("openid");
            String accessToken = oAuthResponse.getAccessToken();
            Long expiresIn = oAuthResponse.getExpiresIn();
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("access_token", accessToken);
            param.put("openid", openId);
            param.put("lang", "zh_CN");
            JSONObject result = HttpClient.httpGet(weChatProperties.getUserInfoUrl(), param);
            result.put("access_token", accessToken);
            result.put("openid", openId);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //清空accessToken
    @PostMapping("/removeAccessToken")
    @ResponseBody
    public String removeAccessToken() {
        WeChatUtil.ACCESS_TOKEN = null;
        return "success";
    }

    @PostMapping("/getAccessToken")
    @ResponseBody
    public String getAccessToken() {
        return WeChatUtil.getAccessToken();
    }


    @GetMapping("/jsapi_ticket/getTicket")
    @ResponseBody
    @ApiOperation(value = "获取jsapi_ticket", notes = "获取jsapi_ticket")
    public String getJSApiTicket() {
        String jsApiTicket = WeChatUtil.getJSApiTicket();
        return jsApiTicket;
    }

    @GetMapping("/jsapi_ticket/sign")
    @ResponseBody
    @ApiOperation(value = "生成jsapi_ticket签名", notes = "生成jsapi_ticket签名")
    public Map<String, String> getJSApiTicketSign(
            @ApiParam(value = "网页的URL，不包含#及其后面部分", required = true) @RequestParam(value = "url") String url) {
        String jsApiTicket = WeChatUtil.getJSApiTicket();
        Map<String, String> sign = JSSignUtil.sign(jsApiTicket, url);
        return sign;
    }

    private void sendMsg(String accessToken, Integer isAll) {
        R<List<UserVO>> data = userCourseClient.selectAllUser();
        if (data != null && isAll == 1) {
            List<UserVO> userList = data.getData();
            // System.out.println("总用户数：==================="+userList.size());
            // if(userList != null && userList.size() >0){
            //  int i=0;
            for (int i = 0; i < userList.size(); i++) {
                UserVO user = userList.get(i);

                this.activityClient.groupSend(user.getOpenId(), user.getName(), user.getIcon());
                System.out.println("发送第========" + i + "=========个用户");
                //  i++;
                // }
                System.out.println("发送完毕。。。。。。。。。。。。。。。。。。。。");
            }
        }
    }


    public static void main(String[] args) {
        String openid = "111111111111#2222";
        String[] str = openid.split("#");
        System.out.println(str.length);
        if (str != null && str.length > 1) {
            System.out.println(openid.split("#")[1]);
        } else {
            System.out.println(openid.split("#")[0]);
        }

    }
}
