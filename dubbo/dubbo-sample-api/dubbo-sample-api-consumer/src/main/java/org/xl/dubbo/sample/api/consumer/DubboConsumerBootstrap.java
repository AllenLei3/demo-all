package org.xl.dubbo.sample.api.consumer;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.xl.dubbo.sample.service.DemoService;

import java.io.IOException;

/**
 * @author xulei
 */
public class DubboConsumerBootstrap {

    public static void main(String[] args) throws IOException {
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("dubbo-sample-api-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://180.76.234.113:2181"));
        reference.setInterface(DemoService.class);
        reference.setVersion("1.0.0");
        DemoService demoService = reference.get();
        System.out.println(demoService.sayHello("dubbo"));

        System.in.read();
    }
}
