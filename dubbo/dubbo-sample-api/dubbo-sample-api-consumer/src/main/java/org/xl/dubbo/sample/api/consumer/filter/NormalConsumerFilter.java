package org.xl.dubbo.sample.api.consumer.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author xulei
 */
@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER}, order = 9997)
public class NormalConsumerFilter implements Filter, Filter.Listener {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("Start to Invoke Dubbo Interface!");
        Result result = invoker.invoke(invocation);
        System.out.println("Finish to Invoke Dubbo Interface!");
        return result;
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        System.out.println("Filter get the value: " + appResponse.getValue());
    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
        System.out.println("Filter get error: " + t.getMessage());
    }
}
