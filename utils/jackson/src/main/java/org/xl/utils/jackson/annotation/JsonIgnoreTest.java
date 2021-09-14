package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonIgnoreTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        user.setAge("25");
        System.out.println(MAPPER.writeValueAsString(user));

        User userD = MAPPER.readValue("{\"name\":\"张三\",\"age\":\"25\"}", User.class);
        System.out.println(userD.getName());
    }

    @Getter
    @Setter
    public static class User {

        @JsonIgnore
        private String name;

        private String age;
    }
}
