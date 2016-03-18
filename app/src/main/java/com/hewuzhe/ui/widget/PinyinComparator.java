package com.hewuzhe.ui.widget;

import com.hewuzhe.model.Address;

import java.util.Comparator;


public class PinyinComparator implements Comparator<Address> {

    public int compare(Address o1, Address o2) {
        if (o1.topc.equals("@") || o2.topc.equals("#")) {
            return -1;
        } else if (o1.topc.equals("#") || o2.topc.equals("@")) {
            return 1;
        } else {
            return o1.topc.compareTo(o2.topc);
        }
    }

}