package org.baeldung.gson.deserialization;

/**
 *
 * @author artem
 */
public class GenericTargetClass<Integer> {

    int intField;

    public GenericTargetClass(int i) {
        intField = i;
    }

    @Override
    public String toString() {
        return "GenericTargetClass{" + "intField=" + intField + '}';
    }
}
