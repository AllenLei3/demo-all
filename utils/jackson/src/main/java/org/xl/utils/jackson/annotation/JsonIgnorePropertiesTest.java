package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonIgnorePropertiesTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        user.setAge("25");
        user.setSex("男");
        System.out.println(MAPPER.writeValueAsString(user));

        User userD = MAPPER.readValue("{\"name\":\"张三\",\"age\":\"25\",\"sex\":\"男\"}", User.class);
        System.out.println(userD.getName());
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"name", "age"})
    public static class User {

        private String name;

        private String age;

        private String sex;
    }
}
