package cn.rzedu.sf.activity;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springblade.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@EnableFeignClients({AppConstant.BASE_PACKAGES, CommonConstant.BASE_PACKAGES})
@ComponentScan({AppConstant.BASE_PACKAGES, CommonConstant.BASE_PACKAGES})
public class SFActivityApplication {

    public static void main(String[] args) {
        BladeApplication.run(CommonConstant.APPLICATION_SF_ACTIVITY_NAME, SFActivityApplication.class, args);
    }
}
