package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.user.utils.AnalyzeWordUtil;
import cn.rzedu.sf.user.vo.AnalyzeWordHard;
import cn.rzedu.sf.user.vo.AnalyzeWordSoft;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * AI测评
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ai/evaluation")
@Api(value = "AI测评", tags = "AI测评")
public class AIEvaluationController {

    /**
     * 硬笔测字接口
     */
    @PostMapping("/hardPen")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "硬笔测字接口", notes = "硬笔测字接口")
    public R<AnalyzeWordHard> analyzeWordHard(@ApiParam(value = "图片文件", required = true) @RequestParam MultipartFile file) {
        try {
            AnalyzeWordHard word = AnalyzeWordUtil.writingAnalyzeHard(file);
            return R.data(word);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.data(null);
    }


    /**
     * 软笔测字接口
     */
    @PostMapping("/softPen")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "软笔测字接口", notes = "软笔测字接口")
    public R<AnalyzeWordSoft> analyzeWordSoft(@ApiParam(value = "图片文件", required = true) @RequestParam MultipartFile file) {
        try {
            AnalyzeWordSoft word = AnalyzeWordUtil.writingAnalyzeSoft(file);
            return R.data(word);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.data(null);
    }
}
