package org.xl.utils.jackson.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xulei
 */
public class JsonSerializeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        user.setBirthday(new Date());
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Getter
    @Setter
    @JsonSerialize(using = CustomUserSerializer.class)
    public static class User {

        private String name;

//        @JsonSerialize(using = CustomDateSerializer.class)
        private Date birthday;
    }
}
