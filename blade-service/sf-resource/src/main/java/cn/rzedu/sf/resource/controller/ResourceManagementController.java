package cn.rzedu.sf.resource.controller;



import cn.rzedu.sf.resource.service.IResourceManagementService;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import cn.rzedu.sf.resource.vo.ProgramaManagementVO;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.feign.EntityFileClient;
import org.springblade.resource.vo.FileResult;
import org.springblade.resource.vo.VodResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.ws.rs.FormParam;
import java.io.*;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/resourceManagement")
@Api(value = "资源管理接口", tags = "资源管理")
public class ResourceManagementController {

    private IResourceManagementService resourceManagementService;
    private EntityFileClient entityFileClient;
    /**
     * 栏目列表
     */
    @GetMapping("/selectProgramList/{subject}")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "栏目列表")
    public R<IPage<ProgramaManagementVO>> selectProgramList(Query query,
            @ApiParam(value = "subject 72=硬笔，71=软笔",required = true)@PathVariable(value = "subject") Integer subject,
            @ApiParam(value = "mediaType",required = true)@RequestParam(value = "mediaType") Integer mediaType
            ) {
        IPage<ProgramaManagementVO> pages = resourceManagementService.selectProgramList(Condition.getPage(query),subject,mediaType);
        return R.data(pages);
    }

    @PostMapping("/updateSort")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "栏目编辑")
    public R updateSort(@Valid @RequestBody ProgramaManagementVO programaManagementVO) {
        String sortName = programaManagementVO.getSortName();
        Integer subject = programaManagementVO.getSubject();
        Integer mediaType = programaManagementVO.getMediaType();
        Integer sortId = programaManagementVO.getSortId();
        return R.status(resourceManagementService.updateSort(sortName,subject,mediaType,sortId));
    }

    @PostMapping("/addSort")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新建栏目")
    public R addSort(@Valid @RequestBody ProgramaManagementVO programaManagementVO) {
        String sortName = programaManagementVO.getSortName();
        Integer subject = programaManagementVO.getSubject();
        Integer mediaType = programaManagementVO.getMediaType();
        return R.status(resourceManagementService.addSort(sortName,mediaType,subject));
    }

    /**
     * 批量删除
     * @param resourceIds
     * @return
     */
    @PostMapping("/removeSort")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "栏目批量删除")
    public R removeSort(@ApiParam @RequestBody List<Integer> resourceIds){
        return R.status(resourceManagementService.removeSort(resourceIds));
    }

    /**
     * 栏目列表
     */
    @GetMapping("/selectResourceList/{subject}")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "资源管理列表")
    public R<IPage<MediaResourceVO>> selectResourceList(
            Query query,
            @ApiParam(value = "subject 72=硬笔，71=软笔",required = true)@PathVariable(value = "subject") Integer subject,
            @ApiParam(value = "mediaType",required = true)@RequestParam(value = "mediaType") Integer mediaType,
            @ApiParam(value = "title")@RequestParam(value = "title",required = false) String title,
            @ApiParam(value = "sortId")@RequestParam(value = "sortId",required = false) Integer sortId
    ) {
        IPage<MediaResourceVO> pages = resourceManagementService.selectResourceList(Condition.getPage(query),subject,mediaType,title,sortId);
        return R.data(pages);
    }


    /**
     * 查看
     * @param resourceId
     * @return
     */
    @GetMapping("/resourceDetail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "资源查看")
    public R<MediaResourceVO> selectResourceDetail(
            @ApiParam(value = "resourceId")@RequestParam(value = "resourceId") Integer resourceId
    ) {
        return R.data(resourceManagementService.selectResourceDetail(resourceId));
    }

    @PostMapping("/updateResource")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "资源编辑")
    public R updateResource(@RequestParam(value = "multipartFile",required = false) MultipartFile multipartFile,
                            @RequestParam("resourceId") Integer resourceId,
                            @RequestParam(value = "objectType",required = false)String objectType,
                            @RequestParam(value = "suffix",required = false)String suffix,
                            @RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "sortId",required = false) Integer sortId) throws IOException {
        String uuid = null;
        String coverImgUrl = null;
        VodResult vodResult = null;
        FileResult fileResult = null;
            if(objectType.equals("audio") || objectType.equals("video")){
                vodResult = entityFileClient.uploadVodMultipartFile(multipartFile);
                uuid = vodResult.getUuid();
                GetVideoInfoResponse response = entityFileClient.findVideoByUuid(uuid);
                coverImgUrl = response.getVideo().getCoverURL();
            }else {
                fileResult = entityFileClient.uploadOssMultipartFile(multipartFile);
                uuid = fileResult.getUuid();
            }

        return R.status(resourceManagementService.updateResource(title,sortId,uuid,coverImgUrl,objectType,suffix,resourceId));
    }

    /**
     * 批量删除
     * @param resourceIds
     * @return
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "资源批量删除")
    public R remove(@ApiParam @RequestBody List<Integer> resourceIds){
        return R.status(resourceManagementService.deleteResource(resourceIds));
    }

    /**
     * 上传资源
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadResource")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "上传资源")
    public R uploadResource(@RequestParam("multipartFile") MultipartFile multipartFile,
                            String objectType,
                            String suffix,
                            String title,
                            Integer sortId,
                            Integer subject,
                            Integer mediaType) throws IOException {
        String uuid = null;
        String coverImgUrl = null;
        EntityFile entityFile = null;
        VodResult vodResult = null;
        FileResult fileResult = null;

        if(objectType.equals("audio") || objectType.equals("video")){
            vodResult = entityFileClient.uploadVodMultipartFile(multipartFile);
            uuid = vodResult.getUuid();
            GetVideoInfoResponse response = entityFileClient.findVideoByUuid(uuid);
            coverImgUrl = response.getVideo().getCoverURL();
        }else {
            fileResult = entityFileClient.uploadOssMultipartFile(multipartFile);
            uuid = fileResult.getUuid();
        }
        return R.status(resourceManagementService.addResource(title,subject,sortId,objectType,suffix,uuid,coverImgUrl,mediaType));
    }

}
