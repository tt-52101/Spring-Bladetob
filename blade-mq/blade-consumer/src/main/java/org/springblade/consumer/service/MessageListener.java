package org.springblade.consumer.service;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.common.tool.WeChatUtil;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import cn.rzedu.sf.activity.feign.ActivityClient;
import cn.rzedu.sf.user.feign.IUserCourseClient;
import cn.rzedu.sf.user.vo.UserVO;

@Component
@AllArgsConstructor
public class MessageListener {
	private final static Logger logger = LoggerFactory.getLogger(MessageListener.class);
    private ActivityClient activityClient;
    private final IUserCourseClient userCourseClient;
    //监听binding中的消息
    @StreamListener(Topic.CONSUMER)
    public void input(String message) {
        System.out.println("获取到消息: "+message);
       // activityClient.sendActivityMessage(message);
        if(message != null){
        	String []messages = message.split("#");
        	String openId = messages[0];
        	String templateId = messages[1];
        	String type = messages[2];
        	if("2".equals(type)){
        		activityClient.sendActivityMessage(openId);
        	}else if("1".equals(type)){
        		sendMessage(openId,templateId);
        	}else if("3".equals(type)){
        		sendMsg();
        	} else if ("4".equals(type)) {
        	    //新助力活动文案
                activityClient.sendNewActivity(openId);
            } else if ("5".equals(type)) {
        	    //未完成助力提示文案
                sendUnfinishedMessage(openId);
            } else if ("6".equals(type)) {
                //书法名家故事 拼团图文
                activityClient.sendInitiateGrouponMessage(openId);
            } else if ("7".equals(type)) {
        	    //社群链接文案
                sendSQMessage(openId);
                activityClient.sendImageKFMessage(WeChatUtil.getAccessToken(), openId, templateId);
            }
        }

    }
    
    
    
    private static void sendMessage(String openId,String templateId) {
       
        Map<String,String> title = new HashMap<String,String>();
        title.put("value", "叮咚，您的【写字训练课】还差3小时失效！仅限前100名，快来报名！");
        Map map = new HashMap();
        Map<String,String> valueKeyword1 = new HashMap<String,String>();
        valueKeyword1.put("value", "129元《硬笔入门训练营》限时免费！");
        Map<String,String> valueKeyword2 = new HashMap<String,String>();
        valueKeyword2.put("value", "11月14日周六");
        Map<String,String> remark = new HashMap<String,String>();
        
        Map<String,String> valueKeyword3 = new HashMap<String,String>();
        valueKeyword3.put("value", "周老师");
        
        remark.put("value", "点击领取课程，1V1老师辅导，添加老师后还有小礼物喔~");
        remark.put("color", "#ff0000");

        map.put("first", title);
        map.put("keyword1", valueKeyword1);
        map.put("keyword2", valueKeyword2);
        map.put("keyword3", valueKeyword3);
        map.put("remark", remark);
//        String url = "http://mmbiz.qpic.cn/mmbiz_png/9tGRF21cpKWDS9rMgJ4WwkyKgUCXIBLuH5nH6UyqOSUh6OHUCjpqgmLJDZtlfvz1UMeib1aNsLHibiczF59ACquRw/0?wx_fmt=png";
        String url = "https://saberfrontrelease.rz-edu.cn/training";
        //向教师推送
//        String result = WeChatUtil.sendTemplateMessages(touser, weChatProperties.getHelpTemplate2(), url, map);
        String result = WeChatUtil.sendTemplateMessages(openId, templateId, url, map);
        logger.info(result);
    }
    
    
    public void sendMsg(){
    	R<List<UserVO>> data =userCourseClient.findInitiatorUser(1, 5);
    	if(data != null){
    		List<UserVO> userList= data.getData();
    		StringBuffer stringBuffer = new StringBuffer();
    		stringBuffer.append("👀象象子看到您还差一点就能领【349元】的故事绘本啦~ \n");
    		stringBuffer.append("[加油]快快邀请好友助力吧！ \n");
    		stringBuffer.append("🉐好友帮忙助力可无门槛领取【69.9元千字文动画课】哦！ \n");
    		stringBuffer.append("👇您已获取的课程可点击下方菜单【我的课堂】进行查看~");
    		if(userList != null && userList.size() >0){
    			for(UserVO user:userList){
    				//PushMessage.keyword(accessToken, user.getOpenId(), stringBuffer);
    				activityClient.keyword(user.getOpenId(), stringBuffer);
    			}
    		}
    	}
    }

    public void sendCourseMessage(String openId) {
        StringBuffer sb = new StringBuffer();
        sb.append("<a href='https://saberfrontrelease.rz-edu.cn/other/5'>【书法名家故事】</a>");
        activityClient.keyword(openId, sb);
    }

    public void sendUnfinishedMessage(String openId) {
        StringBuffer sb = new StringBuffer();
        sb.append("我看到你还有好友没有助力成功哦，还差 人就能拿到【】了");
        sb.append("快分享给身边【家长】或【家长群】吧\uD83D\uDD25");
        activityClient.keyword(openId, sb);
    }

    public void sendSQMessage(String openId) {
        StringBuffer sb = new StringBuffer();
        sb.append("字写得丑，真的影响孩子学习！\n\n");
        sb.append("❌拿笔姿势错误，影响写字速度\n\n");
        sb.append("❌写字速度太慢，作业写不完\n\n");
        sb.append("❌字迹潦草，作文卷面扣3-4分\n\n");
        sb.append("💯【北大名师练字课】科学规范孩子写字，1对1老师点评指导，每天15分钟高效练字！\n\n");
        sb.append("✅ 抓住孩子学习敏感期，帮孩子规范写字！\n\n");
        sb.append("扫描下方二维码领取课程👇👇");
        activityClient.keyword(openId, sb);
    }

}
