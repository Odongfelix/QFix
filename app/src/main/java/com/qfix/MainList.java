package com.qfix;

import java.util.ArrayList;

public class MainList<T extends Job> extends ArrayList<T> {

    @Override
    public boolean add(T t) {
        int index = indexOf(t);
        if (index >= 0) {
            remove(index);
            super.add(index, t);
            return true;
        }
        return super.add(t);
    }
}
