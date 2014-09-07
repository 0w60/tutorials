package org.baeldung.gson.serialization.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class SourceClassChangingFieldNamesSerializer implements JsonSerializer<SourceClass> {

    @Override
    public JsonElement serialize(SourceClass src, Type typeOfSrc, JsonSerializationContext context) {
        String otherIntValueName = "otherIntValue";
        String otherStringValueName = "otherStringValue";

        JsonObject jObject = new JsonObject();
        jObject.addProperty(otherIntValueName, src.getIntValue());
        jObject.addProperty(otherStringValueName, src.getStringValue());

        return jObject;
    }
}
