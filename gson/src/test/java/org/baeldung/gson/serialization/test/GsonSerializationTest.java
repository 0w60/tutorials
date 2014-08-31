package org.baeldung.gson.serialization.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.baeldung.gson.deserialization.test.SourceClass;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GsonSerializationTest {

    @Test
    public void givenUsingGson_whenSerializingCollection_thenCorrect() {
        ArrayList<SourceClass> list = new ArrayList<>();
        Collection<SourceClass> sourceCollection = list;
        Collections.addAll(sourceCollection, new SourceClass(1, "one"), new SourceClass(2, "two"));
        Type sourceCollectionType = new TypeToken<Collection<SourceClass>>() {
        }.getType();
        String jsonCollection = new Gson().toJson(sourceCollection, sourceCollectionType);

        //test
        Collection<SourceClass> testCollection = new Gson().fromJson(jsonCollection, sourceCollectionType);
        assertEquals(sourceCollection, testCollection);
    }

    @Test
    public void givenUsingGson_whenSerializingArrayOfObjects_thenMapToJsonCollection() {
        SourceClass[] sourceArray = {new SourceClass(1, "one"), new SourceClass(2, "two")};
        ArrayList<SourceClass> list = new ArrayList<>(Arrays.asList(sourceArray));
        Collection<SourceClass> targetCollection;
        Type targetCollectionType = new TypeToken<Collection<SourceClass>>() {
        }.getType();
        String jsonCollection = new Gson().toJson(list, targetCollectionType);

        //test
        targetCollection = new Gson().fromJson(jsonCollection, targetCollectionType);
        assertTrue(targetCollection.containsAll(Arrays.asList(sourceArray)));
    }

    @Test
    public void givenUsingGsonCustomSerializer_whenSerializingObjectToJsonWithDissimilarFieldNames_thenCorrect() {
        GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new SourceClassChangingFieldNamesSerializer());
        Gson gson = gsonBuildr.create();
        SourceClass sourceObject = new SourceClass(7, "seven");
        String jsonString = gson.toJson(sourceObject);

        //test
        JsonElement jElement = new JsonParser().parse(jsonString);
        JsonObject jObject = jElement.getAsJsonObject();
        int otherIntValue = jObject.get("otherIntValue").getAsInt();
        String otherStringValue = jObject.get("otherStringValue").getAsString();

        assertEquals(sourceObject.intValue, otherIntValue);
        assertEquals(sourceObject.stringValue, otherStringValue);
    }


}
