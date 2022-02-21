package org.xl.dubbo.sample.api.consumer;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.ApplicationRunner;
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
public class DubboConsumerBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerBootstrap.class, args);
    }

    @Resource
    private DemoService demoService;

    @Bean
    public DemoService demoService() {
        ReferenceBean<DemoService> reference = new ReferenceBean<>();
        reference.setApplication(new ApplicationConfig("dubbo-sample-api-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://180.76.234.113:2181"));
        reference.setInterface(DemoService.class);
        reference.setVersion("1.0.0");
        return reference.get();
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> System.out.println(demoService.sayHello("dubbo"));
    }
}
