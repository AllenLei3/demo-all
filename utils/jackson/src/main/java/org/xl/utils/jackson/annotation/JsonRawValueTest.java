package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonRawValueTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        user.setJson("{\"age\":\"25\"}");
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Getter
    @Setter
    public static class User {

        private String name;

        @JsonRawValue
        private String json;
    }
}
