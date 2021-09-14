package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonRootNameTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        user.setAge("25");
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Getter
    @Setter
    @JsonRootName("user")
    public static class User {

        private String name;

        private String age;
    }
}
