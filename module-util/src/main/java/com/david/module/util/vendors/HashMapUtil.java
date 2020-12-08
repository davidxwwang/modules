package com.david.module.util.vendors;

import java.util.HashMap;

public class HashMapUtil {
    /**
     * merge 两个Hashmap
     *
     * @param oldMap
     * @param newMap
     * @return
     */
    static HashMap merge(HashMap oldMap, HashMap newMap) {

        if (oldMap == null || newMap == null) {
            return null;
        }

        newMap.forEach((key, value) -> {
            oldMap.merge(key, value, (oldValue, newValue) -> {
                return newValue;
            });
        });

        return (HashMap) oldMap.clone();
    }
}
