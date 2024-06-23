package com.qfix;

import java.util.ArrayList;

/**
 * List for holding a job
 * @param <T> the job object
 */
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
