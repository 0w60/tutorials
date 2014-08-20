package org.baeldung.gson.test;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.junit.Test;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

/**
 * Created by artem on 8/20/14.
 */
public class TargetClassDeserializer implements JsonDeserializer<TargetClass> {

    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link com.google.gson.JsonDeserializationContext#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type)} method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the
     * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param jElement  The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws com.google.gson.JsonParseException if json is not in the expected format of {@code typeofT}
     */
    @Override
    public TargetClass deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = jElement.getAsJsonObject();
        int intValue = jObject.get("valueInt").getAsInt();
        String stringValue = jObject.get("valueString").getAsString();
        return new TargetClass(intValue, stringValue);
    }

//    @Test
//    public void givenJasonHasDissimilarFieldNamesButGsonMapsRight_whenDeserializing_thenCorrect() {
//        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
//        JsonParser jParser = new JsonParser();
//        JsonElement jElement = jParser.parse(jsonSourceObject);
//        JsonObject jObject = jElement.getAsJsonObject();
//        int intValue = jObject.get("valueInt").getAsInt();
//        String stringValue = jObject.get("valueString").getAsString();
//
//
//        TargetClass targetObject = new TargetClass(intValue, stringValue);
//
//        assertEquals(jObject.get("valueInt").getAsInt(), targetObject.intValue);
//        assertEquals(jObject.get("valueString").getAsString(), targetObject.stringValue);
//    }

}
