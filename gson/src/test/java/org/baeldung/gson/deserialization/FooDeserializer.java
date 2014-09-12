package org.baeldung.gson.deserialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class FooDeserializer implements JsonDeserializer<Foo[]> {

    @Override
    public Foo[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jArray = json.getAsJsonArray();
        Foo[] scArray = new Foo[jArray.size()];
        int index = 0;
        for (JsonElement jElement : jArray) {
            int i = jElement.getAsJsonObject().get("intValue").getAsInt();
            String s = jElement.getAsJsonObject().get("stringValue").getAsString();
            scArray[index++] = new Foo(i, s);
        }
        return scArray;
    }
}