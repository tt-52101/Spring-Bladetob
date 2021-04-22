package cn.rzedu.sf.activity.feign;

import cn.rzedu.sc.goods.feign.IGrouponClient;
import cn.rzedu.sf.activity.utils.PushMessage;
import cn.rzedu.sf.user.feign.ISFUserClient;
import cn.rzedu.sf.user.vo.UserVO;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.common.tool.HttpUtils;
import org.springblade.common.tool.WeChatUtil;
import org.springblade.common.vo.EventVo;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RefreshScope
public class ActivityClientImpl implements ActivityClient {
	private final static Logger logger = LoggerFactory.getLogger(ActivityClientImpl.class);
	private final ISFUserClient userClient;
	private final IGrouponClient grouponClient;

	@Value("${activity.endTime}")
	private Date endTime;
	@Value("${activity.startTime}")
	private Date startTime;

	@Override
	public String getAccessToken() {
		return WeChatUtil.getAccessToken();
	}

	@Override
	public void sendActivityMessage(String openId) {
		try {
			System.out.println(openId + " ------------------------------------");
			System.out.println("结束时间 ------------------------------------ " + new Date());
			//获取用户信息
			String accessToken = WeChatUtil.getAccessToken();
			if(new Date().before(startTime)){ //活动未开始
				StringBuffer sb = new StringBuffer();
				sb.append("活动未开始");
				PushMessage.reply(accessToken, openId, sb);
			} else if(new Date().after(endTime)){ //活动结束
				StringBuffer sb = new StringBuffer();
				sb.append("活动已经结束");
				PushMessage.reply(accessToken, openId, sb);
			} else {
				net.sf.json.JSONObject jsonObject = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId, "POST", null);
				logger.info("jsonObject: " + JSON.toJSONString(jsonObject));

				R<UserVO> userVOR = userClient.detailByOpenIdV1(openId);
				UserVO scanUserId = userVOR.getData();

				EventVo eventVo =  new EventVo();
				eventVo.setFromUserName(openId);
				PushMessage.pushMessage(eventVo, 3);
				//更新发送次数
			}
		} catch (IOException e) {
			logger.error("获取海报失败 :", e);
		}
	}

	@Override
	@SneakyThrows
	public String sendActivityPoster(String openId, Integer type) {
		String result = PushMessage.sendActivityPoster(openId, type);
		return result;
	}

	@Override
	public void sendNewActivity(String openId) {
		PushMessage.replyActivity(openId);
	}

	@Override
	public String sendNewActivityPoster(String openId, Integer type) {
		try {
			return PushMessage.replyPoster(openId, type);
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}
	}

	@Override
	public void sendImageKFMessage(String accessToken, String openId, String mediaId) {
		PushMessage.reply3(accessToken, openId, mediaId);
	}
	
	@Override
	public void keyword(String openId,StringBuffer stringBuffer){
		PushMessage.reply4(openId,stringBuffer);
	}
	
	
	@Override
	 public String groupSend(String openId,String name,String icon) {
		String result = "";
		try{
			result = PushMessage.groupSend(openId,name,icon);
			}catch(Exception e){
			e.printStackTrace();
		}
		 return result;
	}

	@Override
	public void sendInitiateGrouponMessage(String openId) {
		try {
			R<UserVO> userVOR = userClient.detailByOpenIdV1(openId);
			if (userVOR.getData() == null || userVOR.getData().getId() == null) {
				logger.error("发送拼团消息失败，无用户：" + openId);
				return;
			}
			Integer id = userVOR.getData().getId();
			R<Integer> result = grouponClient.initiateGroup(id, 4);
			Integer groupId = result.getData();
			if (groupId == null) {
				logger.error("发送拼团消息失败，创建拼团失败");
				return;
			}
			PushMessage.replyTWMessage(openId, groupId);

		} catch (Exception e) {
			logger.error("发送拼团消息失败 :", e);
		}

	}
}
