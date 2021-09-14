package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JacksonInjectTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        InjectableValues.Std injectableValues = new InjectableValues.Std()
                // 名称和注解中声明的相同才行
                .addValue("dynamic", "some value");
        MAPPER.setInjectableValues(injectableValues);

        User u = MAPPER.readValue("{\"age\":25}", User.class);
        System.out.println(u.getName());
        System.out.println(u.getAge());
    }

    @Getter
    @Setter
    public static class User {

        @JacksonInject(value = "dynamic")
        private String name;

        private Integer age;
    }
}
