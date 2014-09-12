package org.baeldung.gson.deserialization;

public class GenericFoo<Integer> {

    public Integer intField;

    public GenericFoo(Integer i) {
        intField = i;
    }

    @Override
    public String toString() {
        return "GenericFoo{" + "intField=" + intField + '}';
    }
}
