package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonGetterTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.putName("张三");
        System.out.print(MAPPER.writeValueAsString(user));

        User userD = MAPPER.readValue("{\"name\":\"张三\"}", User.class);
        System.out.print(userD.returnName());
    }

    public static class User {

        private String name;

        @JsonGetter("name")
        public String returnName() {
            return name;
        }

        @JsonSetter("name")
        public void putName(String name) {
            this.name = name;
        }
    }


}
