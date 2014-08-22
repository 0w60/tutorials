package org.baeldung.gson.test;

class GenericTargetClass<Integer> {

    protected int intField;

    GenericTargetClass(int i) {
        intField = i;
    }

    @Override
    public String toString() {
        return "GenericTargetClass{" + "intField=" + intField + '}';
    }
}
