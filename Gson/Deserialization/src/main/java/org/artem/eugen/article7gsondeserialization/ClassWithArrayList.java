package org.artem.eugen.article7gsondeserialization;

import java.util.ArrayList;


public class ClassWithArrayList {
    
    ArrayList<Integer> list;

    public ClassWithArrayList(ArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ClassWithArrayList{" + "list=" + list + '}';
    }
}
