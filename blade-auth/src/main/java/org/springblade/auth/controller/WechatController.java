package org.springblade.auth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.auth.utils.*;
import org.springblade.common.tool.WeChatUtil;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rzedu.sf.user.feign.ISFUserClient;
import cn.rzedu.sf.user.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    private final ISFUserClient userClient;

    @RequestMapping(value = "/modifyUserProfile", method = RequestMethod.POST)
    @ResponseBody
    public Boolean modifyUserProfile(String unionId, String nickName, String avatarUrl, Integer gender, String province, String city) {
        R<UserVO> userR = userClient.detailByUnionId(unionId);
        if (userR.isSuccess() && userR.getData() != null) {
            UserVO userVO = userR.getData();
            userVO.setName(nickName);
            userVO.setNickname(nickName);
            userVO.setUsername(nickName);
            userVO.setIcon(avatarUrl);
            userVO.setCity(city);
            userVO.setProvince(province);
            userVO.setSex(gender);
            userVO.setIsGetInfo(true);
            R<Boolean> result = this.userClient.updateById(userVO);
            if (result.isSuccess() && result.getData()) {
                return result.getData();
            }
        }

        return false;
    }

    @RequestMapping(value = "/decodeUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map decodeUserInfo(String encryptedData, String iv, String code) {
        Map map = new HashMap();
        // 登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }
        // 小程序唯一标识 (在微信小程序管理后台获取)
        String wxspAppid = WeChatUtil.XCX_APP_ID;
        // 小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = WeChatUtil.XCX_APP_SECRET;
        // 授权（必填）
        String grant_type = WeChatUtil.XCX_GRANT_TYPE;
        // 请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type="
                + grant_type;
        try {
            Connection.Response response = HttpUtils.get(WeChatUtil.JCODE_2_SESSION_URL + "?" + params);
            logger.info("response:" + response);
            // 解析相应内容（转换成json对象）
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(response.body());
            // 获取会话密钥（session_key）
            String session_key = json.get("session_key").toString();
            // 用户的唯一标识（openid）
            String openid = (String) json.get("openid");
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");
                map.put("session_key", session_key);
                map.put("openId", openid);
                net.sf.json.JSONObject userInfoJSON = net.sf.json.JSONObject.fromObject(result);
                Map userInfo = new HashMap();
                String unionId = userInfoJSON.getString("unionId");
                R<UserVO> userR = this.userClient.detailByUnionId(unionId, userInfoJSON.getString("nickName"), userInfoJSON.getString("avatarUrl"));
                userInfo.put("unionId", unionId);
                if (userR != null && userR.isSuccess() && userR.getData() != null) {
                    UserVO userVO = userR.getData();
                    userInfo.put("userId", userVO.getId());
                    userInfo.put("name", userVO.getName());
                    userInfo.put("openId", userVO.getOpenId());
                    userInfo.put("avatarUrl", userVO.getIcon());
                    userInfo.put("nickName", (userVO.getName() != null && !"".equals(userVO.getName())) ? userVO.getName() : userInfoJSON.get("nickName"));
                    userInfo.put("isGetInfo", userVO.getIsGetInfo());
                    userInfo.put("gender", userVO.getSex());
                    userInfo.put("province", userVO.getProvince());
                    userInfo.put("city", userVO.getCity());
                }
                map.put("userInfo", userInfo);
            } else {
                map.put("status", 0);
                map.put("msg", "解密失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信oauth-微信授权", notes = "传入code，微信oauth的code值")
    public String subscribe(HttpServletRequest request) {

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

}
