package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonIncludeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        //user.setAge(null);
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Getter
    @Setter
    public static class User {

        private String name;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String age;
    }
}
