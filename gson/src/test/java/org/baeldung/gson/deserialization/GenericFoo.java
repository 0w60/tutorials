package org.baeldung.gson.deserialization;

public class GenericFoo<T> {

    public T theValue;

    public GenericFoo(final T value) {
        theValue = value;
    }

    @Override
    public final String toString() {
        return "GenericFoo{" + "theValue=" + theValue + '}';
    }
}
