package com.qfix;

import java.util.ArrayList;

public class NonDuplicateList<T extends Job> extends ArrayList<T> {
    @Override
    public boolean add(T t) {
        if (contains(t)) return true;
        return super.add(t);
    }
}
