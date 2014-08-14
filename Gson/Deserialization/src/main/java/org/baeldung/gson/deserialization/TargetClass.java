package org.baeldung.gson.deserialization;

/**
 *
 * @author artem
 */
public class TargetClass {
    int intValue;
    String stringValue;

    public TargetClass(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "TargetClass{" + "intValue= " + intValue + ", stringValue= " + stringValue + '}';
    }
}
