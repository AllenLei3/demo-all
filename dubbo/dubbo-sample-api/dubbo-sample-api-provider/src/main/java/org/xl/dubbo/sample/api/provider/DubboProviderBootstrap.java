package org.xl.dubbo.sample.api.provider;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.ServiceBean;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.xl.dubbo.sample.service.DemoService;

import javax.annotation.Resource;

/**
 * @author xulei
 */
@EnableDubbo
@SpringBootApplication
public class DubboProviderBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderBootstrap.class, args);
    }

    @Bean
    public ServiceBean<DemoService> serviceBean(@Autowired DemoService demoService) {
        ServiceBean<DemoService> serviceBean = new ServiceBean<>();
        serviceBean.setApplication(new ApplicationConfig("dubbo-sample-api-provider"));
        serviceBean.setInterface(DemoService.class);
        serviceBean.setRef(demoService);
        serviceBean.setRegistry(new RegistryConfig("zookeeper://180.76.234.113:2181"));
        serviceBean.setProtocol(new ProtocolConfig("dubbo", -1));
        serviceBean.setVersion("1.0.0");
        return serviceBean;
    }

}
