package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.user.entity.*;
import cn.rzedu.sf.user.service.*;
import cn.rzedu.sf.user.vo.GcItemVO;
import cn.rzedu.sf.user.vo.TeacherVO;
import cn.rzedu.sf.user.vo.TeamVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 基础数据-后台
 * 控制器
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/basic")
@Api(value = " 基础数据-后台 ", tags = "基础数据-后台")
public class BasicDataController extends BladeController {

    private IGcItemService gcItemService;

    private IRegionService regionService;

    private ISchoolService schoolService;

    private ITeamTypeService teamTypeService;

    private ITeamService teamService;

    private ITeamStudentService teamStudentService;

    private ITeamTeacherService teamTeacherService;

    private IStudentService studentService;

    private ITeacherService teacherService;

    private IHomeworkService homeworkService;

    private IHomeworkUserRecordService homeworkUserRecordService;

    private IHomeworkUserCommentService homeworkUserCommentService;


    /**
     * 区域-分页
     */
    @GetMapping("/region/page")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "区域-分页", notes = "传入region")
    public R<IPage<Region>> regionPage(Region region, Query query) {
        IPage<Region> pages = regionService.selectRegionPage(Condition.getPage(query), region);
        return R.data(pages);
    }

    /**
     * 区域-列表
     */
    @GetMapping("/region/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "区域-列表", notes = "根据父级id和层级查询，默认查询level=1，parent=0的省")
    public R<List<Region>> regionList(
            @ApiParam(value = "父级code", required = true) @RequestParam(value = "parent", defaultValue = "0") Integer parent,
            @ApiParam(value = "级别：1=省；2=市；3=区县") @RequestParam(value = "level", required = false) Integer level) {
        List<Region> list = regionService.findRegionList(parent, level);
        return R.data(list);
    }

    /**
     * 区域-新增或修改
     */
    @PostMapping("/region/submit")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "区域-新增或修改", notes = "必填项：code，name。选填项：parent，不填默认0，即变动数据为市级地区")
    public R regionSubmit(@Valid @RequestBody Region region) {
        return R.status(regionService.saveOrUpdateRegion(region));
    }

    /**
     * 区域-删除
     */
    @PostMapping("/region/remove")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "区域-删除", notes = "传入ids")
    public R regionRemove(
            @ApiParam(value = "区域代码", required = true) @RequestParam String code,
            @ApiParam(value = "是否删除子区域", required = true) @RequestParam(defaultValue = "0") Integer delChildren) {
        return R.status(regionService.removeRegion(code, delChildren));
    }


    /**
     * 基础代码-列表
     */
    @GetMapping("/gc/list")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "基础代码-列表", notes = "根据基础代码类型code获取数据")
    public R<List<GcItemVO>> gcList(
            @ApiParam(value = "基础代码类型", required = true) @RequestParam String code) {
        List<GcItemVO> list = gcItemService.findByCode(code);
        return R.data(list);
    }

    /**
     * 基础代码-获取代码项名称
     */
    @GetMapping("/gc/name/get")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "基础代码-获取代码项名称", notes = "根据基础代码类型code和值获取代码名")
    public R<String> getGcName(
            @ApiParam(value = "基础代码类型", required = true) @RequestParam String code,
            @ApiParam(value = "代码项值", required = true) @RequestParam String value
    ) {
        String itemName = gcItemService.getItemName(code, value);
        return R.data(itemName);
    }

    /**
     * 基础代码-获取代码项名
     */
    @GetMapping("/gc/value/get")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "基础代码-获取代码项值", notes = "根据基础代码类型code和名称获取代码值")
    public R<String> getGcValue(
            @ApiParam(value = "基础代码类型", required = true) @RequestParam String code,
            @ApiParam(value = "代码项名", required = true) @RequestParam String name
    ) {
        String itemValue = gcItemService.getItemValue(code, name);
        return R.data(itemValue);
    }

    /**
     * 基础代码-删除
     */
    @PostMapping("/gc/remove")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "基础代码-删除", notes = "传入ids")
    public R schoolTypeRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(gcItemService.removeByIds(Func.toLongList(ids)));
    }


    /**
     * 学校分类-列表
     */
    @GetMapping("/type/school/list")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "学校分类-列表", notes = "学校分类-列表")
    public R<List<GcItemVO>> schoolTypeList() {
        String code = "XXFL";
        List<GcItemVO> list = gcItemService.findByCode(code);
        return R.data(list);
    }

    /**
     * 学校分类-新增或修改
     */
    @PostMapping("/type/school/submit")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "学校分类-新增或修改", notes = "必填项：value，name。选填项：id。id有修改，无则新增")
    public R schoolTypeSubmit(
            @ApiParam(value = "代码项名", required = true) @RequestParam String name,
            @ApiParam(value = "代码项值", required = true) @RequestParam String value,
            @ApiParam(value = "id") @RequestParam(required = false) Integer id
    ){
        GcItem gcItem = new GcItem();
        gcItem.setId(id);
        gcItem.setCode("XXFL");
        gcItem.setName(name);
        gcItem.setValue(value);
        int i = gcItemService.saveOrUpdateItem(gcItem);
        if (i == 0) {
            return R.status(true);
        } else if (i == 2) {
            return R.fail("代码项值value已存在，不能重复");
        } else {
            return R.status(false);
        }
    }


    /**
     * 班级分类-列表
     */
    @ApiIgnore
    @GetMapping("/type/team/list")
    @ApiOperationSupport(order = 16)
    @ApiOperation(value = "班级分类-列表", notes = "班级分类-列表")
    public R<List<GcItemVO>> typeTeamList(@ApiParam(value = "学校类型：1=学校 2=机构") @RequestParam(required = false) String type) {
        String code = "BJFL";
        if (StringUtils.isNotBlank(type)) {
            type += "%";
        }
        List<GcItemVO> list = gcItemService.findByRegexValue(code, type);
        return R.data(list);
    }

    /**
     * 班级分类-新增或修改
     */
    @ApiIgnore
    @PostMapping("/type/team/submit")
    @ApiOperationSupport(order = 17)
    @ApiOperation(value = "班级分类-新增或修改", notes = "id有修改，无则新增")
    public R typeTeamSubmit(
            @ApiParam(value = "学校类型：1=学校 2=机构", required = true) @RequestParam String type,
            @ApiParam(value = "名称", required = true) @RequestParam String name,
            @ApiParam(value = "序号", required = true) @RequestParam Integer sortOrder,
            @ApiParam(value = "id") @RequestParam(required = false) Integer id
    ) {
        String code = "BJFL";
        String value = "";
        if (id != null) {
            GcItem gcItem = gcItemService.getById(id);
            if (gcItem != null) {
                value = gcItem.getValue();
            }
        } else {
            value = gcItemService.getNextValue(code, type + "%");
        }
        GcItem gcItem = new GcItem();
        gcItem.setId(id);
        gcItem.setCode(code);
        gcItem.setName(name);
        gcItem.setValue(value);
        gcItem.setSortOrder(sortOrder);
        int i = gcItemService.saveOrUpdateItem(gcItem);
        if (i == 0) {
            return R.status(true);
        } else if (i == 2) {
            return R.fail("代码项值value已存在，不能重复");
        } else {
            return R.status(false);
        }
    }


    /**
     * 班级分类-列表
     */
    @ApiIgnore
    @GetMapping("/team/type/list")
    @ApiOperationSupport(order = 21)
    @ApiOperation(value = "班级分类-列表", notes = "班级分类-列表")
    public R<List<TeamType>> teamTypeList(@ApiParam(value = "学校类型：1=学校 2=机构") @RequestParam(required = false) Integer type) {
        List<TeamType> list = teamTypeService.findByType(type);
        return R.data(list);
    }

    /**
     * 班级分类-新增或修改
     */
    @ApiIgnore
    @PostMapping("/team/type/submit")
    @ApiOperationSupport(order = 22)
    @ApiOperation(value = "班级分类-新增或修改", notes = "id有修改，无则新增")
    public R teamTypeSubmit(
            @ApiParam(value = "学校类型：1=学校 2=机构", required = true) @RequestParam Integer type,
            @ApiParam(value = "名称", required = true) @RequestParam String name,
            @ApiParam(value = "序号", required = true) @RequestParam Integer listOrder,
            @ApiParam(value = "id") @RequestParam(required = false) Integer id
    ) {
        boolean status = teamTypeService.saveOrUpdateTeamType(type, name, listOrder, id);
        return R.status(status);
    }

    /**
     * 班级分类-删除
     */
    @PostMapping("/team/type/remove")
    @ApiOperationSupport(order = 23)
    @ApiOperation(value = "班级分类-删除", notes = "传入ids")
    public R teamTypeRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(teamTypeService.removeByIds(Func.toLongList(ids)));
    }



    /**
     * 学校-列表
     */
    @ApiIgnore
    @GetMapping("/school/list")
    @ApiOperationSupport(order = 26)
    @ApiOperation(value = "学校-列表", notes = "学校-列表")
    public R<List<School>> schoolList(@ApiParam(value = "学校类型：1=学校 2=机构") @RequestParam(required = false) Integer type) {
        List<School> list = schoolService.findByType(type);
        return R.data(list);
    }

    /**
     * 学校-新增或修改
     */
    @ApiIgnore
    @PostMapping("/school/submit")
    @ApiOperationSupport(order = 27)
    @ApiOperation(value = "学校-新增或修改", notes = "id有修改，无则新增")
    public R schoolSubmit(@Valid @RequestBody School school) {
        boolean status = schoolService.saveOrUpdateSchool(school);
        return R.status(status);
    }

    /**
     * 学校-删除
     */
    @PostMapping("/school/remove")
    @ApiOperationSupport(order = 28)
    @ApiOperation(value = "学校-删除", notes = "传入ids")
    public R schoolRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(schoolService.removeByIds(Func.toLongList(ids)));
    }


    /**
     * 班级-列表
     */
    @ApiIgnore
    @GetMapping("/team/list")
    @ApiOperationSupport(order = 31)
    @ApiOperation(value = "班级-列表", notes = "班级-列表")
    public R<List<TeamVO>> teamList(
            @ApiParam(value = "学校类型：1=学校 2=机构") @RequestParam(required = false) Integer type,
            @ApiParam(value = "班级分类") @RequestParam(required = false) Integer teamType,
            @ApiParam(value = "学校id") @RequestParam(required = false) Integer schoolId
    ) {
        List<TeamVO> list = teamService.findTeamVOWithTextbook(type, schoolId, teamType);
        return R.data(list);
    }

    /**
     * 班级-新增或修改
     */
    @ApiIgnore
    @PostMapping("/team/submit")
    @ApiOperationSupport(order = 32)
    @ApiOperation(value = "班级-新增或修改", notes = "id有修改，无则新增")
    public R teamSubmit(@Valid @RequestBody Team team) {
        boolean status = teamService.saveOrUpdateTeam(team);
        return R.status(status);
    }

    /**
     * 班级-删除
     */
    @PostMapping("/team/remove")
    @ApiOperationSupport(order = 33)
    @ApiOperation(value = "班级-删除", notes = "传入ids")
    public R teamRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(teamService.removeByIds(Func.toLongList(ids)));
    }



    /**
     * 老师-列表
     */
    @ApiIgnore
    @GetMapping("/teacher/list")
    @ApiOperationSupport(order = 41)
    @ApiOperation(value = "老师-列表", notes = "老师-列表")
    public R<List<TeacherVO>> teacherList(
            @ApiParam(value = "学校id") @RequestParam(required = false) Integer schoolId
    ) {
        List<TeacherVO> list = teacherService.findBySchoolId(schoolId);
        return R.data(list);
    }

    /**
     * 老师-新增或修改
     */
    @ApiIgnore
    @PostMapping("/teacher/submit")
    @ApiOperationSupport(order = 42)
    @ApiOperation(value = "老师-新增或修改", notes = "id有修改，无则新增")
    public R teacherSubmit(@Valid @RequestBody Teacher teacher) {
        boolean status = teacherService.saveOrUpdateTeacher(teacher);
        return R.status(status);
    }

    /**
     * 老师-删除
     */
    @PostMapping("/teacher/remove")
    @ApiOperationSupport(order = 43)
    @ApiOperation(value = "老师-删除", notes = "传入ids")
    public R teacherRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(teacherService.removeByIds(Func.toLongList(ids)));
    }

}
