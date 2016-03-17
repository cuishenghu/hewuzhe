package com.hewuzhe.ui.widget;

import com.hewuzhe.model.Friend;

import java.util.Comparator;


public class PinyinFriendComparator implements Comparator<Friend> {

    public int compare(Friend o1, Friend o2) {
        if (o1.topc.equals("@") || o2.topc.equals("#")) {
            return -1;
        } else if (o1.topc.equals("#") || o2.topc.equals("@")) {
            return 1;
        } else {
            return o1.topc.compareTo(o2.topc);
        }
    }

}