package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xulei
 */
public class JsonBackReferenceTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");

        Friend friend = new Friend();
        friend.setNumber("001");
        friend.setUser(user);
        Friend friend2 = new Friend();
        friend2.setNumber("002");
        friend2.setUser(user);

        List<Friend> friends = new ArrayList<>();
        friends.add(friend);
        friends.add(friend2);
        user.setFriends(friends);
        System.out.println(MAPPER.writeValueAsString(user));
    }

    @Getter
    @Setter
    public static class User {

        private String name;

        @JsonManagedReference
        private List<Friend> friends;
    }

    @Getter
    @Setter
    public static class Friend {

        private String number;

        @JsonBackReference
        private User user;
    }
}
