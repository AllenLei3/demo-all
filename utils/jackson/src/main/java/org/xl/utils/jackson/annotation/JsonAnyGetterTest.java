package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulei
 */
public class JsonAnyGetterTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        Map<String, String> info = new HashMap<>();
        info.put("age", "25");
        info.put("address", "china");
        User user = new User();
        user.setName("张三");
        user.setInfo(info);
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Getter
    @Setter
    public static class User {

        private String name;

        private Map<String, String> info;

        @JsonAnyGetter
        public Map<String, String> getInfo() {
            return info;
        }
    }

}
