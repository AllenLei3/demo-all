package org.xl.dubbo.sample.api.provider;

import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xl.dubbo.sample.service.DemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xulei
 */
@Component("demoService")
public class DefaultDemoService implements DemoService {

    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return String.format("[%s] : Hello, %s", serviceName, name) + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
