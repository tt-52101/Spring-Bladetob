package cn.rzedu.sf.activity.utils;

import cn.rzedu.sf.activity.vo.ArticleJSON;
import cn.rzedu.sf.activity.vo.NewsJSONN;
import cn.rzedu.sf.activity.vo.NewsMessageJSON;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.common.tool.HttpClient;
import org.springblade.common.tool.HttpUtils;
import org.springblade.common.tool.WeChatUtil;
import org.springblade.common.vo.EventVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class PushMessage {

    private final static Logger logger = LoggerFactory.getLogger(PushMessage.class);

    //海报文件生成存放路径
    private static String directory;
    //服务地址
    private static String serverPath;

    @Value("${poster.directory}")
    public void setDirectory(String directory) {
        PushMessage.directory = directory;
    }

    @Value("${serverPath}")
    public void setServerPath(String serverPath) {
        PushMessage.serverPath = serverPath;
    }

    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *  关注后推送 两个文案、一张海报
     * @param eventVo
     * @param type
     * @return
     * @throws IOException
     */
    public static Object pushMessage(EventVo eventVo, Integer type) throws IOException {
        String openId = eventVo.getFromUserName();

        //获取用户信息
        String accessToken = WeChatUtil.getAccessToken();
        JSONObject jsonObject = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId, "POST", null);


        //生成uuid作为图片名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        File directoryNew = new File(directory);
        if (!directoryNew.exists()) {
            directoryNew.mkdirs();
        }

        String nickname = jsonObject.getString("nickname");
        String headimgurl = jsonObject.getString("headimgurl");
        String headUrl = headimgurl;
        if(headUrl == null || "".equals(headUrl)){
            headUrl = serverPath + "/img/dxhead.jpg";
        }
        String outPutPath = directory + "POSTER_" + uuid + ".png";

        if (type == 1) {
            //获取用户二维码
            String qrCodePath = getQRCode(directory, openId, uuid, "A", accessToken);
            logger.info("qrCodePath--------------- " + qrCodePath);
            //背景图地址
            ClassPathResource classPathResource = new ClassPathResource("static/poster_background1.png");
            File background = classPathResource.getFile();
            String backgroundPath = background.getPath();

            System.out.println("backgroundPath : " + backgroundPath);
            String result = Image2Pic.overlapImage(backgroundPath, headUrl, qrCodePath, outPutPath);
            System.out.println("result : " + result);

            if ("success".equals(result)) {
                File file = new File(outPutPath);
                if (file.exists()) {
                    String mediaId = uploadPoster(file, accessToken);
                   // reply1(accessToken, openId, nickname, type);
                    reply3(accessToken, openId, mediaId);
                    reply2(accessToken, openId,nickname);

                    //删除文件
                    File qrCode = new File(qrCodePath);
                    file.delete();
                    qrCode.delete();
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        } else if (type == 2) {
            //获取用户二维码
            String qrCodePath = getQRCode(directory, openId, uuid, "B", accessToken);
            logger.info("qrCodePath--------------- " + qrCodePath);
            //背景图地址
            ClassPathResource classPathResource = new ClassPathResource("static/poster_background2.png");
            File background = classPathResource.getFile();
            String backgroundPath = background.getPath();

            String result = Image2Pic.overlapImage(backgroundPath, headUrl, qrCodePath, outPutPath);

            if ("success".equals(result)) {
                File file = new File(outPutPath);
                if (file.exists()) {
                    String mediaId = uploadPoster(file, accessToken);
                    //reply1(accessToken, openId, nickname, type);
                    reply3(accessToken, openId, mediaId);
                    reply2(accessToken, openId,nickname);

                    //删除文件
                    File qrCode = new File(qrCodePath);
                    file.delete();
                    qrCode.delete();
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        } else if (type == 3) {
            //获取用户二维码
            String qrCodePath = getQRCode(directory, openId, uuid, "C", accessToken);
            logger.info("qrCodePath--------------- " + qrCodePath);
            //背景图地址
            ClassPathResource classPathResource = new ClassPathResource("static/poster_background3.png");
            File background = classPathResource.getFile();
            String backgroundPath = background.getPath();

            String result = Image2Pic.overlapImage(backgroundPath, headUrl, qrCodePath, outPutPath);

            if ("success".equals(result)) {
                File file = new File(outPutPath);
                if (file.exists()) {
                    String mediaId = uploadPoster(file, accessToken);
                    //reply1(accessToken, openId, nickname, type);
                    reply3(accessToken, openId, mediaId);
                    reply2(accessToken, openId,nickname);

                    //删除文件
                    File qrCode = new File(qrCodePath);
                    file.delete();
                    qrCode.delete();
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        }

        return "success";

    }

    /**
     * 获取用户二维码图片
     *
     * @param directory   二维码输出目录
     * @param openId
     * @param uuid        文件名
     * @param type        A、B
     * @param accessToken
     * @return
     */
    private static String getQRCode(String directory, String openId, String uuid, String type, String accessToken) {

        String sceneStr = "ACTIVITY_" + type + "_" + openId;
        logger.info("sceneId---------------------------- " + sceneStr);
        Map<String, Object> map = new HashMap<>();
        map.put("expire_seconds", 604800);
        map.put("action_name", "QR_STR_SCENE");
        Map<String, Object> sceneMap = new HashMap<>();
        Map<String, Object> sceneIdMap = new HashMap<>();
        sceneIdMap.put("scene_str", sceneStr);
        sceneMap.put("scene", sceneIdMap);
        map.put("action_info", sceneMap);
        String data = JSON.toJSONString(map);
        // 得到ticket票据,用于换取二维码图片
        JSONObject jsonObject = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken, "POST", data);
        String ticket = (String) jsonObject.get("ticket");
        logger.info("data------------------- " + data);
        logger.info("accessToken------------------- " + accessToken);
        logger.info("jsonObject------------------- " + JSON.toJSONString(jsonObject));
        logger.info("ticket------------------- " + ticket);
        // WXConstants.QRCODE_SAVE_URL: 填写存放图片的路径
        HttpUtils.httpsRequestPicture("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket),
                "GET", null, directory, "QRCODE_" + uuid, "png");

        return directory + "QRCODE_" + uuid + ".png";
    }

    //海报上传至临时素材,返回media_id
    private static String uploadPoster(File file, String accessToken) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("access_token", accessToken);
        paramMap.put("type", "image");
        Map<String, File> fileMap = new HashMap<String, File>();
        fileMap.put("file", file);
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/media/upload";
            Connection.Response response = HttpUtils.post(url, paramMap, fileMap);
            if (response.statusCode() == 200) {
                JSONObject jsonObject = JSONObject.fromObject(response.body());
                String mediaId = jsonObject.getString("media_id");
                return mediaId;
            }
        } catch (IOException e) {
            logger.error("上传临时素材报错：", e);
        }
        return "";
    }

    //客服消息1 -- 文案一
    private static void reply1(String accessToken, String openId, String nickname, Integer type) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("🎉@" + nickname + " 给你发福利啦！ \n");
            sb.append("🎁价值【239元】“学霸锦盒” \n");
            sb.append("🉐今天限时【0元】免费领！ \n");
            sb.append("活动不花您一分钱，全国包邮！\n\n");
            sb.append("👇拥有“学霸锦盒”，孩子可以 \n");

            if (type == 1) {
                sb.append("1⃣坐端正，远离驼背、近视 \n");
                sb.append("2⃣懂握笔，写字又快又好看 \n");
                sb.append("3⃣习惯好，高效记忆思维强 \n\n");
                sb.append("🔥仅剩【1985】套，领完即止");
            } else if (type == 2) {
                sb.append("1⃣懂握笔，写字又快又好看 \n");
                sb.append("2⃣习惯好，高效记忆思维强 \n");
                sb.append("3⃣懂国学，语文考试得高分 \n\n");
                sb.append("🔥仅剩【1985】套，领完即止");
            }

            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("grant_type", "client_credential");

            com.alibaba.fastjson.JSONObject paramInner = com.alibaba.fastjson.JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("客服消息1报错： ", e);
        }
    }

    //客服消息2 -- 文案二
    /*private static void reply2(String accessToken, String openId) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("👇0元免费领取价值【239元】学霸锦盒，仅需【2】步\n");
            sb.append("1⃣将上方海报分享给【好友或家长群】\n");
            sb.append("2⃣好友首次扫码关注公众号即可成功助力\n\n");

            sb.append("👉累积【1】位好友关注，即可免费领取【千字文动画课】 \n");
            sb.append("👉累积【4】位好友关注，即可免费领取【小学必背古诗词课程】 \n");
            sb.append("👉累积【8】位好友关注，即可免费领取【学霸锦盒】 \n");
            sb.append("👉公众号自动提示进度，完成任务即可填写收货地址 \n\n");

            sb.append("⚠️注意： \n");
            sb.append("好友助力同样可获丰厚好礼！快邀请身边的宝妈为你助力吧~");

            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("grant_type", "client_credential");

            com.alibaba.fastjson.JSONObject paramInner = com.alibaba.fastjson.JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("客服消息2报错： ", e);
        }
    }*/

    
    
    
    private static void reply2(String accessToken, String openId,String nickname) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("🎉@"+nickname+" 恭喜你获得绘本0元领特权！还能免费赠好友一门好课！ \n");
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
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("客服消息2报错： ", e);
        }
    }
    
    
    //0元领活动文案
    public static void replyActivity(String openId) {
        try {
            String accessToken = WeChatUtil.getAccessToken();
            JSONObject jsonObject = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId, "POST", null);
            String nickname = jsonObject.getString("nickname");

            StringBuffer sb = new StringBuffer();
            sb.append("@" + nickname + " 家长  您的【新用户礼包】  \uD83D\uDD25还有3小时失效\n\n");
//            sb.append("\uD83D\uDD14叮Ding，孩子都期中考了，给孩子个鼓励礼物吧\n\n");
//            sb.append("🎁【0元领活动】限时8小时，全国包邮，真实有效\n\n");
//            sb.append("\uD83C\uDFC1参与方式：点击链接，转发弹出海报给【家长】或家长群，8个家长扫码关注公众号就能0元拿\n\n");

            sb.append("👉 <a href='weixin://bizmsgmenu?msgmenucontent=领畅销儿童驼背矫正仪&msgmenuid=11'>点击领取畅销儿童驼背矫正仪</a>\n\n");
            sb.append("👉 <a href='weixin://bizmsgmenu?msgmenucontent=领20册小学必读国学故事&msgmenuid=12'>点击领取20册小学必读国学故事</a>\n\n");
            sb.append("👉 <a href='weixin://bizmsgmenu?msgmenucontent=领透气网棉双肩小学书包&msgmenuid=13'>点击领取透气网棉双肩小学书包</a>\n\n");
//            sb.append("👉 <a href='weixin://bizmsgmenu?msgmenucontent=获取助力海报14&msgmenuid=14'>领 儿童智能矫正矫正带</a>\n\n");
            sb.append("\uD83D\uDC4D全国包邮，真实有效\n\n");

            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("grant_type", "client_credential");

            com.alibaba.fastjson.JSONObject paramInner = com.alibaba.fastjson.JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //海报前文案
    public static void replyPosterText(String accessToken, String openId, Integer codeType) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("❤️恭喜获得《" + getAwardName(codeType) + "》领取资格\n\n");
            sb.append("领取方法：\n");
            sb.append("1、将下方您的海报，分享给好友或微信群\n");
            sb.append("2、邀请【3位】新好友扫码关注后，即可免费领取\n\n");
            sb.append("注：活动真实有效，数量有限，先到先得\uD83D\uDD25");

            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + sb.toString() + "\"}}";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("grant_type", "client_credential");

            com.alibaba.fastjson.JSONObject paramInner = com.alibaba.fastjson.JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAwardName(Integer codeType) {
        String name = "";
        switch (codeType) {
            case 11 : name = "畅销儿童驼背矫正仪"; break;
            case 12 : name = "20册小学必读国学故事"; break;
            case 13 : name = "透气网棉双肩小学书包"; break;
            case 14 : name = "儿童智能矫正矫正带"; break;
        }
        return name;
    }

    //4种奖品海报
    public static String replyPoster(String openId, Integer codeType) throws IOException {
        //获取用户信息
        String accessToken = WeChatUtil.getAccessToken();
        JSONObject jsonObject = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId, "POST", null);


        //生成uuid作为图片名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        File directoryNew = new File(directory);
        if (!directoryNew.exists()) {
            directoryNew.mkdirs();
        }

        String nickname = jsonObject.getString("nickname");
        String headimgurl = jsonObject.getString("headimgurl");
        String headUrl = headimgurl;
        if(headUrl == null || "".equals(headUrl)){
            headUrl = serverPath + "/img/dxhead.jpg";
        }
        String outPutPath = directory + "POSTER_" + uuid + ".png";

        //获取用户二维码
        String qrCodePath = getQRCode(directory, openId, uuid, codeType.toString(), accessToken);
        logger.info("qrCodePath--------------- " + qrCodePath);

        String png = "";
        switch (codeType) {
            case 11 : png = "poster_background11.png"; break;
            case 12 : png = "poster_background12.png"; break;
            case 13 : png = "poster_background13.png"; break;
            case 14 : png = "poster_background14.png"; break;
        }
        //背景图地址
        ClassPathResource classPathResource = new ClassPathResource("static/" + png);
        File background = classPathResource.getFile();
        String backgroundPath = background.getPath();

        String result = Image2Pic.overlapImage(backgroundPath, headUrl, qrCodePath, outPutPath, codeType);
        if ("success".equals(result)) {
            File file = new File(outPutPath);
            if (file.exists()) {
                String mediaId = uploadPoster(file, accessToken);
                replyPosterText(accessToken, openId, codeType);
                reply3(accessToken, openId, mediaId);

                //删除文件
                File qrCode = new File(qrCodePath);
                file.delete();
                qrCode.delete();
            } else {
                return "fail";
            }
        } else {
            return "fail";
        }
        return "success";
    }


    //拼团图文消息
    public static void replyTWMessage(String openId, Integer groupId) {
        String accessToken = WeChatUtil.getAccessToken();
        String title = "仅差2人开课！23个历史圣贤励志故事，足以影响孩子一生！";
        String description = "用小故事讲大道理，给孩子树立品德学习的好榜样！";
        String picurl = "http://mmbiz.qpic.cn/mmbiz_png/PFpVpsjick2joPLV8KkTlMUvGqHicNt5ecavoR1hGPAeyicJVKXfpf0XibDpBxyKSVoDCTtSzyEgWItOCN6KmLDl1w/0?wx_fmt=png";
        String url = "https://saberfrontrelease.rz-edu.cn/group?groupId=" + groupId +"&isMakeUp=2&id=4&loginSource=3" +
                "&grouponSource=1";

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
        newsMessageJSON.setTouser(openId);

        String param = com.alibaba.fastjson.JSONObject.toJSONString(newsMessageJSON);
        com.alibaba.fastjson.JSONObject paramInner = com.alibaba.fastjson.JSONObject.parseObject(param);
        try {
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    //客服消息3
    public static void reply3(String accessToken, String openId, String mediaId) {
        try {
            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"" + mediaId + "\"}}";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("grant_type", "client_credential");
            com.alibaba.fastjson.JSONObject paramInner = com.alibaba.fastjson.JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("客服消息3报错： ", e);
        }
    }

    /**
     *  微信菜单获取海报
     * @param openId
     * @param type
     * @return
     * @throws IOException
     */
    public static String sendActivityPoster(String openId, Integer type) throws IOException {

        //获取用户信息
        String accessToken = WeChatUtil.getAccessToken();
        JSONObject jsonObject = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId, "POST", null);

        //生成uuid作为图片名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        File directoryNew = new File(directory);
        if (!directoryNew.exists()) {
            directoryNew.mkdirs();
        }

        String nickname = jsonObject.getString("nickname");
        String headimgurl = jsonObject.getString("headimgurl");
        String headUrl = headimgurl;
        if(headUrl == null || "".equals(headUrl)){
            headUrl = serverPath + "/img/dxhead.jpg";
        }
        String outPutPath = directory + "POSTER_" + uuid + ".png";

        if (type == 1) {
            //获取用户二维码
            String qrCodePath = getQRCode(directory, openId, uuid, "A", accessToken);
            logger.info("qrCodePath--------------- " + qrCodePath);
            //背景图地址
            ClassPathResource classPathResource = new ClassPathResource("static/poster_background1.png");
            File background = classPathResource.getFile();
            String backgroundPath = background.getPath();

            String result = Image2Pic.overlapImage(backgroundPath, headUrl, qrCodePath, outPutPath);

            if ("success".equals(result)) {
                File file = new File(outPutPath);
                if (file.exists()) {
                    String mediaId = uploadPoster(file, accessToken);
                    reply3(accessToken, openId, mediaId);

                    //删除文件
                    File qrCode = new File(qrCodePath);
                    file.delete();
                    qrCode.delete();
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        } else if (type == 2) {
            //获取用户二维码
            String qrCodePath = getQRCode(directory, openId, uuid, "B", accessToken);
            logger.info("qrCodePath--------------- " + qrCodePath);
            //背景图地址
            ClassPathResource classPathResource = new ClassPathResource("static/poster_background2.png");
            File background = classPathResource.getFile();
            String backgroundPath = background.getPath();

            String result = Image2Pic.overlapImage(backgroundPath, headUrl, qrCodePath, outPutPath);

            if ("success".equals(result)) {
                File file = new File(outPutPath);
                if (file.exists()) {
                    String mediaId = uploadPoster(file, accessToken);
                    reply3(accessToken, openId, mediaId);

                    //删除文件
                    File qrCode = new File(qrCodePath);
                    file.delete();
                    qrCode.delete();
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        }else if (type == 3) {
            //获取用户二维码
            String qrCodePath = getQRCode(directory, openId, uuid, "C", accessToken);
            logger.info("qrCodePath--------------- " + qrCodePath);
            //背景图地址
            ClassPathResource classPathResource = new ClassPathResource("static/poster_background3.png");
            File background = classPathResource.getFile();
            String backgroundPath = background.getPath();

            String result = Image2Pic.overlapImage(backgroundPath, headUrl, qrCodePath, outPutPath);

            if ("success".equals(result)) {
                File file = new File(outPutPath);
                if (file.exists()) {
                    String mediaId = uploadPoster(file, accessToken);
                    //reply1(accessToken, openId, nickname, type);
                    reply3(accessToken, openId, mediaId);
                    reply2(accessToken, openId,nickname);

                    //删除文件
                    File qrCode = new File(qrCodePath);
                    file.delete();
                    qrCode.delete();
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        }

        return "success";
    }

    /**
     *  客服消息
     * @param accessToken
     * @param openId
     * @param stringBuffer 消息内容
     */
    public static void reply(String accessToken, String openId, StringBuffer stringBuffer) {
        try {
            String content = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + stringBuffer.toString() + "\"}}";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("grant_type", "client_credential");

            com.alibaba.fastjson.JSONObject paramInner = com.alibaba.fastjson.JSONObject.parseObject(content);
            HttpClient.httpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, paramInner, true);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("客服消息2报错： ", e);
        }
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
    
    
    
    /**
     *  关键字回复
     * @param openId
     * @param stringBuffer 消息内容
     */
    public static void reply4(String openId, StringBuffer stringBuffer) {
        try {
        	
        	String accessToken = WeChatUtil.getAccessToken();
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
    
    
    public static String groupSend(String openId, String name,String icon)throws IOException{
    	
    	String accessToken = WeChatUtil.getAccessToken();
    	//生成uuid作为图片名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String outPutPath = directory + "POSTER_" + uuid + ".png";
    	  //获取用户二维码
        String qrCodePath = getQRCode(directory, openId, uuid, "C", accessToken);
        logger.info("qrCodePath--------------- " + qrCodePath);
        //背景图地址
        ClassPathResource classPathResource = new ClassPathResource("static/poster_background3.png");
        File background = classPathResource.getFile();
        String backgroundPath = background.getPath();

        String result = Image2Pic.overlapImage(backgroundPath, icon, qrCodePath, outPutPath);
      //  reply2(accessToken, openId,name);
        if ("success".equals(result)) {
            File file = new File(outPutPath);
            if (file.exists()) {
                String mediaId = uploadPoster(file, accessToken);
                //reply1(accessToken, openId, nickname, type);
                reply3(accessToken, openId, mediaId);
                reply2(accessToken, openId,name);

                //删除文件
                File qrCode = new File(qrCodePath);
                file.delete();
                qrCode.delete();
            } else {
                return "fail";
            }
        } else {
            return "fail";
        }

    return "success";
}
    	

}
