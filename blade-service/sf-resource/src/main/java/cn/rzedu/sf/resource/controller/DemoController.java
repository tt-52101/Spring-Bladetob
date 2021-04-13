package cn.rzedu.sf.resource.controller;//package cn.rzedu.sf.resource.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springblade.common.tool.WeChatUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/demo")
public class DemoController {

    /**
     * 批量修改上传文件
     *
     * @return ObjectStat
     */
    @SneakyThrows
    @GetMapping("/test")
//	@ApiOperation(value = "上传文件", notes = "oss上传文件接口" , position = 1)
    public Object putFile() {

		String xcxAccessToken = WeChatUtil.getXCXAccessToken();
		System.out.println(xcxAccessToken);

        return xcxAccessToken;
    }

}
