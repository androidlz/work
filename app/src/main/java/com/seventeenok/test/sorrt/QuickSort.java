package com.seventeenok.test.sorrt;

import java.util.Arrays;

import static com.seventeenok.test.sorrt.Util.swap;


public class QuickSort implements study.sorrt.KySort {
    @Override
    public void kySort(int[] a, int size) {
        quickSortInternal(a, 0, size - 1);
    }


    private static void quickSortInternal(int[] array,int l,int r){
        if (l >= r){
            return;
        }
        //q就是当前的分区点pivot,partion()方法就是找到分区点并且返回这点的下标，下标为q
        int q = partition3(array,l,r);
        //下面进行递归分区，下标左右两边的数组继续寻找pivot分区点，继续划分；
        //递归就是继续调用本方法，传入新的数组以及分区；
        quickSortInternal(array,l,q-1);
        quickSortInternal(array,q+1,r);

    }


    /**
     *  分区点
     * @param array 传入的数组
     * @param l  左边界
//     * @param y  右边界
     */
    private static int partition3(int[] array,int l,int r){
        //三个指针，lt永远指向小于pivot区域的最后个元素
        //gt永远指向大于pivot区域的的第一个元素
        //i一直向后遍历元素；跟双路快排优点差别，双路快排是两个指针同时
        //分别从前向后、从后向前遍历；三路快排是三个指针，两个固定指向，一个从前向后遍历；
        int value = array[l];
        int lt = l;//刚开始lt指向小于v区域的前一个位置；即初始位置；
        int i = l+1;
        int gt = r+1;//同样gt后向前遍历，初始指向大于v区域的前一个位置；
        //下面开始遍历,从l+1处开始遍历
        while (i < gt){
            if (array[i] < value){
                swap(array,i,lt+1);
                lt++;
                i++;
            }else if(array[i] > value){
                swap(array,i,gt-1);
                gt--;
                //此处不能i++；因为当前i的元素是换回来的gt-1位置的元素
                //必须再次比较
                //前一个if入口的i++是因为当前i所指的元素就是换过来的小于
                //v的值，所以直接遍历下一个元素就行；
                // i++;
            }else{
                i++;
            }
        }
        //注意：这里的循环完成后，一定是gt指向小于v的最后一个区域；
        //所以这里可以直接交换l和gt的元素，然后返回gt 这个下标；
        swap(array,l,lt);
        return lt;
    }


}

