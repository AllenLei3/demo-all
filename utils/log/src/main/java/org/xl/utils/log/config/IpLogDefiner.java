package org.xl.utils.log.config;

import ch.qos.logback.core.PropertyDefinerBase;

import java.net.InetAddress;

/**
 * @author xulei
 */
public class IpLogDefiner extends PropertyDefinerBase {

    @Override
    public String getPropertyValue() {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            ip = "0.0.0.0";
        }
        return ip;
    }
}
