package cn.rzedu.sf.resource.controller;



import cn.rzedu.sf.resource.service.IResourceManagementService;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import cn.rzedu.sf.resource.vo.ProgramaManagementVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/resourceManagement")
@Api(value = "资源管理接口", tags = "资源管理")
public class ResourceManagementController {

    private IResourceManagementService resourceManagementService;
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

}
