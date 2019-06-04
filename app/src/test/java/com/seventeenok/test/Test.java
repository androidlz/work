package com.seventeenok.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Test {
    public HashMap<Integer, Integer> time = new HashMap<>();

    @org.junit.Test
    public void myTest() {
        //注意处理视频时段的时候   记得判断开始时间是否存在
        time.put(0, 100);
        time.put(200, 250);
        time.put(240, 280);
        time.put(150, 300);
        time.put(50, 100);
        HashMap<Integer, Integer> sortMap = sortData(time, false);
        getUniqueList(sortMap);
    }


    private HashMap<Integer, Integer> sortData(HashMap<Integer, Integer> time, boolean isLoop) {
        Set<Integer> keySet = time.keySet();
        Set<Map.Entry<Integer, Integer>> entries = time.entrySet();
        Object[] keys = keySet.toArray();
        HashMap<Integer, Integer> tempMap = new HashMap<>();
        Arrays.sort(keys);
        for (Object key : keys) {
            int kk = (int) key;
            int value = time.get(kk);
            tempMap.put(kk, value);
            System.out.println("key---" + kk + "     value---" + value);
        }
        if (isLoop) {
            getUniqueList(tempMap);
        }
        return tempMap;
    }

    private HashMap<Integer, Integer> getUniqueList(HashMap<Integer, Integer> sortMap) {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        if (sortMap == null || sortMap.isEmpty()) {
            return sortMap;
        }
        if (sortMap.size() > 1) {
            getMap(sortMap);
        }
        Set<Map.Entry<Integer, Integer>> entries = sortMap.entrySet();
        Iterator<Map.Entry<Integer, Integer>> iterator = entries.iterator();
        System.out.println("-------------------");
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            System.out.println("key====" + next.getKey() + "    value===" + next.getValue());
        }
        return sortMap;
    }

    public HashMap<Integer, Integer> getMap(HashMap<Integer, Integer> sortMap) {
        Set<Integer> keySet = sortMap.keySet();
        Set<Integer> keySet_delelte = sortMap.keySet();
        Object[] keys = keySet.toArray();
        for (int i = 1; i < sortMap.size(); i++) {
            int key = (int) keys[i];
            int value = sortMap.get(key);
            int key_pre = (int) keys[i - 1];
            int value_pre = sortMap.get(key_pre);
            if (key > key_pre && key < value_pre && value <= value_pre) {
                //处理无效数据  直接移除
                sortMap.remove(key);
                getMap(sortMap);
                break;
            } else if (key > key_pre && key < value_pre && value > value_pre) {
                sortMap.remove(key);
                sortMap.remove(key_pre);
                sortMap.put(key_pre, value);
                break;
            }
        }
        return sortMap;
    }

}
