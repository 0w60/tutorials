package org.baeldung.gson.serialization.test;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class GsonSerializationTest {

    @Test
    public void givenCollection_whenSerializing_thenCorrect() {
        Collection<SourceClass> sourceCollection = Lists.newArrayList(
                new SourceClass(1, "one"), new SourceClass(2, "two"));
        Type sourceCollectionType = new TypeToken<Collection<SourceClass>>() {
        }.getType();
        String jsonCollection = new Gson().toJson(sourceCollection, sourceCollectionType);

        //test
        Collection<SourceClass> testCollection = new Gson().fromJson(jsonCollection, sourceCollectionType);
        assertEquals(sourceCollection, testCollection);
    }

    @Test
    public void givenArrayOfObjects_whenSerializing_thenMapToJsonCollection() {
        SourceClass[] sourceArray = {new SourceClass(1, "one"), new SourceClass(2, "two")};
        String jsonCollection = new Gson().toJson(sourceArray);

        //test
        String estimatedResult = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        assertEquals(estimatedResult, jsonCollection);
    }

    @Test
    public void givenUsingCustomSerializer_whenSerializingObjectToJsonWithDissimilarFieldNames_thenCorrect() {
        SourceClass sourceObject = new SourceClass(7, "seven");
        GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new SourceClassChangingFieldNamesSerializer());
        Gson gson = gsonBuildr.create();
        String jsonString = gson.toJson(sourceObject);

        //test
        String estimatedResult = "{\"otherIntValue\":7,\"otherStringValue\":\"seven\"}";
        assertEquals(estimatedResult, jsonString);
    }

    @Test
    public void givenUsingCustomSerializer_whenSerializingObject_thenFieldIgnored() {
        SourceClass sourceObject = new SourceClass(7, "seven");
        GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new SourceClassIgnoringExtraFieldsSerializer());
        Gson gson = gsonBuildr.create();
        String jsonString = gson.toJson(sourceObject);

        //test
        String estimatedResult = "{\"intValue\":7}";
        assertEquals(estimatedResult, jsonString);
    }
}