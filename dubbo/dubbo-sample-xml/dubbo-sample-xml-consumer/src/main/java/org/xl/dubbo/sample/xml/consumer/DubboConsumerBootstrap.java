package org.xl.dubbo.sample.xml.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.xl.dubbo.sample.service.DemoService;

import javax.annotation.Resource;

/**
 * @author xulei
 */
@EnableDubbo
@SpringBootApplication
@ImportResource("classpath:dubbo-sample-xml-consumer.xml")
public class DubboConsumerBootstrap {

    @Resource
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerBootstrap.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> System.out.println(demoService.sayHello("dubbo"));
    }
}
