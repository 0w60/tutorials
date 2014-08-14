package org.artem.eugen.article7gsondeserialization;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author artem
 */
public class Main {

    public static void main(String[] args) {
        Main ob = new Main();
        ob.givenJasonWithArray_whenGsonDeserializes_thenMapsToArrayList();
        ob.givenJasonHasDissimilarFieldNamesButGsonMapsRight_whenDeserializing_thenCorrect();
        ob.givenJsonHasExtraValuesButGsonIsIgnoringExtras_whenDeserializing_thenCorrect();
        ob.givenUsingGson_whenDeserializingGeneric_thenCorrect();
        ob.givenUsingGson_whenDeserializingCollection_thenCorrect();

    }

    public void givenJasonWithArray_whenGsonDeserializes_thenMapsToArrayList() {
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
        
        //test
        System.out.println("targetObject.toString(): " + targetObject.toString());
    }

    public void givenJasonHasDissimilarFieldNamesButGsonMapsRight_whenDeserializing_thenCorrect() {
        String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        TargetClass targetObject;
        
        JsonParser jParser = new JsonParser();
        JsonElement jElement = jParser.parse(jsonSourceObject);
        JsonObject jObject = jElement.getAsJsonObject();

        int intValue = jObject.get("valueInt").getAsInt();
        String stringValue = jObject.get("valueString").getAsString();

        targetObject = new TargetClass(intValue, stringValue);

        //test
        System.out.println("targetObject.toString(): " + targetObject.toString());
    }

    public void givenJsonHasExtraValuesButGsonIsIgnoringExtras_whenDeserializing_thenCorrect() {
        String serializedSourceObject = "{\"intValue\":1,\"stringValue\":\"one\",\"extraString\":\"two\",\"extraFloat\":2.2}";
        TargetClass targetObject = new Gson().fromJson(serializedSourceObject, TargetClass.class);

        //test
        System.out.println("targetObject.toString(): " + targetObject.toString());

    }

    public void givenUsingGson_whenDeserializingGeneric_thenCorrect() {
        java.lang.reflect.Type genericTargetClassType = new TypeToken<GenericTargetClass<Integer>>() {
        }.getType();
        String serializedSourceObject = "{\"intField\":1}";
        GenericTargetClass<Integer> targetObject = new Gson().fromJson(serializedSourceObject, genericTargetClassType);

        //test
        System.out.println("targetObject.toString(): " + targetObject.toString());
    }

    public void givenUsingGson_whenDeserializingCollection_thenCorrect() {
        String serializedSourceCollection = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        
        java.lang.reflect.Type targetClassType = new TypeToken<ArrayList<TargetClass>>() {
        }.getType();

        Collection<TargetClass> targetCollection = new Gson().fromJson(serializedSourceCollection, targetClassType);

        //test
        System.out.println("targetCollection.toString(): " + targetCollection.toString());

    }
}
