package org.xl.springboot;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.xl.springboot.aop.AopTest;

import javax.annotation.Resource;

/**
 * @author xulei
 */
@SpringBootApplication(scanBasePackages={"org.xl.springboot"})
public class SpringBootMain implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
        AopTest aopTest = applicationContext.getBean(AopTest.class);
        aopTest.say();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBootMain.applicationContext = applicationContext;
    }
}
