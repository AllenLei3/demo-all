package org.xl.dubbo.sample.api.provider;

import org.apache.dubbo.rpc.RpcContext;
import org.xl.dubbo.sample.service.DemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xulei
 */
public class DefaultDemoService implements DemoService {

    @Override
    public String sayHello(String name) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return String.format("[dubbo-sample-api-provider] : Hello, %s", name) + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
