package org.xl.dubbo.sample.api.provider;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.xl.dubbo.sample.service.DemoService;

import java.io.IOException;

/**
 * @author xulei
 */
public class DubboProviderBootstrap {

    public static void main(String[] args) throws IOException {

        ServiceConfig<DemoService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(new ApplicationConfig("dubbo-sample-api-provider"));
        serviceConfig.setInterface(DemoService.class);
        serviceConfig.setRef(new DefaultDemoService());
        serviceConfig.setRegistry(new RegistryConfig("zookeeper://180.76.234.113:2181"));
        serviceConfig.setProtocol(new ProtocolConfig("dubbo", -1));
        serviceConfig.setVersion("1.0.0");
        serviceConfig.export();

        System.in.read();
    }
}
