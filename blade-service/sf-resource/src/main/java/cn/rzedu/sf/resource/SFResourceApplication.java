package cn.rzedu.sf.resource;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springblade.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@ComponentScan({"org.springblade.resource.hystrix","cn.rzedu.sf.resource"})
public class SFResourceApplication {

    public static void main(String[] args) {
        BladeApplication.run(CommonConstant.APPLICATION_SF_RESOURCE_NAME, SFResourceApplication.class, args);
    }
}
