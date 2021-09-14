package org.xl.utils.jackson.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xulei
 */
public class CustomUserDeserializer extends StdDeserializer<JsonDeserializeTest.User> {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CustomUserDeserializer() {
        this(null);
    }

    protected CustomUserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public JsonDeserializeTest.User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonDeserializeTest.User user = new JsonDeserializeTest.User();

        ObjectCodec codec = p.getCodec();
        JsonNode jsonNode = codec.readTree(p);
        try {
            String name = jsonNode.get("name").asText();
            user.setName(name);
            String birthday = jsonNode.get("birthday").asText();
            user.setBirthday(format.parse(birthday));
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
