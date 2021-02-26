package com.daimao.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    /**
     * 求list1 - list2的差集
     * @return
     */
    public static <E> List<E> differenceSet(List<E> list1,List<E> list2) {
        List<E> resultList = new ArrayList<>();
        for(E item : list1) {
            if(!list2.contains(item)) {
                resultList.add(item);
            }
        }
        return resultList;
    }
}
