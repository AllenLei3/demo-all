package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonEnumDefaultValueTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        MAPPER.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        User user = MAPPER.readValue("{\"name\":\"张三\",\"sex\":\"NONE\"}", User.class);
        System.out.println(user.getSex());
    }

    @Setter
    @Getter
    public static class User {

        private String name;

        private Sex sex;
    }

    private enum Sex {

        MAN,

        WOMAN,

        @JsonEnumDefaultValue
        UNKNOWN
    }

}
