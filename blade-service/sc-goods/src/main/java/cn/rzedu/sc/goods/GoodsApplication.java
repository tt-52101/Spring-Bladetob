package cn.rzedu.sc.goods;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableFeignClients("cn.rzedu")
@EnableScheduling
public class GoodsApplication {

    public static void main(String[] args) {
        BladeApplication.run(CommonConstant.APPLICATION_SC_GOODS_NAME, GoodsApplication.class, args);
    }
}
