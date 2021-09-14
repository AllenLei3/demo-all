package org.xl.utils.jackson.deserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xulei
 */
public class JsonDeserializeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User userD = MAPPER.readValue("{\"name\":\"张三\",\"birthday\":\"2021-09-10 11:13:33\"}", User.class);
        System.out.println(userD.getBirthday());
    }

    @Getter
    @Setter
    @JsonDeserialize(using = CustomUserDeserializer.class)
    public static class User {

        private String name;

        @JsonDeserialize(using = CustomDateDeserializer.class)
        private Date birthday;
    }
}
