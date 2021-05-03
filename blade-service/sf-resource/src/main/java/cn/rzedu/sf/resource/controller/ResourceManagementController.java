package cn.rzedu.sf.resource.controller;



import cn.rzedu.sf.resource.service.IResourceManagementService;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import cn.rzedu.sf.resource.vo.ProgramaManagementVO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    public R<List<ProgramaManagementVO>> selectProgramList(
            @ApiParam(value = "subject 72=硬笔，71=软笔",required = true)@PathVariable(value = "subject") Integer subject,
            @ApiParam(value = "mediaType",required = true)@RequestParam(value = "mediaType") Integer mediaType
            ) {
        return R.data(resourceManagementService.selectProgramList(subject,mediaType));
    }

    @PostMapping("/updateSort")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "编辑")
    public R updateSort(@Valid @RequestBody ProgramaManagementVO programaManagementVO) {
        String sortName = programaManagementVO.getSortName();
        Integer subject = programaManagementVO.getSubject();
        Integer mediaType = programaManagementVO.getMediaType();
        return R.status(resourceManagementService.updateSort(sortName,subject,mediaType));
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
            @ApiParam(value = "sortName")@RequestParam(value = "sortName",required = false) String sortName
    ) {
        IPage<MediaResourceVO> pages = resourceManagementService.selectResourceList(Condition.getPage(query),subject,mediaType,title,sortName);
        return R.data(pages);
    }


    /**
     * 查看
     * @param resourceId
     * @return
     */
    @GetMapping("/resourceDetail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "查看")
    public R<MediaResourceVO> selectResourceDetail(
            @ApiParam(value = "resourceId")@RequestParam(value = "resourceId") Integer resourceId
    ) {
        return R.data(resourceManagementService.selectResourceDetail(resourceId));
    }

    @PostMapping("/updateResource")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "编辑")
    public R updateResource(@Valid @RequestBody MediaResourceVO mediaResourceVO,
                            @RequestParam(required = false) MultipartFile multipartFile) throws IOException {
        String title = mediaResourceVO.getTitle();
        Integer sortId = mediaResourceVO.getSortId();
        Integer resourceId = mediaResourceVO.getId();
        String uuid = null;
        String coverImgUrl = null;

        if(!multipartFile.isEmpty()){
            File file = null;
            EntityFile entityFile = null;
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split(".");
            file=File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            if(filename[1].equals("audio") || filename[1].equals("video")){
                entityFile = entityFileClient.upload(file);
                uuid = entityFile.getUuid();
                coverImgUrl = entityFile.getThumbnailUrl();
            }else {
                entityFile = entityFileClient.uploadImage(file);
                uuid = entityFile.getUuid();
            }

            file.deleteOnExit();
        }

        return R.status(resourceManagementService.updateResource(title,sortId,uuid,coverImgUrl,resourceId));
    }

    /**
     * 批量删除
     * @param resourceIds
     * @return
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "批量删除")
    public R remove(@ApiParam @RequestParam List<Integer> resourceIds){
        return R.status(resourceManagementService.deleteResource(resourceIds));
    }

    /**
     * 上传资源
     * @param mediaResourceVO
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadResource")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "上传资源")
    public R uploadResource(@Valid @RequestBody MediaResourceVO mediaResourceVO,
                            @RequestParam MultipartFile multipartFile) throws IOException {
        String title = mediaResourceVO.getTitle();
        Integer subject = mediaResourceVO.getSubject();
        Integer sortId = mediaResourceVO.getSortId();
        String objectType = mediaResourceVO.getObjectType();
        String suffix = null;
        String uuid = null;
        String coverImgUrl = null;
        Integer mediaType = mediaResourceVO.getMediaType();
        File file = null;
        EntityFile entityFile = null;
        String originalFilename = multipartFile.getOriginalFilename();
        String[] filename = originalFilename.split(".");
        suffix = filename[1];
        file=File.createTempFile(filename[0], filename[1]);
        multipartFile.transferTo(file);
        if(objectType.equals("audio") || objectType.equals("video")){
            entityFile = entityFileClient.upload(file);
            objectType = filename[1];
            uuid = entityFile.getUuid();
            coverImgUrl = entityFile.getThumbnailUrl();
        }else {
            entityFile = entityFileClient.uploadImage(file);
            uuid = entityFile.getUuid();
        }

        return R.status(resourceManagementService.addResource(title,subject,sortId,objectType,suffix,uuid,coverImgUrl,mediaType));
    }

}
