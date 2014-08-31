package org.baeldung.gson.serialization.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.baeldung.gson.deserialization.test.SourceClass;

import java.lang.reflect.Type;

public class SourceClassChangingFieldNamesSerializer implements JsonSerializer<SourceClass> {

    @Override
    public JsonElement serialize(SourceClass src, Type typeOfSrc, JsonSerializationContext context) {
        String otherIntValueName = "otherIntValue";
        String otherStringValueName = "otherStringValue";

        JsonObject jObject = new JsonObject();
        jObject.addProperty(otherIntValueName, src.intValue);
        jObject.addProperty(otherStringValueName, src.stringValue);

        return jObject;
    }
}
