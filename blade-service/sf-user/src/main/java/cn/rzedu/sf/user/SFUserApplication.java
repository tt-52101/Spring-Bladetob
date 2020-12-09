package cn.rzedu.sf.user;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients("cn.rzedu")
public class SFUserApplication {

    public static void main(String[] args) {
        BladeApplication.run(CommonConstant.APPLICATION_SF_USER_NAME, SFUserApplication.class, args);
    }
}
