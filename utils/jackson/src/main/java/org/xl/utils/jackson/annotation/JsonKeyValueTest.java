package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulei
 */
public class JsonKeyValueTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        Map<String, String> info = new HashMap<>();
        info.put("sex", "男");
        info.put("age", "25");
        user.setInfo(info);
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Getter
    @Setter
    private static class User {

        private String name;

        @JsonKey
        private Map<String, String> info;
    }
}
