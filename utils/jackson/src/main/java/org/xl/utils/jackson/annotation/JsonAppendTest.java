package org.xl.utils.jackson.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Getter;
import lombok.Setter;
import org.xl.utils.jackson.deserialize.CustomUserDeserializer;
import org.xl.utils.jackson.deserialize.JsonDeserializeTest;

/**
 * @author xulei
 */
public class JsonAppendTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("张三");
        user.setAge("25");
        String str = MAPPER.writerFor(User.class).withAttribute("version", "1.0").writeValueAsString(user);
        System.out.println(str);

        CustomUserDeserializer userDeserializer = new CustomUserDeserializer();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(JsonDeserializeTest.User.class, userDeserializer);
        MAPPER.registerModule(module);
    }

    @Getter
    @Setter
    @JsonAppend(attrs = {
            @JsonAppend.Attr(value = "version")
    })
    public static class User {

        private String name;

        private String age;
    }
}
