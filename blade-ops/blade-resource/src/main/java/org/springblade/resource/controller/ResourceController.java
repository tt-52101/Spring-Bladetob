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
package org.springblade.resource.controller;

import static org.springblade.resource.utils.VodUploadUtil.initVodClient;

import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.FileUtil;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.service.IEntityFileService;
import org.springblade.resource.utils.FileMd5Util;
import org.springblade.resource.utils.VodUploadUtil;
import org.springblade.resource.vo.VodResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.vod.upload.resp.UploadImageResponse;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;

/**
 * 对象存储端点
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vod/resource")
@Api(value = "阿里云点播API", tags = "阿里云点播API")
public class ResourceController {

	private RedisUtil redisUtil;
    private final static Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private IEntityFileService entityFileService;
    private OssProperties ossProperties;

    
//    private ICharacterService characterService;


    /**
     * 上传文件 - 阿里云视频点播
     *
     * @param file 文件
     * @return
     */
    @SneakyThrows
    @PostMapping("/put-file")
    @ApiOperation(value = "上传文件", notes = "点播服务上传文件接口", position = 1)
    public R<VodResult> putFile(@RequestParam MultipartFile file) {
        VodResult vodResult = new VodResult();

        String md5 = FileMd5Util.getFileMd5(file.getInputStream(), 5);
        EntityFile entityFile = entityFileService.findFileByMD5(md5);

        if (entityFile == null) {
            entityFile = new EntityFile();
            UploadStreamResponse response = VodUploadUtil.uploadStream(ossProperties.getAccessKey(), ossProperties.getSecretKey(), file.getOriginalFilename(), file.getOriginalFilename(), file.getInputStream());
            GetPlayInfoResponse getPlayInfoResponse = new GetPlayInfoResponse();
            vodResult.setRequestId(response.getRequestId());
            if (response.isSuccess()) {
                entityFile.setFileName(file.getOriginalFilename());
                entityFile.setRealFileName(file.getOriginalFilename());
                entityFile.setMd5Code(md5);
                entityFile.setSuffix(FileUtil.getFileExtension(file.getOriginalFilename()));
                entityFile.setSize((double) file.getSize());
                entityFile.setUuid(response.getVideoId());
                entityFile.setModifyDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                entityFile.setCreateDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                entityFileService.save(entityFile);

                vodResult.setUuid(response.getVideoId());
            } else {
                vodResult.setErrorCode(response.getCode());
                vodResult.setErrorMessage(response.getMessage());
            }
        } else { //已存在，根据文件id查询url
            vodResult.setUuid(entityFile.getUuid());
        }

        return R.data(vodResult);
    }

    /**
     * 查找文件
     *
     * @param uuid
     * @param type 文件类型 1=视频 2=图片
     * @return
     */
//    @SneakyThrows
//    @PostMapping("/find-file-by-uuid")
//    @ApiOperation(value = "查找文件", notes = "查找文件通过uuid", position = 2)
//    public R<VodResult> findFileByUuid(@ApiParam(value = "文件ID", required = true) @RequestParam(value = "uuid") String uuid,
//                                       @ApiParam(value = "文件类型 1=视频 2=图片", required = true) @RequestParam(value = "type")Integer type) {
//        VodResult vodResult = new VodResult();
//        if (type == 1) {
//            GetPlayInfoResponse response = new GetPlayInfoResponse();
//            try {
//                response = VodUploadUtil.getPlayInfo(ossProperties.getAccessKey(), ossProperties.getSecretKey(), uuid);
//                List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
//                //播放地址
//                vodResult.setVideoUrls(playInfoList);
//                for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
//                    System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
//                }
//                //Base信息
//                vodResult.setVideoBase(response.getVideoBase());
//                System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
//            } catch (Exception e) {
//                System.out.print("ErrorMessage = " + e.getLocalizedMessage());
//                vodResult.setErrorMessage(e.getLocalizedMessage());
//            }
//            System.out.print("RequestId = " + response.getRequestId() + "\n");
//            vodResult.setRequestId(response.getRequestId());
//        } else if (type == 2) {
//            EntityFile entityFile = entityFileService.findFileByUuid(uuid);
//            vodResult.setImageUrl(uuid);
//            vodResult.setImageUrl(entityFile.getUrl());
//        }
//        return R.data(vodResult);
//    }

    /**
     * 根据videoId返回视频数据
     *
     * @param uuid
     * @return
     */
    @SneakyThrows
    @PostMapping("/find-file-by-uuid")
    @ApiOperation(value = "根据videoId返回视频数据", notes = "根据videoId返回视频数据", position = 2)
    public R<GetVideoInfoResponse> findFileByUuid(@ApiParam(value = "videoId", required = true) @RequestParam(value = "uuid") String uuid,
                                                  @ApiParam(value = "资源ID") @RequestParam(value = "resourceId") Integer resourceId,
                                                  @ApiParam(value = "subject,71 = 软笔,72=硬笔") @RequestParam(value = "subject") Integer subject,
                                                  @ApiParam(value = "mediaType") @RequestParam(value = "mediaType") Integer mediaType
                                                  ) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Integer userId =Integer.parseInt(request.getHeader("userId")) ;
        String username = request.getHeader("username");
        if (resourceId != null && subject!=null && mediaType != null){
            entityFileService.saveBrowsingHistory(userId,username,resourceId,subject,mediaType);
        }

        DefaultAcsClient client = initVodClient(ossProperties.getAccessKey(), ossProperties.getSecretKey());
        GetVideoInfoResponse response = new GetVideoInfoResponse();
        try {
            response = VodUploadUtil.getVideoInfo(client, uuid);
            System.out.print(JSON.toJSONString(response));
            System.out.print("Title = " + response.getVideo().getTitle() + "\n");
            System.out.print("Description = " + response.getVideo().getDescription() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

        return R.data(response);
    }

    /**
     * 根据videoId返回视频数据
     *
     * @param videoId
     * @return
     */
    @SneakyThrows
    @GetMapping("/find-playinfo-by-videoid")
    @ApiOperation(value = "根据videoId返回视频数据", notes = "根据videoId返回视频数据", position = 4)
    public R<GetPlayInfoResponse> getPlayInfo(@ApiParam(value = "videoId", required = true) @RequestParam(value = "videoId") String videoId) {
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = VodUploadUtil.getPlayInfo(ossProperties.getAccessKey(), ossProperties.getSecretKey(), videoId);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        return R.data(response);
    }

    /**
     * 根据videoId返回视频数据
     *
     * @param videoId
     * @return
     */
    @SneakyThrows
    @PostMapping("/find-video-by-uuid")
    @ApiOperation(value = "根据videoId返回视频数据及playauth", notes = "根据videoId返回视频数据及playauth", position = 3)
    public R<GetVideoPlayAuthResponse> findVideoByUuid(@ApiParam(value = "videoId", required = true) @RequestParam(value = "videoId") String videoId) {
    	GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
        	Object object = redisUtil.get("video_"+videoId);
        	//logger.info("缓存内容："+JSON.toJSONString(object));
        	if(object != null){
        		String json = object.toString();
        		logger.info("json内容："+JSON.toJSONString(json));
        		response = JSONObject.parseObject(json,GetVideoPlayAuthResponse.class);
        		logger.info("缓存内容："+JSON.toJSONString(response));
        		return R.data(response);
        	}
        	
        	response = getVideo(videoId);
            redisUtil.set("video_"+videoId, JSON.toJSONString(response), 2000);
            logger.info("非缓存内容："+JSON.toJSONString(response));
           
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
    	
        return R.data(response);
    }

    private GetVideoPlayAuthResponse getVideo(String videoId){
        // 创建SubmitMediaInfoJob实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile("cn-Shanghai", ossProperties.getAccessKey(), ossProperties.getSecretKey() );
        IAcsClient client = new DefaultAcsClient(profile);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        request.setAuthInfoTimeout(3000L);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
           // logger.info(videoId);
           // logger.info(JSON.toJSONString(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            logger.error("ErrCode:" + e.getErrCode());
            logger.error("ErrMsg:" + e.getErrMsg());
            logger.error("RequestId:" + e.getRequestId());
//            if("Forbidden.IllegalStatus".equals(e.getErrCode())){
//                return getVideo(videoId);
//            }
        }

        return response;
    }

   // public static void main(String[] args) {
       /* int i = addDigits(38);
        System.out.println(i);*/
        // 创建SubmitMediaInfoJob实例并初始化
//        DefaultProfile profile = DefaultProfile.getProfile(
//                "cn-Shanghai",                // 点播服务所在的地域ID，中国大陆地域请填cn-shanghai
//                "LTAIV7VpvFzcnGvT",        // 您的AccessKey ID
//                "auxYY7Uj3nw2lYd1sdPwACNOcWgrau" );    // 您的AccessKey Secret
//        IAcsClient client = new DefaultAcsClient(profile);
//        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
//        // 视频ID。可以通过GetVideoList接口查询得到
//        request.setVideoId("07e552acd593471381d218b2f7adec2b");
//        try {
//            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            System.out.println("ErrCode:" + e.getErrCode());
//            System.out.println("ErrMsg:" + e.getErrMsg());
//            System.out.println("RequestId:" + e.getRequestId());
//        }
   // }

    public static int addDigits(int num) {
        int tempInt = num;
        if (num > 10) {
            num--;
            return addDigits(num);
        }
        int result = num;
        return result;
    }
//   public static void main(String[] args) {
//
//	   String json ="{\"playAuth\":\"eyJTZWN1cml0eVRva2VuIjoiQ0FJUzF3SjFxNkZ0NUIyeWZTaklyNURQT2RUd2pvaEw3cHE3TkV2RnRrVVBRc3BKMi9IaGhUejJJSGhKZVhOdkJPMGV0ZjQrbVdCWTdQY1lscnNvRThJYUdSMmZOWTVvNnAxR3pRU2lib3laVDBJSmFSeEMydk9mQW1HMkowUFJMNmF3Q3J5THNicS9GOTZwYjFmYjdDd1JwWkx4YVRTbFdYRzhMSlNOa3VRSlI5OExYdzYrSHdFa1k4bFJLbGxOcGRNaU1YTEFGUENqTlh5UW5HM2NYbVZscGpSOWhXNTR3cU81ek15VGlIemJrRWFvOHVzY3RvbnJDNFc0YjZzSU9ZdGtBZSt4MWYxWGNLVEdza2hWOXdNWTJLcDlscjFjNVJUS3I2dnFZVDlyN2c2Qkx2RGYvL0IyTVFaOWZkSmFJYU5mcStYbW52QUswWTY2MWFhUGtrb1NaNzRORG42QUh0bndtSnVWUnRuRWJJaHBLZXpKWEYzV3ljMktPNVhQdEFjcFhHa1dMZ3NpZWFCNmNpSXRVME54RjJtQmNQNzZxQUNRTTEyNU40R0IwYkFyMTZSeXcyN2g5TUd4TzBPMVJMR1V3ZTZSdnlQNjZ1VkNHb0FCY04zVkJNVDNJcEgrc0dma0ExVUsvTkU3TzBDVmxKTTF6OW01SVd1ejVOSHQzbXNrREZnMGdYdjcvMG9ZZTN0RU1NbGhVMStkKzVDbjhRTjIrS0N1NUFnSjNISXVSOG8xTmZWa1F6ZXhqakNJcXJ5dG9DakxVcFpycW9vN2c2b3grK3diY3NrNUk5ajM1M2hrYVR3Nk5KcjBuNEpkSk1zUFhEdjcyeEF0bkIwPSIsIkF1dGhJbmZvIjoie1wiQ0lcIjpcIllrVkcwZmc5Q1ZtWjNuWjBVSkErVzJMdEtsMWtPRTIyU2ZNVkdpK2NnWEk9XCIsXCJDYWxsZXJcIjpcIlp5OUNuNnJ1M2szVno5QnF4NnAyU3c9PVwiLFwiRXhwaXJlVGltZVwiOlwiMjAyMC0wOS0xNFQxNjowODoyN1pcIixcIk1lZGlhSWRcIjpcIjRhMTQ5ZWYwZGVlYTQyZWRiNDYyOGQ1N2FjOWYyMGNlXCIsXCJQbGF5RG9tYWluXCI6XCJjZG5zZi5yei1lZHUuY25cIixcIlNpZ25hdHVyZVwiOlwiS0FRRXV6bkVMNWxhc3VNZEFaeFRkU1NPUHVZPVwifSIsIlZpZGVvTWV0YSI6eyJTdGF0dXMiOiJOb3JtYWwiLCJWaWRlb0lkIjoiNGExNDllZjBkZWVhNDJlZGI0NjI4ZDU3YWM5ZjIwY2UiLCJUaXRsZSI6IkNCQjc0OEU5MEJDQzQwNEFBNTA4NUZEMDIxOThFQzUxRTFBQl8xMDAxX3RpYW5fOC5tcDQiLCJDb3ZlclVSTCI6Imh0dHBzOi8vY2Ruc2YucnotZWR1LmNuLzRhMTQ5ZWYwZGVlYTQyZWRiNDYyOGQ1N2FjOWYyMGNlL3NuYXBzaG90cy8zZTUxMDc0YjE2NTk0MDZiOGVmOTRkMjllZTVlNTY4NS0wMDAwMy5qcGciLCJEdXJhdGlvbiI6MzEuMzJ9LCJBY2Nlc3NLZXlJZCI6IlNUUy5OU3pybkRjV2pZWFA2bXRSRVpORWU0NEpnIiwiUGxheURvbWFpbiI6ImNkbnNmLnJ6LWVkdS5jbiIsIkFjY2Vzc0tleVNlY3JldCI6IjhUWXQ1NVF0UENCcmR3V0R4cHFKWjhSeFR0b3Zxb3p1NUxOOUdYdnN3VFBFIiwiUmVnaW9uIjoiY24tc2hhbmdoYWkiLCJDdXN0b21lcklkIjozMDU3NDkzMH0=\",\"requestId\":\"72752674-2EE1-4924-814B-E8E3EA50FCCB\",\"videoMeta\":{\"coverURL\":\"https://cdnsf.rz-edu.cn/4a149ef0deea42edb4628d57ac9f20ce/snapshots/3e51074b1659406b8ef94d29ee5e5685-00003.jpg\",\"duration\":31.32,\"status\":\"Normal\",\"title\":\"CBB748E90BCC404AA5085FD02198EC51E1AB_1001_tian_8.mp4\",\"videoId\":\"4a149ef0deea42edb4628d57ac9f20ce\"}}";
//	    //json = JSON.toJSONString(json);
//	   GetVideoPlayAuthResponse response = JSONObject.parseObject(json,GetVideoPlayAuthResponse.class);
//		logger.info("缓存内容："+JSON.toJSONString(response));
//}

}
