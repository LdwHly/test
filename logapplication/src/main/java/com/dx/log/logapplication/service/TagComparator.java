package com.dx.log.logapplication.service;

import java.util.Comparator;

public class TagComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        if (WindowUtils.ALL_TAG.equals(o1)) {
            return -1;
        } else if (o1.equals("Request")) {
            if (WindowUtils.ALL_TAG.equals(o2))
                return 1;
            return -1;
        }
        return 0;
    }
}
