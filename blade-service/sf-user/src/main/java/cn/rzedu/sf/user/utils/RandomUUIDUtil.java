package cn.rzedu.sf.user.utils;

import java.util.UUID;

/**
 * 随机生成用户uuid
 * @author
 */
public class RandomUUIDUtil {

    /**
     * 初始位数
     */
    private static final int INIT_DIGIT = 6;

    /**
     * 英文字母初始位数
     */
    private static final int INIT_DIGIT_ENG = 2;


    public static String getUUID() {
        return getUUID(INIT_DIGIT);
    }

    public static String getUUID(int number) {
        UUID uuid = UUID.randomUUID();
        String result = uuid.toString();
        result = result.toUpperCase().replaceAll("-", "").substring(0, number);
        return result;
    }



    public static String getUUIDString() {
        return getUUIDString(INIT_DIGIT);
    }

    /**
     * 自定义uuid  2位英文+4位数字（初始）
     * @param number
     * @return
     */
    public static String getUUIDString(int number) {
        //数字位数
        int digitNumber = number - INIT_DIGIT_ENG;
        StringBuffer sb = new StringBuffer();
        long result = 0;
        //英文位数
        for (int i = 0; i < INIT_DIGIT_ENG; i++) {
            //A-Z 的 ASCII 码值[65,90]
            result = Math.round(Math.random() * 25 + 65);
            sb.append(String.valueOf((char) result));
        }
        //数字位数，不够的往前面补0
        result = Math.round(Math.random() * Math.pow(10, digitNumber) + 1);
        sb.append(String.format("%0"+ digitNumber +"d", result));
        return sb.toString();
    }
}
