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

import cn.rzedu.sf.resource.feign.ITextbookClient;
import cn.rzedu.sf.user.feign.ISFUserClient;
import cn.rzedu.sf.user.vo.UserVO;
import com.alibaba.fastjson.JSONObject;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.auth.granter.ITokenGranter;
import org.springblade.auth.granter.TokenGranterBuilder;
import org.springblade.auth.granter.TokenParameter;
import org.springblade.auth.utils.HttpClient;
import org.springblade.auth.utils.MessageUtil;
import org.springblade.auth.utils.SignUtil;
import org.springblade.auth.utils.TokenUtil;
import org.springblade.auth.vo.Message;
import org.springblade.common.cache.CacheNames;
import org.springblade.common.props.WeChatProperties;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.system.user.entity.UserInfo;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.rzedu.sf.resource.entity.Character;

/**
 * 认证模块
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "授权接口")
public class AuthController {

	private RedisUtil redisUtil;

//	@Value("${wechat.config.appId}")
//	private String appId;
//
//	@Value("${wechat.config.appSecret}")
//	private String appSecret;

	private final static Logger logger = LoggerFactory.getLogger(AuthController.class); 
	
	@Resource
	private WeChatProperties weChatProperties;

	private ISFUserClient userClient;

	private ITextbookClient textbookClient;
	@PostMapping("token")
	@ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public R<AuthInfo> token(@ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = "password", required = false) String grantType,
							 @ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
							 @ApiParam(value = "租户ID", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantId,
							 @ApiParam(value = "账号") @RequestParam(required = false) String account,
							 @ApiParam(value = "密码") @RequestParam(required = false) String password) {

		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("tenantId", tenantId)
			.set("account", account)
			.set("password", password)
			.set("grantType", grantType)
			.set("refreshToken", refreshToken)
			.set("userType", userType);

		ITokenGranter granter = TokenGranterBuilder.getGranter(grantType);
		UserInfo userInfo = granter.grant(tokenParameter);

		if (userInfo == null || userInfo.getUser() == null || userInfo.getUser().getId() == null) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}

		return R.data(TokenUtil.createAuthInfo(userInfo));
	}

	@GetMapping("/captcha")
	@ApiOperation(value = "获取验证码")
	public R<Kv> captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = UUID.randomUUID().toString();
		// 存入redis并设置过期时间为30分钟
		redisUtil.set(CacheNames.CAPTCHA_KEY + key, verCode, 30L, TimeUnit.MINUTES);
		// 将key和base64返回给前端
		return R.data(Kv.init().set("key", key).set("image", specCaptcha.toBase64()));
	}


	@GetMapping("/getUserInfoByWxOAuth")
	@ApiOperation(value = "微信oauth-获取微信用户信息", notes = "传入code，微信oauth的code值")
	public R<JSONObject> getUserInfoByWxOAuth(@ApiParam(value = "微信oauth-code", required = true) @RequestParam(defaultValue = "code", required = false) String code) {
		JSONObject userInfo = extractOpenId(code, false);
		if (userInfo == null) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}
		return R.data(userInfo);
	}

	@GetMapping("/getUserInfoByWxOAuth/public")
	@ApiOperation(value = "微信oauth-获取微信用户信息（公开页面用）", notes = "传入code，微信oauth的code值")
	public R<JSONObject> getUserInfoByWxOAuthPublic(@ApiParam(value = "微信oauth-code", required = true) @RequestParam(defaultValue = "code", required = false) String code) {
		JSONObject userInfo = extractOpenId(code, true);
		if (userInfo == null) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}
		return R.data(userInfo);
	}

	private JSONObject extractOpenId(String code, Boolean isPublish) {
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

			addSFUserInfo(result, openId, result.getString("nickname"), result.getString("headimgurl"), isPublish);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//加入书法用户信息
	private void addSFUserInfo(JSONObject result, String openId, String name, String headImgUrl, Boolean isPublish) {
		UserVO userVO = getSFUserByOpenId(openId, name, headImgUrl, isPublish);
		if (userVO != null && userVO.getId() != null) {
			result.put("userId", userVO.getId());
			result.put("name", userVO.getName());
			result.put("nickname", userVO.getNickname());
			result.put("sex", userVO.getSex());
			result.put("birthday", userVO.getBirthday());
			result.put("icon", userVO.getIcon());
			result.put("uuid", userVO.getUuid());
			result.put("isExist", true);
		} else {
			result.put("isExist", false);
		}
	}

	private UserVO getSFUserByOpenId(String openId, String name, String headImgUrl, Boolean isPublish) {
		UserVO userVO = null;
		R<UserVO> result = null;
		if (isPublish) {
			result = userClient.detailByOpenIdV2(openId, name, headImgUrl);
		} else {
			result = userClient.detailByOpenIdV1(openId);
		}
		if (result.isSuccess() && 200 == result.getCode()) {
			userVO = result.getData();
		}
		return userVO;
	}


	@GetMapping("/getUserInfoByWxOAuth/sns")
	@ApiOperation(value = "微信oauth-获取微信用户信息（少年说报名用）", notes = "传入code，微信oauth的code值")
	public R<JSONObject> getUserInfoByWxOAuthSns(@ApiParam(value = "微信oauth-code", required = true) @RequestParam(defaultValue = "code", required = false) String code) {
		JSONObject userInfo = extractOpenIdSns(code);
		if (userInfo == null) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}
		return R.data(userInfo);
	}

	private JSONObject extractOpenIdSns(String code) {
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthClientRequest accessTokenRequest = null;
		try {
			accessTokenRequest = OAuthClientRequest
					.tokenLocation(weChatProperties.getAccessTokenUrl())
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setParameter("appId", weChatProperties.getSnsAppId())
					.setParameter("secret", weChatProperties.getSnsAppSecret())
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
	
	
}
