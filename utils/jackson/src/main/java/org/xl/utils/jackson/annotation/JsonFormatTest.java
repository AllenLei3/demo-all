package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xulei
 */
public class JsonFormatTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setBirthday(new Date());
        user.setSex(Sex.MAN);
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Setter
    @Getter
    public static class User {

        private String name;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date birthday;

        private Sex sex;
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Sex {
        MAN(1, "男生"),

        WOMAN(2, "女生");

        private final Integer code;
        private final String description;

        Sex(Integer code, String description) {
            this.code = code;
            this.description = description;
        }
    }
}
