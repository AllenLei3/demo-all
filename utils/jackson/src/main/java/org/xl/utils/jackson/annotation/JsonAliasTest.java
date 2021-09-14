package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonAliasTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User u = MAPPER.readValue("{\"nm\":\"张三\"}", User.class);
        System.out.println(u.getName());
    }

    @Getter
    @Setter
    public static class User {

        @JsonAlias(value = "age")
        private String name;

        private Integer age;
    }

}
