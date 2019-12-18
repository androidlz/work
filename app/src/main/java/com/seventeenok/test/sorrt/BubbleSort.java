package study.sorrt;

import static study.sorrt.Util.swap;

public class BubbleSort implements KySort {

    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5};
//
        int len = arr.length;
//
//        for (int i=0;i<len-1;i++) {
//            System.out.println("第"+(i+1)+"趟");
//            //增加判断位
//            boolean flag=true;
//            for (int j=0;j<len-1-i;j++) {
//                System.out.println("第"+(j+1)+"次");
//
//                if(arr[j]>arr[j+1]){
//                    int temp=arr[j];
//                    arr[j]=arr[j+1];
//                    arr[j+1]=temp;
//                    flag=false;
//                }
//                System.out.println(Arrays.toString(arr));
//            }
//            //如果上面没有执行直接退出
//            if(flag){
//                break;
//            }
//        }
//        System.out.println(Arrays.toString(arr));


    }


    @Override
    public void kySort(int[] attr, int len) {
        //更简洁的实现方式
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (attr[j] > attr[j + 1]) {
                    swap(attr, j, j + 1);
                }
            }
        }
    }
}
