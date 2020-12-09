package cn.rzedu.sf.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置mybatis包名、properties
 *
 * @author
 */
@Configuration
@MapperScan({"cn.rzedu.**.mapper.**"})
public class SFUserConfiguration {

}
