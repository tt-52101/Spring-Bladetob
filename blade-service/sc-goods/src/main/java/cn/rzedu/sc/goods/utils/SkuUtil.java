package cn.rzedu.sc.goods.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * 商品sku 工具类
 * @author
 */
public class SkuUtil {

    /**
     * 转换提取属性value
     * 如{"年级":"一年级", "册次":"上册"} --> 一年级 上册
     * @param name
     * @return
     */
    public static String extractSkuName(String name) {
        String shortName = "";
        if (StringUtils.isBlank(name)) {
            return shortName;
        }
        JSONObject jsonObject = JSON.parseObject(name);
        Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
        while (iterator.hasNext()) {
            shortName += iterator.next().getValue().toString();
        }
        return shortName;
    }
}
