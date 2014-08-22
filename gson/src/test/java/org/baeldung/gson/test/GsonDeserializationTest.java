package org.baeldung.gson.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class GsonDeserializationTest {

    @Test
    public void givenJsonHasDissimilarFieldNamesButGsonMapsRight_whenUsingCustomDeserializer_thenCorrect() {
        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeAdapter(TargetClass.class, new TargetClassDeserializer());
        Gson gson = gsonBldr.create();
        TargetClass targetObject = gson.fromJson(jsonSourceObject, TargetClass.class);

        //test
        JsonElement jElement = new JsonParser().parse(jsonSourceObject);
        JsonObject jObject = jElement.getAsJsonObject();

        assertEquals(jObject.get("valueInt").getAsInt(), targetObject.intValue);
        assertEquals(jObject.get("valueString").getAsString(), targetObject.stringValue);
    }

    private class SourceClassDeserializer implements JsonDeserializer<SourceClass> {

        @Override
        public SourceClass deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Gson gson = new Gson();
            ArrayList<typeOfT> list = new ArrayList<>();
            JsonArray jArray = jElement.getAsJsonArray();
        for (JsonElement jsonElement : jArray) {
            list.add(gson.fromJson(jsonElement, SourceClass.class));
        }


            return null;
        }
    }

    @Test
    public void givenJasonWithArray_whenGsonDeserializes_thenMapsToArrayList() {
        //It is necessary to override the equals() method in SourceClass
        final SourceClass[] sourceArray = {new SourceClass(1, "one"), new SourceClass(2, "two")};
        final String jsonSourceObject =
                "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        ArrayList<SourceClass> targetList = new ArrayList<>();
        Gson gson = new Gson();
        /*
        Type sourceArrayType = new TypeToken<SourceClass[]>() {
        }.getType();
        String jsonSourceArray = gson.toJson(sourceArray, sourceArrayType);
        */
//        JsonElement jElement = new JsonParser().parse(jsonSourceObject);
        assertEquals(sourceArray[0], targetList.get(0));
    }

    @Test
    public void givenJasonHasDissimilarFieldNamesButGsonMapsRight_whenDeserializing_thenCorrect
            () {
        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        JsonParser jParser = new JsonParser();
        JsonElement jElement = jParser.parse(jsonSourceObject);
        JsonObject jObject = jElement.getAsJsonObject();
        int intValue = jObject.get("valueInt").getAsInt();
        String stringValue = jObject.get("valueString").getAsString();

        TargetClass targetObject = new TargetClass(intValue, stringValue);

        assertEquals(jObject.get("valueInt").getAsInt(), targetObject.intValue);
        assertEquals(jObject.get("valueString").getAsString(), targetObject.stringValue);
    }

    @Test
    public void givenJsonHasExtraValuesButGsonIsIgnoringExtras_whenDeserializing_thenCorrect() {
        final String serializedSourceObject = "{\"intValue\":1,\"stringValue\":\"one\",\"extraString\":\"two\",\"extraFloat\":2.2}";
        TargetClass targetObject = new Gson().fromJson(serializedSourceObject, TargetClass.class);

        assertEquals(targetObject.intValue, 1);
        assertEquals(targetObject.stringValue, "one");
    }

    @Test
    public void givenUsingGson_whenDeserializingGeneric_thenCorrect() {
        Type genericTargetClassType = new TypeToken<GenericTargetClass<Integer>>() {
        }.getType();
        final String serializedSourceObject = "{\"intField\":1}";

        GenericTargetClass<Integer> targetObject = new Gson().fromJson(
                serializedSourceObject, genericTargetClassType);

        assertEquals(targetObject.intField, 1);
    }

    @Test
    public void givenUsingGson_whenDeserializingCollection_thenCorrect() {
        final String serializedSourceCollection =
                "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        Type targetClassType = new TypeToken<ArrayList<TargetClass>>() {
        }.getType();

        Collection<TargetClass> targetCollection = new Gson().fromJson(
                serializedSourceCollection, targetClassType);
        assertThat(targetCollection, instanceOf(ArrayList.class));
    }
}