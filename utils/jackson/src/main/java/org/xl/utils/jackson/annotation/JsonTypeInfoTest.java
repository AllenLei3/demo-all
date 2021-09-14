package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonTypeInfoTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        Hobby hobby = new Hobby();
        hobby.setType("basketball");
        user.setHobby(hobby);
        Sex sex = new Man();
        sex.setSex("男");
        user.setSex(sex);
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Getter
    @Setter
    private static class User {

        private String name;

        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT)
        private Sex sex;

        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "flag")
        private Object hobby;
    }

    @Getter
    @Setter
    private static class Hobby {

        private String type;
    }

    private interface Sex {
        String getSex();
        void setSex(String sex);
    }

    @Getter
    @Setter
    private static class Man implements Sex {

        private String sex;
    }

    @Getter
    @Setter
    private static class Woman implements Sex {

        private String sex;
    }
}
