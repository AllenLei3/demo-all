package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonUnwrappedTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        Identity identity = new Identity();
        identity.setAge("25");
        identity.setAddress("china");
        user.setIdentity(identity);
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Getter
    @Setter
    public static class User {

        private String name;

        @JsonUnwrapped
        private Identity identity;
    }

    @Getter
    @Setter
    public static class Identity {

        private String age;

        private String address;
    }

}
