package org.xl.utils.jackson.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author xulei
 */
public class CustomUserSerializer extends StdSerializer<JsonSerializeTest.User> {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CustomUserSerializer() {
        this(null);
    }

    protected CustomUserSerializer(Class<JsonSerializeTest.User> t) {
        super(t);
    }

    @Override
    public void serialize(JsonSerializeTest.User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", value.getName() + "^^");
        gen.writeStringField("birthday", format.format(value.getBirthday()) + "^^");
        gen.writeEndObject();
    }

}
