package org.baeldung.gson.deserialization;

import java.util.ArrayList;


class ClassWithArrayList {

    ArrayList<Integer> list;

    ClassWithArrayList(ArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ClassWithArrayList{" + "list=" + list + '}';
    }
}
