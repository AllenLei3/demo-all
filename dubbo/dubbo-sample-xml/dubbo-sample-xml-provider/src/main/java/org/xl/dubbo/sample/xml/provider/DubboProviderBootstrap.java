package org.xl.dubbo.sample.xml.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author xulei
 */
@EnableDubbo
@SpringBootApplication
@ImportResource("classpath:dubbo-sample-xml-provider.xml")
public class DubboProviderBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderBootstrap.class, args);
    }
}
