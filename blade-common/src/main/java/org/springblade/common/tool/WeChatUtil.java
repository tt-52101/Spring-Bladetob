package org.springblade.common.tool;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;

import org.springblade.common.cache.CacheNames;
import org.springblade.common.props.WeChatProperties;
import org.springblade.common.vo.EventVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class WeChatUtil {

	//连接微信服务器的token
	public static final String WX_TOKEN = "R_Z_Z_H_X_Y";
	//获取access_token接口地址
	public static final String WX_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//发送模板消息的接口地址
	public static final String WX_SEND_TEMPLATE_MESSAGES_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	//发送消息的接口地址
	public static final String WX_SEND_MESSAGES_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	//生成获取小程序码
	public static final String WX_XCX_GETUNLIMITED = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";
	//访问接口所需的access_token凭证
	public static String ACCESS_TOKEN;
	//access_token凭证的有效时间
	public static Long EXPIRESTIME;

	//APPID
	private static String APP_ID = "";
	// APP密钥
	private static String APP_SECRET = "";

	//APPID
	public static String XCX_APP_ID = "";
	// APP密钥
	public static String XCX_APP_SECRET = "";

	public static String XCX_GRANT_TYPE = "";

	public static String JCODE_2_SESSION_URL = "";

	//微信获取jsapi_ticket地址
	private static final String WX_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	//jsapi_ticket
	public static String JSAPI_TICKET;
	//jsapi_ticket有效期
	public static Long EXPIRES_TIME_JSAPI_TICKET;

	private static RedisTemplate<String, Object> redisTemplate;

	@Value("${wechat.config.appId}")
	public void setAPP_KEY(String appId) {
		WeChatUtil.APP_ID = appId;
	}

	@Value("${wechat.config.appSecret}")
	public void setAPP_SECRET(String appSecret) {
		WeChatUtil.APP_SECRET = appSecret;
	}

	@Resource
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Value("${wechat.config.xcxAppId}")
	public void setXCX_APP_ID(String xcxAppId) {
		WeChatUtil.XCX_APP_ID = xcxAppId;
	}

	@Value("${wechat.config.xcxAppSecret}")
	public void setXCX_APP_SECRET(String xcxAppSecret) {
		WeChatUtil.XCX_APP_SECRET = xcxAppSecret;
	}

	@Value("${wechat.config.xcxGrantType}")
	public void setXCX_GRANT_TYPE(String xcxGrantType) { WeChatUtil.XCX_GRANT_TYPE = xcxGrantType;}

	@Value("${wechat.config.jcode2SessionUrl}")
	public void setJcode2SessionUrl(String jcode2SessionUrl) { WeChatUtil.JCODE_2_SESSION_URL = jcode2SessionUrl;}

	public static EventVo getPullMessage(HttpServletRequest request) throws IOException, JAXBException {
		InputStream stream = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		try {
			System.out.println("微信返回来的输入流："+request.getInputStream());
			stream = request.getInputStream();
			reader = new InputStreamReader(stream);
			bufferedReader = new BufferedReader(reader);

			String temp = null;
			StringBuffer message = new StringBuffer();

			while ((temp = bufferedReader.readLine()) != null) {
				message.append(new String(temp.getBytes(), "UTF-8"));
			}
			System.out.println("解析后数据："+message.toString());
			return parseXml(message.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				stream.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		return null;
	}

	public static EventVo parseXml(String eventVo) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(EventVo.class); // 获取上下文对象
		Unmarshaller marshaller = context.createUnmarshaller();

		StringReader reader = new StringReader(eventVo);

		EventVo vo = (EventVo) marshaller.unmarshal(reader);
		return vo;
	}

	public static String convertToXml(Object obj, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}


	/**
	 * 微信公众号推送消息接口
	 *
	 * @param touser      接受方（用户的openId）
	 * @param templateId 模板消息的模板ID
	 * @param url         推送消息链接的url
	 * @param data        推送的数据,Map<"模板信息字段",Map<"values/color（字段属性）",Object> >
	 * @return
	 */
	public static String sendTemplateMessages(String touser, String templateId, String url, Map<String, Map<String, Object>> data) {
		String jsondata = JSONUtils.toJSONString(data);
		String dataNew = "{\n" +
				"           \"touser\":\"" + touser + "\",\n" +
				"           \"template_id\":\"" + templateId + "\",\n" +
				"           \"url\":\"" + url + "\",\n" +
				"           \"data\":" + jsondata +
				"       }";
		System.out.println("mqtsendTemplateMessages_data1: " + dataNew);
		return WeChatUtil.sendTemplateMessages(dataNew);
	}

	/**
	 * 发送模板消息
	 *
	 * @param data
	 * @return
	 */
	public static String sendTemplateMessages(String data) {
		String result = HttpClient.sendPost(WX_SEND_TEMPLATE_MESSAGES_URL.replace("ACCESS_TOKEN", getAccessToken()), data);
		return result;
	}

	/**
	 *
	 * 获取access_token
	 * @return
	 */
	public static String getAccessToken() {
		System.out.println("ACCESS_TOKEN --------- " + ACCESS_TOKEN);
		System.out.println("EXPIRESTIME --------- " + EXPIRESTIME);

		Object obj = redisTemplate.opsForValue().get("ACCESS_TOKEN");

		if(obj != null){
			ACCESS_TOKEN = obj.toString();
		}else{
			String result = HttpClient.sendGet(WX_ACCESSTOKEN_URL.replace("APPID", APP_ID).replace("APPSECRET", APP_SECRET));
			System.out.println("TOKEN--------------------------" + result);
			JSONObject json = JSONObject.parseObject(result);
			ACCESS_TOKEN = json.getString("access_token");
			Long expires_in = json.getLong("expires_in");
			EXPIRESTIME = System.currentTimeMillis() + (expires_in * 1000);
			redisTemplate.opsForValue().set("ACCESS_TOKEN", ACCESS_TOKEN, 2, TimeUnit.HOURS);


		}
		return ACCESS_TOKEN;
	}

	/**
	 * 获取小程序access_token
	 * @return
	 */
	public static String getXCXAccessToken() {
		String accessToken = null;
		Object obj = redisTemplate.opsForValue().get("XCX_ACCESS_TOKEN");
		if (obj != null) {
			accessToken = obj.toString();
		} else {
			String result = HttpClient.sendGet(WX_ACCESSTOKEN_URL.replace("APPID", XCX_APP_ID).replace("APPSECRET", XCX_APP_SECRET));
			System.out.println("TOKEN--------------------------" + result);
			JSONObject json = JSONObject.parseObject(result);
			accessToken = json.getString("access_token");
			redisTemplate.opsForValue().set("XCX_ACCESS_TOKEN", accessToken, 2, TimeUnit.HOURS);
		}
		System.out.println("XCX_ACCESS_TOKEN --------- " + accessToken);
		return accessToken;
	}

	public static byte[] createQRCode(Map<String,Object> map){
		String xcxAccessToken = getXCXAccessToken();
		String jsonString = JSON.toJSONString(map);
		System.out.println(jsonString);
		byte[] result = HttpClient.sendPostQRCode(WX_XCX_GETUNLIMITED.replace("ACCESS_TOKEN", xcxAccessToken), jsonString);
		return result;
	}

	/**
	 * 获取jsapi_ticket
	 * @return
	 */
	public static String getJSApiTicket() {
		String accessToken = getAccessToken();
		System.out.println("JSAPI_TICKET--------- " + JSAPI_TICKET);
		System.out.println("EXPIRES_TIME_JSAPI_TICKET--------- " + EXPIRES_TIME_JSAPI_TICKET);
		if (JSAPI_TICKET == null || System.currentTimeMillis() > EXPIRES_TIME_JSAPI_TICKET) {
			String result = HttpClient.sendGet(WX_JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken));
			System.out.println("JSAPI_TICKET_RESULT--------------------------" + result);
			JSONObject json = JSONObject.parseObject(result);
			JSAPI_TICKET = json.getString("ticket");
			Long expires_in = json.getLong("expires_in");
			EXPIRES_TIME_JSAPI_TICKET = System.currentTimeMillis() + (expires_in * 1000);
		}
		return JSAPI_TICKET;
	}

	/**
	 * 发送图文客服消息
	 * @param openId		接受方（用户的openId）
	 * @param title			图文消息标题
	 * @param description	内容
	 * @param picurl		图片路径
	 * @param url			跳转地址
	 */
	public static void sendNewsCustomMessage(String openId, String title, String description, String picurl, String url) {
		String content = "{\n" +
				"    \"touser\":\"" + openId + "\",\n" +
				"    \"msgtype\":\"news\",\n" +
				"    \"news\":{\n" +
				"        \"articles\": [\n" +
				"         {\n" +
				"             \"title\":\"" + title + "\",\n" +
				"             \"description\":\"" + description + "\",\n" +
				"             \"url\":\"" + url + "\",\n" +
				"             \"picurl\":\"" + picurl +"\"\n" +
				"         }\n" +
				"         ]\n" +
				"    }\n" +
				"}";
		JSONObject paramInner = JSONObject.parseObject(content);
		try {
			HttpClient.httpPost(WX_SEND_MESSAGES_URL.replace("ACCESS_TOKEN", getAccessToken()), paramInner, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送文本消息
	 * @param openId
	 * @param stringBuffer  消息内容
	 */
	public static void sendTextMessage(String openId, StringBuffer stringBuffer) {
		try {
			String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + stringBuffer.toString() + "\"}}";
			JSONObject paramInner = JSONObject.parseObject(content);
			HttpClient.httpPost(WX_SEND_MESSAGES_URL.replace("ACCESS_TOKEN", getAccessToken()), paramInner, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送图片消息
	 * @param openId
	 * @param mediaId  图片id
	 */
	public static void sendImageMessage(String openId, String mediaId) {
		try {
			String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"" + mediaId + "\"}}";
			JSONObject paramInner = JSONObject.parseObject(content);
			HttpClient.httpPost(WX_SEND_MESSAGES_URL.replace("ACCESS_TOKEN", getAccessToken()), paramInner, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
