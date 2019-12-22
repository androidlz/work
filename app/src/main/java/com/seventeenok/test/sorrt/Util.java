package com.seventeenok.test.sorrt;

import java.util.Arrays;

public class Util {
    public static void swap(int[] a, int i, int j) {
        System.out.println("即将交换" + a[i] + "和" + a[j]);
        int k = a[i];
        a[i] = a[j];
        a[j] = k;
        System.out.println(Arrays.toString(a));

    }
}
