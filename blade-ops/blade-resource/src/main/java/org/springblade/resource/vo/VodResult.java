package org.springblade.resource.vo;

import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import lombok.Data;

import java.util.List;

/**
 * 点播服务文件上传、查询结果
 */
@Data
public class VodResult {

    private String requestId;
    private String name;

    private String uuid;
    private List<GetPlayInfoResponse.PlayInfo> videoUrls;
    private GetPlayInfoResponse.VideoBase videoBase;

    private String imageId;
    private String imageUrl;

    private String errorCode;
    private String errorMessage;

}
