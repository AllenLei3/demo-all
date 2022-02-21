package org.xl.dubbo.sample.annotation.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.xl.dubbo.sample.service.DemoService;

/**
 * @author xulei
 */
@EnableDubbo
@SpringBootApplication
public class DubboConsumerBootstrap {

    @DubboReference(
            version = "1.0.0",
            timeout = 1000,
            methods = {
                    @Method(name = "sayHello", timeout = 3000)
            }
    )
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerBootstrap.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> System.out.println(demoService.sayHello("dubbo"));
    }
}
