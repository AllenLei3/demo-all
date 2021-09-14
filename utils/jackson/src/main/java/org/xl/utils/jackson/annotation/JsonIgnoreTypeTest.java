package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonIgnoreTypeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        Hobby hobby = new Hobby();
        hobby.setItem("basketball");
        user.setHobby(hobby);
        System.out.println(MAPPER.writeValueAsString(user));

        User userD = MAPPER.readValue("{\"name\":\"张三\",\"hobby\":{\"item\":\"basketball\"}}", User.class);
        System.out.println(userD.getHobby() == null);
    }

    @Getter
    @Setter
    public static class User {

        private String name;

        private Hobby hobby;
    }

    @Getter
    @Setter
    @JsonIgnoreType
    public static class Hobby {

        private String item;
    }
}
