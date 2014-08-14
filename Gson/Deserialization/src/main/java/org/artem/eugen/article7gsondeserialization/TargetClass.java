package org.artem.eugen.article7gsondeserialization;

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
