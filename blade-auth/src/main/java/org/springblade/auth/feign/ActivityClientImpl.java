//package org.springblade.auth.feign;
//
//import cn.rzedu.sf.user.feign.IActivityClient;
//import cn.rzedu.sf.user.feign.ISFUserClient;
//import cn.rzedu.sf.user.vo.UserVO;
//import com.alibaba.fastjson.JSON;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springblade.auth.utils.HttpUtils;
//import org.springblade.auth.utils.PushMessage;
//import org.springblade.auth.utils.WeChatUtil;
//import org.springblade.auth.vo.EventVo;
//import org.springblade.core.tool.api.R;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.Date;
//
//@RestController
//@RequiredArgsConstructor
//public class ActivityClientImpl implements ActivityClient {
//	private final static Logger logger = LoggerFactory.getLogger(ActivityClientImpl.class);
//	private final ISFUserClient userClient;
//	private final IActivityClient activityClient;
//
//	@Value("${activity.endTime}")
//	private Date endTime;
//	@Value("${activity.startTime}")
//	private Date startTime;
//
//	@Override
//	public void sendActivityMessage(String openId) {
//		try {
//			System.out.println(openId + " ------------------------------------");
//			System.out.println("结束时间 ------------------------------------ " + new Date());
//			//获取用户信息
//			String accessToken = WeChatUtil.getAccessToken();
//			if(new Date().before(startTime)){ //活动未开始
//				StringBuffer sb = new StringBuffer();
//				sb.append("活动未开始");
//				PushMessage.reply(accessToken, openId, sb);
//			} else if(new Date().after(endTime)){ //活动结束
//				StringBuffer sb = new StringBuffer();
//				sb.append("活动已经结束");
//				PushMessage.reply(accessToken, openId, sb);
//			} else {
//				net.sf.json.JSONObject jsonObject = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId, "POST", null);
//				logger.info("jsonObject: " + JSON.toJSONString(jsonObject));
//
//				R<UserVO> userVOR = userClient.detailByOpenIdV1(openId);
//				UserVO scanUserId = userVOR.getData();
//				R<Integer> activity = activityClient.userAwardType(scanUserId.getId());
//				Integer num = activity.getData();
//
//				EventVo eventVo =  new EventVo();
//				eventVo.setFromUserName(openId);
//				PushMessage.pushMessage(eventVo, num);
//				//更新发送次数
//				activityClient.updateSendCount(4, 1);
//			}
//		} catch (IOException e) {
//			logger.error("获取海报失败");
//		}
//	}
//}
