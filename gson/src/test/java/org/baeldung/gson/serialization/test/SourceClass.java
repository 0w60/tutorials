package org.baeldung.gson.serialization.test;

public class SourceClass {
    private int intValue;
    private String stringValue;

    public SourceClass(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    @Override
    public String toString() {
        return "SourceClass{" +
                "intValue=" + intValue +
                ", stringValue='" + stringValue + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SourceClass)) return false;

        SourceClass that = (SourceClass) o;

        if (intValue != that.intValue) return false;
        if (!stringValue.equals(that.stringValue)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = intValue;
        result = 31 * result + stringValue.hashCode();
        return result;
    }
}
