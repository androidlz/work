package com.seventeenok.test.sorrt;

import com.seventeenok.test.sorrt.Util;

import java.util.Arrays;

public class SelectSort implements KySort {
    @Override
    public void kySort(int[] attr, int len) {
        for (int i = 0; i < len - 1; i++) {
            int min = i;
            for (int j = i + 1; j < len; j++) {
                if (attr[min] > attr[j]) {
                    min = j;
                }
            }
            if (min != i) {
                Util.swap(attr, i, min);
            }
            System.out.println(Arrays.toString(attr));

        }
    }
}
