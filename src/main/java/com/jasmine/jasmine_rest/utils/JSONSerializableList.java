package com.jasmine.jasmine_rest.utils;

import java.util.ArrayList;
import java.util.Collection;

public class JSONSerializableList<T> extends ArrayList<T> implements JSONSerializable {

    public JSONSerializableList(int initialCapacity) {
        super(initialCapacity);
    }

    public JSONSerializableList() {
        super();
    }

    public JSONSerializableList(Collection<? extends T> c) {
        super(c);
    }
}
