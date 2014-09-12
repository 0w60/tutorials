package org.baeldung.gson.serialization.test;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GsonSerializationTest {

    @Test
    public void givenCollection_whenSerializing_thenCorrect() {
        Collection<SourceClass> sourceCollection = Lists.newArrayList(
                new SourceClass(1, "one"), new SourceClass(2, "two"));
        Type sourceCollectionType = new TypeToken<Collection<SourceClass>>() {
        }.getType();
        String jsonCollection = new Gson().toJson(sourceCollection, sourceCollectionType);

        //test
        String expectedResult = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        assertEquals(expectedResult, jsonCollection);
    }

    @Test
    public void givenArrayOfObjects_whenSerializing_thenCorrect() {
        SourceClass[] sourceArray = {new SourceClass(1, "one"), new SourceClass(2, "two")};
        String jsonString = new Gson().toJson(sourceArray);

        //test
        String expectedResult = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenUsingCustomSerializer_whenSerializingObjectToJsonWithDissimilarFieldNames_thenCorrect() {
        SourceClass sourceObject = new SourceClass(7, "seven");
        GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new SourceClassChangingFieldNamesSerializer());
        Gson gson = gsonBuildr.create();
        String jsonString = gson.toJson(sourceObject);

        //test
        String expectedResult = "{\"otherIntValue\":7,\"otherStringValue\":\"seven\"}";
        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenUsingCustomSerializer_whenSerializingObject_thenFieldIgnored() {
        SourceClass sourceObject = new SourceClass(7, "seven");
        GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new SourceClassIgnoringExtraFieldsSerializer());
        Gson gson = gsonBuildr.create();
        String jsonString = gson.toJson(sourceObject);

        //test
        String expectedResult = "{\"intValue\":7}";
        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenDate_whenSerializing_thenCorrect() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
        Date sourceDate = calendar.getTime();
        Gson gson = new Gson();
        Type sourceDateType = new TypeToken<Date>() {
        }.getType();
        String jsonDate = gson.toJson(sourceDate, sourceDateType);

        //test
        System.out.println("jsonDate:\n" + jsonDate);
        String expectedResult = "\"Jan 1, 2000 12:00:00 AM\"";
        assertEquals(expectedResult, jsonDate);
    }

    @Test
    public void givenUsingCustomDeserializer_whenFieldNotMatchesCriteria_thenIgnoringIt() {
        SourceClass sourceObject = new SourceClass(-1, "minus 1");
        GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new IgnoringFieldsNotMatchingCriteriaSerializer());
        Gson gson = gsonBuildr.create();
        Type sourceObjectType = new TypeToken<SourceClass>() {
        }.getType();
        String jsonString = gson.toJson(sourceObject, sourceObjectType);

        //test
        String expectedResult = "{\"stringValue\":\"minus 1\"}";
        assertEquals(expectedResult, jsonString);
    }
}