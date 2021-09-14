package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

/**
 * @author xulei
 */
public class JsonCreatorTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = MAPPER.readValue("{\"name\":\"张三\",\"age\":\"25\"}", User.class);
        System.out.println(user.getName());
    }

    @Getter
    public static class User {

        private final String name;

        private final String age;

        @JsonCreator
        public User(@JsonProperty("name") String name, @JsonProperty("age") String age) {
            this.name = name;
            this.age = age;
        }
    }
}
