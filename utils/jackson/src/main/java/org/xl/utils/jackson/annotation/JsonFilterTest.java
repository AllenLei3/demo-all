package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonFilterTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("cardCode", "address");
        FilterProvider provider = new SimpleFilterProvider().addFilter("customFilter", propertyFilter);

        User user = new User();
        user.setName("张三");
        user.setAge("25");
        user.setAddress("china");
        user.setCardCode("123456");
        System.out.println(MAPPER.writer(provider).writeValueAsString(user));
    }

    @Getter
    @Setter
    @JsonFilter("customFilter")
    public static class User {

        private String name;

        private String age;

        private String cardCode;

        private String address;
    }
}
