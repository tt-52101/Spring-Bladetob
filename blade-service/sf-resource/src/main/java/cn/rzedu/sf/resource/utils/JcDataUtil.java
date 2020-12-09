package cn.rzedu.sf.resource.utils;

/**
 * 基础数据转换工具类
 * @author
 */
public class JcDataUtil {

    public static String getGradeName(String gradeCode) {
        String name = "";
        switch (gradeCode) {
            case "21" : name = "一年级"; break;
            case "22" : name = "二年级"; break;
            case "23" : name = "三年级"; break;
            case "24" : name = "四年级"; break;
            case "25" : name = "五年级"; break;
            case "26" : name = "六年级"; break;
            case "31" : name = "初一"; break;
            case "32" : name = "初二"; break;
            case "33" : name = "初三"; break;
            case "41" : name = "高一"; break;
            case "42" : name = "高二"; break;
            case "43" : name = "高三"; break;
        }
        return name;
    }

    public static String getVolumeName(String volume) {
        String name = "";
        switch (volume) {
            case "1" : name = "上册"; break;
            case "2" : name = "下册"; break;
        }
        return name;
    }
}
