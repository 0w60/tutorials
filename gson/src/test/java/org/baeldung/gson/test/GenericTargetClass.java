package org.baeldung.gson.test;

class GenericTargetClass<Integer> {

    protected Integer intField;

    GenericTargetClass(Integer i) {
        intField = i;
    }

    @Override
    public String toString() {
        return "GenericTargetClass{" + "intField=" + intField + '}';
    }
}
