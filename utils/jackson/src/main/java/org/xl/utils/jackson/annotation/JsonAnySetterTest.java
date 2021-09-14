package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author xulei
 */
public class JsonAnySetterTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = MAPPER.readValue("{\"name\":\"张三\",\"info\":{\"address\":\"china\",\"age\":\"25\"}}", User.class);
        System.out.println(user.getAddress());
    }

    @Getter
    @Setter
    public static class User {

        private String name;

        private String address;

        private String age;

        @JsonAnySetter
        public void setInfo(Map<String, String> info) {
            this.address = info.get("address");
            this.age = info.get("age");
        }
    }

}
