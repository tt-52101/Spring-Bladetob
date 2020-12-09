package org.springblade.resource.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadImageImpl;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadImageRequest;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadImageResponse;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.resource.vo.VodResult;

import java.io.InputStream;
import java.util.List;

public class VodUploadUtil {

    //初始化
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 获取视频上传地址和凭证
     *
     * @param client 发送请求客户端
     * @return CreateUploadVideoResponse 获取视频上传地址和凭证响应数据
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle("this is a sample");
        request.setFileName("filename.mp4");
        //UserData，用户自定义设置参数，用户需要单独回调URL及数据透传时设置(非必须)
        //JSONObject userData = new JSONObject();
        //UserData回调部分设置
        //JSONObject messageCallback = new JSONObject();
        //messageCallback.put("CallbackURL", "http://xxxxx");
        //messageCallback.put("CallbackType", "http");
        //userData.put("MessageCallback", messageCallback.toJSONString());
        //UserData透传数据部分设置
        //JSONObject extend = new JSONObject();
        //extend.put("MyId", "user-defined-id");
        //userData.put("Extend", extend.toJSONString());
        //request.setUserData(userData.toJSONString());
        return client.getAcsResponse(request);
    }

    /**
     * 刷新视频上传凭证
     *
     * @param client 发送请求客户端
     * @return RefreshUploadVideoResponse 刷新视频上传凭证响应数据
     * @throws Exception
     */
    public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient client) throws Exception {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId("VideoId");
        return client.getAcsResponse(request);
    }

    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(String accessKeyId, String accessKeySecret, String videoId) throws Exception {
        DefaultAcsClient client = VodUploadUtil.initVodClient(accessKeyId, accessKeySecret);

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    /**
     * 流式上传接口
     *
     * @param title
     * @param fileName
     * @param inputStream
     */
    public static UploadStreamResponse uploadStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setShowWaterMark(true);
        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
        //request.setCallback("http://callback.sample.com");
        /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        request.setStorageLocation("outin-0afb5dc1ca6311eaaa1300163e06123c.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return response;
    }

    /**
     * 图片上传接口，流式上传示例（支持文件流和网络流）
     * 参数参考文档 https://help.aliyun.com/document_detail/55619.html
     */
    public static UploadImageResponse uploadImageStream(String accessKeyId, String accessKeySecret, InputStream inputStream) {
        /* 图片类型（必选）取值范围：default（默认)，cover（封面），watermark（水印）*/
        String imageType = "default";
        UploadImageRequest request = new UploadImageRequest(accessKeyId, accessKeySecret, imageType);
        /* 图片文件扩展名（可选）取值范围：png，jpg，jpeg */
        //request.setImageExt("png");
        /* 图片标题（可选）长度不超过128个字节，UTF8编码 */
        //request.setTitle("图片标题");
        /* 图片标签（可选）单个标签不超过32字节，最多不超过16个标签，多个用逗号分隔，UTF8编码 */
        //request.setTags("标签1,标签2");
        /* 存储区域（可选）*/
        request.setStorageLocation("outin-0afb5dc1ca6311eaaa1300163e06123c.oss-cn-shanghai.aliyuncs.com");
        /* 流式上传时，InputStream为必选，fileName为源文件名称，如:文件名称.png(可选)*/
        //request.setFileName("测试文件名称.png");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        // 1.文件流上传
        // InputStream fileStream = getFileStream(request.getFileName());
        // if (fileStream != null) {
        //     request.setInputStream(fileStream);
        // }

        // 2.网络流上传
//        String url = "http://test.aliyun.com/image/default/test.png";
//        InputStream urlStream = getUrlStream(url);
//        if (urlStream != null) {
//            request.setInputStream(urlStream);
//        }
        request.setInputStream(inputStream);

        // 开始上传图片
        UploadImageImpl uploadImage = new UploadImageImpl();
        UploadImageResponse response = uploadImage.upload(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.print("ImageId=" + response.getImageId() + "\n");
            System.out.print("ImageURL=" + response.getImageURL() + "\n");
        } else {
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return response;
    }

    /**
     * 获取视频信息
     * @param client 发送请求客户端
     * @return GetVideoInfoResponse 获取视频信息响应数据
     * @throws Exception
     */
    public static GetVideoInfoResponse getVideoInfo(DefaultAcsClient client, String videoId) throws Exception {
        GetVideoInfoRequest request = new GetVideoInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }
}
