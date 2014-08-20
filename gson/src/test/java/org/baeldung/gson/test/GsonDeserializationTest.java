package org.baeldung.gson.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author artem
 */
public class GsonDeserializationTest {

    @Test
    public void givenJasonWithArray_whenGsonDeserializes_thenMapsToArrayList() {
        final String jsonSourceObject = "{\"array\":[1,2,3]}";
        ArrayList<Integer> list = new ArrayList<>();
        JsonElement jElement = new JsonParser().parse(jsonSourceObject);
        JsonObject jObject = jElement.getAsJsonObject();
        JsonArray jArray = jObject.get("array").getAsJsonArray();
        for (JsonElement jsonElement : jArray) {
            int i = jsonElement.getAsInt();
            list.add(i);
        }
        ClassWithArrayList targetObject = new ClassWithArrayList(list);

        Object[] testArray = new Object[jArray.size()];
        for (int i = 0; i < jArray.size(); i++) {
            testArray[i] = jArray.get(i).getAsInt();
        }
        assertArrayEquals(testArray, targetObject.list.toArray());
    }

    @Test
    public void givenJasonHasDissimilarFieldNamesButGsonMapsRight_whenDeserializing_thenCorrect() {
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

    @Test(expected = AssertionError.class)
    public void givenJsonHasExtraValuesButGsonIsIgnoringExtras_whenDeserializing_thenCorrect() {
        final String serializedSourceObject = "{\"intValue\":1,\"stringValue\":\"one\",\"extraString\":\"two\",\"extraFloat\":2.2}";
        TargetClass targetObject = new Gson().fromJson(serializedSourceObject, TargetClass.class);
        Field[] targetObjectFields = targetObject.getClass().getDeclaredFields();
        try {
            for (Field f : targetObjectFields) {
                assertThat(f.toString(), Matchers.containsString("extraString"));
            }
        } catch (AssertionError e) {
            for (Field f : targetObjectFields) {
                assertThat(f.toString(), Matchers.containsString("extraFloat"));
            }
        }
    }

    //TODO
    @Test
    public void givenUsingGson_whenDeserializingGeneric_thenCorrect() {
        Type genericTargetClassType = new TypeToken<GenericTargetClass<Integer>>() {
        }.getType();
        final String serializedSourceObject = "{\"intField\":1}";

        GenericTargetClass<Integer> targetObject = new Gson().fromJson(
                serializedSourceObject, genericTargetClassType);

//        assertThat(targetObject, instanceOf(GenericTargetClass.class));
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
