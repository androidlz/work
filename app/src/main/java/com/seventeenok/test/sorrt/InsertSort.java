package com.seventeenok.test.sorrt;

import com.seventeenok.test.sorrt.KySort;

import java.util.Arrays;

public class InsertSort implements KySort {
    @Override
    public void kySort(int[] attr, int len) {
        for (int i = 1; i < len; i++) {
            int temp = attr[i];
            int j = i;
            while (j > 0 && attr[j - 1] > temp) {
                attr[j] = attr[j - 1];
                j--;
            }
            //如果存在比其小的数  则插入
            if (j != i) {
                attr[j] = temp;
            }
            System.out.println(Arrays.toString(attr));
        }
    }
}
