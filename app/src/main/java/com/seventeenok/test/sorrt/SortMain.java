package com.seventeenok.test.sorrt;

import com.seventeenok.test.sorrt.TwoSumArray;

public class SortMain {
    private int[] a;
    private static study.sorrt.KySort kySort;

    public SortMain(int... values) {
        this.a = values;
    }

    private static void setSorter(study.sorrt.KySort sorter) {
        kySort = sorter;
    }

    public static void main(String[] args) {
        //冒泡排序
//        setSorter(new BubbleSort());
        //插入排序
//        setSorter(new InsertSort());
        //选择排序
//        setSorter(new SelectSort());
        //快速排序
//        setSorter(new QuickSort());
//        setSorter(new QuickSort3());
//        setSorter(new QuickSort2());
        //找出和为固定值的两个数
        setSorter(new TwoSumArray());
        SortMain sortMain;
        sortMain = new SortMain(72, 6, 57, 88, 60, 42, 83, 73, 48, 85, 6, 7, 8, 6, 9,18);
        sortMain.kySort();
        sortMain.display();


    }

    private void display() {
        for (int i : a) {   //遍历数组中每一个元素
            System.out.print(i + " ");   //展示
        }
        System.out.println();
    }


    private void kySort() {
        kySort.kySort(a, a.length);
    }
}
