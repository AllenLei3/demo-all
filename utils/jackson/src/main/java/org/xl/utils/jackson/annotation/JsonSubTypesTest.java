package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonSubTypesTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        Sex sex = new Man();
        sex.setSex("男");
        user.setSex(sex);
        System.out.println(MAPPER.writeValueAsString(user));

        User userD = MAPPER.readValue("{\"name\":\"张三\",\"sex\":{\"@type\":\"Man\",\"sex\":\"男\"}}", User.class);
        System.out.println(userD.getSex().getClass().getName());
    }

    @Getter
    @Setter
    private static class User {

        private String name;

        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
        private Sex sex;
    }

    @JsonSubTypes({
            @JsonSubTypes.Type(value = Man.class, name = "Man"),
            @JsonSubTypes.Type(value = Woman.class, name = "Woman")})
    private interface Sex {
        String getSex();
        void setSex(String sex);
    }

    @Getter
    @Setter
    @JsonTypeName("Man")
    private static class Man implements Sex {

        private String sex;
    }

    @Getter
    @Setter
    @JsonTypeName("Woman")
    private static class Woman implements Sex {

        private String sex;
    }
}
