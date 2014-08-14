package org.baeldung.gson.deserialization;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;

/**
 *
 * @author artem
 */
public class MainTest {

    public MainTest() {
    }

    @Test
    public void testGivenJasonWithArray_whenGsonDeserializes_thenMapsToArrayList() {
        String jsonSourceObject = "{\"array\":[1,2,3]}";
        ArrayList<Integer> list = new ArrayList<>();
        JsonElement jElement = new JsonParser().parse(jsonSourceObject);
        JsonObject jObject = jElement.getAsJsonObject();
        JsonArray jArray = jObject.get("array").getAsJsonArray();
        for (JsonElement jsonElement : jArray) {
            int i = jsonElement.getAsInt();
            list.add(i);
        }
        ClassWithArrayList targetObject = new ClassWithArrayList(list);

    }

    @Test
    public void testGivenJasonHasDissimilarFieldNamesButGsonMapsRight_whenDeserializing_thenCorrect() {
        String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        TargetClass targetObject;
        JsonParser jParser = new JsonParser();
        JsonElement jElement = jParser.parse(jsonSourceObject);
        JsonObject jObject = jElement.getAsJsonObject();
        int intValue = jObject.get("valueInt").getAsInt();
        String stringValue = jObject.get("valueString").getAsString();

        targetObject = new TargetClass(intValue, stringValue);
    }

    @Test
    public void testGivenJsonHasExtraValuesButGsonIsIgnoringExtras_whenDeserializing_thenCorrect() {
        String serializedSourceObject = "{\"intValue\":1,\"stringValue\":\"one\",\"extraString\":\"two\",\"extraFloat\":2.2}";
        TargetClass targetObject = new Gson().fromJson(serializedSourceObject, TargetClass.class);
    }

    @Test
    public void testGivenUsingGson_whenDeserializingGeneric_thenCorrect() {
        java.lang.reflect.Type genericTargetClassType = new TypeToken<GenericTargetClass<Integer>>() {
        }.getType();
        String serializedSourceObject = "{\"intField\":1}";

        GenericTargetClass<Integer> targetObject = new Gson().fromJson(serializedSourceObject, genericTargetClassType);
    }

    @Test
    public void testGivenUsingGson_whenDeserializingCollection_thenCorrect() {
        String serializedSourceCollection = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        java.lang.reflect.Type targetClassType = new TypeToken<ArrayList<TargetClass>>() {
        }.getType();

        Collection<TargetClass> targetCollection = new Gson().fromJson(serializedSourceCollection, targetClassType);
    }

}
