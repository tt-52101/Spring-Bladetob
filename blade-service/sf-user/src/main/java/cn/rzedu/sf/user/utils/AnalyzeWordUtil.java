package cn.rzedu.sf.user.utils;

import cn.rzedu.sf.user.vo.AnalyzeWordHard;
import cn.rzedu.sf.user.vo.AnalyzeWordSoft;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.springblade.common.tool.HttpUtils;
import org.springblade.common.tool.ImgUtil;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三方测字封装
 * @author
 */
public class AnalyzeWordUtil {

    /** 硬笔测试接口路径 */
    private static final String CEZI_HARD_URL = "http://42.194.144.72:80/api/v1/copybook/hardpen/analyze";
    /** 软笔测试接口路径 */
    private static final String CEZI_SOFT_URL = "http://42.194.144.72:80/api/v1/copybook/analyze";
    /** 下载标准字库文字图像路径 */
    private static final String DOWNLOAD_URL = "http://42.194.144.72/download?path=";
    /** 暗物智能接口调用token */
    private static final String CEZI_TOKEN = "PqMrQlkc5FLgSdBlJCjHffMxzi1MoMcy";
    /** 文件暂存目录 */
    private static final String DIRECTORY = "/home/";

    /**
     * 调用硬笔测评接口
     * @param base64
     * @return
     */
    public static AnalyzeWordHard writingAnalyzeHard(String base64) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("token", CEZI_TOKEN);
            headers.put("Content-Type", "application/json");
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("image", base64);
//            System.out.println("base64:" + base64);
            Connection.Response response = HttpUtils.post(CEZI_HARD_URL, headers, "{\"image\": \"" + base64 + "\"}");
//            System.out.println("responseBody:" +response.body());
            JSONObject object = JSONObject.parseObject(response.body());
            Integer errCode = Integer.parseInt(String.valueOf(object.get("err_code")));
            if (errCode == 0) {
                AnalyzeWordHard data = JSONObject.toJavaObject(object.getJSONObject("data"), AnalyzeWordHard.class);
                data.setUploadImage(base64);
                data.setOriginalImage(downloadOriginalImg(data.getOriginalData()));
                return data;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用软笔测评接口
     * @param base64
     * @return
     */
    public static AnalyzeWordSoft writingAnalyzeSoft(String base64) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("token", CEZI_TOKEN);
            headers.put("Content-Type", "application/json");
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("image", base64);
            Connection.Response response = HttpUtils.post(CEZI_SOFT_URL, headers, "{\"image\": \"" + base64 + "\"}");
            JSONObject object = JSONObject.parseObject(response.body());
            Integer errCode = Integer.parseInt(String.valueOf(object.get("err_code")));
            if (errCode == 0) {
                AnalyzeWordSoft data = JSONObject.toJavaObject(object.getJSONObject("data"), AnalyzeWordSoft.class);
                data.setUploadImage(base64);
                data.setOriginalImage(batchDownloadOriginalImg(data.getOriginalData()));
                return data;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AI测评上传的图片-硬笔
     * @param file
     * @return
     */
    public static AnalyzeWordHard writingAnalyzeHard(MultipartFile file) {
        String base64 = ImgUtil.generateBase64(file);
        return writingAnalyzeHard(base64);
    }

    /**
     * AI测评上传的图片-软笔
     * @param file
     * @return
     */
    public static AnalyzeWordSoft writingAnalyzeSoft(MultipartFile file) {
        String base64 = ImgUtil.generateBase64(file);
        return writingAnalyzeSoft(base64);
    }

    /**
     * 下载标准字图片并转换为base64图片
     * @param url
     * @return
     */
    public static String downloadOriginalImg(String url) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("token", CEZI_TOKEN);
            Connection.Response response = HttpUtils.get(DOWNLOAD_URL + url, headers);
            //返回结果
            byte[] bytes = response.bodyAsBytes();

            //文件存放目录
            String directory = DIRECTORY;
            //文件名
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            String filePath = directory + fileName;

            //下载文件
            File file = new File(filePath);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();

            //转换格式
            InputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), null, inputStream);

            String base64 = ImgUtil.generateBase64(multipartFile);
            //删除文件
            if (file.exists()) {
                file.delete();
            }
            return base64;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> batchDownloadOriginalImg(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>(list.size());
        for (String url : list) {
            result.add(downloadOriginalImg(url));
        }
        return result;
    }
}
