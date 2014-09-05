package org.baeldung.gson.deserialization.test;


class TargetClass {
    int intValue;
    String stringValue;

    TargetClass(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "TargetClass{" + "intValue= " + intValue + ", stringValue= " + stringValue + '}';
    }
}
