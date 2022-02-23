package org.xl.dubbo.spi.impl;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.threadpool.ThreadPool;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author xulei
 */
public class CustomProtocol implements Protocol {

    private ThreadPool threadPool;

    public ThreadPool getThreadPool() {
        return threadPool;
    }

    public CustomProtocol setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
        return this;
    }

    @Override
    public int getDefaultPort() {
        return 0;
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    @Override
    public void destroy() {

    }
}
