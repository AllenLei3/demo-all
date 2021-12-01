package org.xl.java.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.HashMap;
import java.util.Map;

/**
 * 避免动态脚本注入攻击
 *
 * @author xulei
 */
public class JsScriptEngine {

    public static void main(String[] args) throws ScriptException, InstantiationException {
        // 注入攻击
        String injectBody = "admin';java.lang.System.exit(0);'";
//        System.out.println(badInvokeScriptEngine(injectBody));

        // 正常调用
        System.out.println(bingingParam(injectBody));

        // 安全限制
        System.out.println(securityInvokeScript(injectBody));
    }

    /**
     * 没有对传入的动态脚本做限制,可能会产生注入攻击
     */
    private static Object badInvokeScriptEngine(String name) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine jsEngine = scriptEngineManager.getEngineByName("js");
        return jsEngine.eval(String.format("var name='%s'; name=='admin'?1:0;", name));
    }

    /**
     * 通过binging把外部数据只当做数据，而不当做代码的一部分
     */
    private static Object bingingParam(String name) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine jsEngine = scriptEngineManager.getEngineByName("js");
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return jsEngine.eval(String.format("var name='%s'; name=='admin'?1:0;", new SimpleBindings(params)));
    }

    /**
     * 使用SecurityManager配合AccessControlContext来构建一个脚本运行的沙箱环境。
     * 脚本能执行的所有操作权限是通过setPermissions方法精细化设置的
     */
    private static Object securityInvokeScript(String name) throws InstantiationException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine jsEngine = scriptEngineManager.getEngineByName("js");
        ScriptingSandbox scriptingSandbox = new ScriptingSandbox(jsEngine);
        return scriptingSandbox.eval(String.format("var name='%s'; name=='admin'?1:0;", name));
    }
}
