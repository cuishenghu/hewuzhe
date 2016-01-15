package com.hewuzhe.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class Alist<E> {

    public ArrayList<E> list = new ArrayList<>();


    public Alist add(E obj) {
        list.add(obj);
        return this;
    }

    public Alist addAll(Collection<? extends E> objs) {
        list.addAll(objs);
        return this;
    }

    public Alist remove(int index) {
        list.remove(index);
        return this;
    }

    public ArrayList<E> ok() {
        return list;
    }


}
