package org.baeldung.gson.deserialization;


public class Foo {
    public int intValue;
    public String stringValue;

    public Foo(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Foo)) return false;

        Foo foo = (Foo) o;

        if (intValue != foo.intValue) return false;
        if (!stringValue.equals(foo.stringValue)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = intValue;
        result = 31 * result + stringValue.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Foo{" + "intValue= " + intValue + ", stringValue= " + stringValue + '}';
    }
}
