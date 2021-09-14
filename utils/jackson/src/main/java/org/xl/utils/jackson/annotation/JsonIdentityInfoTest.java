package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xulei
 */
public class JsonIdentityInfoTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User userA = new User();
        userA.setName("张三");
        User userB = new User();
        userB.setName("李四");
        // 循环引用
        userA.setFriend(userB);
        userB.setFriend(userA);
        System.out.println(MAPPER.writeValueAsString(userA));
    }

    @Getter
    @Setter
    public static class User {

        private String name;

        @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
        @JsonIdentityReference(alwaysAsId = true)
        private User friend;
    }
}
