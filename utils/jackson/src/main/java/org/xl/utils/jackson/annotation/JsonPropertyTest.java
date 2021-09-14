package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonPropertyTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        JsonIncludePropertiesTest.User user = new JsonIncludePropertiesTest.User();
        user.setName("张三");
        user.setAge("25");
        user.setSex("男");
        System.out.println(MAPPER.writeValueAsString(user));

        User userD = MAPPER.readValue("{\"name\":\"张三\",\"age\":25}", User.class);
        System.out.println(userD.getName());
    }

    @Getter
    @Setter
    public static class User {

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String name;

        @JsonPropertyOrder({"age", "name"})
        private String age;
    }
}
