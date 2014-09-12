package org.baeldung.gson.deserialization.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.baeldung.gson.deserialization.Foo;
import org.baeldung.gson.deserialization.FooDeserializer;
import org.baeldung.gson.deserialization.FooDeserializerFromJsonWithDifferentFields;
import org.baeldung.gson.deserialization.GenericFoo;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class GsonDeserializationTest {

    @Test
    public void givenJsonHasDissimilarFieldNamesButGsonMapsRight_whenUsingCustomDeserializer_thenCorrect() {
        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeAdapter(Foo.class, new FooDeserializerFromJsonWithDifferentFields());
        Gson gson = gsonBldr.create();
        Foo targetObject = gson.fromJson(jsonSourceObject, Foo.class);

        assertEquals(targetObject.intValue, 7);
        assertEquals(targetObject.stringValue, "seven");
    }

    @Test
    public void givenJsonWithArray_whenUsingGsonCustomDeserializer_thenMapsToArrayList() {
        //It is necessary to override the equals() method in Foo Class
        final String jsonSourceObject =
                "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeHierarchyAdapter(Foo[].class, new FooDeserializer());
        Gson gson = gsonBldr.create();

        List<Foo> targetList = Arrays.asList(gson.fromJson(jsonSourceObject, Foo[].class));

        assertEquals(new Foo(1, "one"), targetList.get(0));
    }

    @Test
    public void givenJsonHasDissimilarFieldNamesButGsonMapsRight_whenDeserializingManualy_thenCorrect
            () {
        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        JsonParser jParser = new JsonParser();
        JsonElement jElement = jParser.parse(jsonSourceObject);
        JsonObject jObject = jElement.getAsJsonObject();
        int intValue = jObject.get("valueInt").getAsInt();
        String stringValue = jObject.get("valueString").getAsString();

        Foo targetObject = new Foo(intValue, stringValue);

        assertEquals(targetObject.intValue, 7);
        assertEquals(targetObject.stringValue, "seven");
    }

    @Test
    public void givenJsonHasExtraValuesButGsonIsIgnoringExtras_whenDeserializing_thenCorrect() {
        final String serializedSourceObject = "{\"intValue\":1,\"stringValue\":\"one\",\"extraString\":\"two\",\"extraFloat\":2.2}";
        Foo targetObject = new Gson().fromJson(serializedSourceObject, Foo.class);

        assertEquals(targetObject.intValue, 1);
        assertEquals(targetObject.stringValue, "one");
    }

    @Test
    public void givenUsingGson_whenDeserializingGeneric_thenCorrect() {
        Type genericTargetClassType = new TypeToken<GenericFoo<Integer>>() {
        }.getType();
        final String serializedSourceObject = "{\"intField\":1}";

        GenericFoo<Integer> targetObject = new Gson().fromJson(
                serializedSourceObject, genericTargetClassType);

        assertEquals(targetObject.intField, new Integer(1));
    }

    @Test
    public void givenUsingGson_whenDeserializingCollection_thenCorrect() {
        final String serializedSourceCollection =
                "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        Type targetClassType = new TypeToken<ArrayList<Foo>>() {
        }.getType();

        Collection<Foo> targetCollection = new Gson().fromJson(
                serializedSourceCollection, targetClassType);
        assertThat(targetCollection, instanceOf(ArrayList.class));
    }
}
