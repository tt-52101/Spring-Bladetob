package cn.rzedu.sf.user;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springblade.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@EnableFeignClients(basePackages = {AppConstant.BASE_PACKAGES, CommonConstant.BASE_PACKAGES})
@ComponentScan({"org.springblade", "cn.rzedu.sf.user"})
public class SFUserApplication {

    public static void main(String[] args) {
        BladeApplication.run(CommonConstant.APPLICATION_SF_USER_NAME, SFUserApplication.class, args);
    }
}
