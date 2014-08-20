package org.baeldung.gson.test;

/**
 * @author artem
 */
class GenericTargetClass<Integer> {

    private int intField;

    GenericTargetClass(int i) {
        intField = i;
    }

    @Override
    public String toString() {
        return "GenericTargetClass{" + "intField=" + intField + '}';
    }
}
