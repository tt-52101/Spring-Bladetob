package cn.rzedu.sf.user.utils;

/**
 * 随机数
 * @author
 */
public class RandomNumberUtil {

    /**
     * 基本学习人数
     */
    private static final int BASE_NUMBER = 1200;

    /**
     * 基本购买人数
     */
    private static final int BASE_NUMBER_BUY = 1400;

    /**
     * 设置随机学习人数  1200+随机(200~400)+真实人数
     * @return
     */
    public static int getRandomCount() {
        return getRandomCount(0);
    }

    /**
     * 设置随机学习人数  1200+随机(200~400)+真实人数
     * @param count
     * @return
     */
    public static int getRandomCount(Integer count){
        if (count == null) {
            count = 0;
        }
        int random = (int) (Math.random() * 200 + 1) + 200;
        return BASE_NUMBER + random + count;
    }

    /**
     * 设置随机购买人数  1400+随机(200~400)+真实人数
     * @param count
     * @return
     */
    public static int getBuyRandomCount(Integer count){
        if (count == null) {
            count = 0;
        }
        int random = (int) (Math.random() * 200 + 1) + 200;
        return BASE_NUMBER_BUY + random + count;
    }
}
