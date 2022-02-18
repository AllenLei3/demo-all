package org.xl.springboot.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xulei
 */
@Configuration
@ConditionalOnClass(name = {"org.xl.springboot.autoconfig.AAA"})
public class TestAutoConfiguration {

    @Bean
    public Test test() {
        System.out.println("Create Test Object");
        return new Test();
    }
}
