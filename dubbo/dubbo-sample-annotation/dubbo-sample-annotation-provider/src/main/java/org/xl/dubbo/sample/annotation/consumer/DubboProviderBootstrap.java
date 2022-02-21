package org.xl.dubbo.sample.annotation.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xulei
 */
@EnableDubbo
@SpringBootApplication
public class DubboProviderBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderBootstrap.class, args);
    }
}
