package study.sorrt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class SortTestHelper {
    //生成有n个元素的随机数数组，每个元素的随机范围为[randomL, randomR]
    public static Integer[] generateRandomArray(int n, int randomL, int randomR) {
        //assert randomL <= randomR;
        if (randomL > randomR) {
            throw new RuntimeException("Class->SortTestHelper, Method->genarateRandomArray's params must be [randomL <= randomR]");
        }

        Integer[] arr = new Integer[n];

        for (int i = 0; i < n; i++) {
            //方法一：
            //new Random().nextInt(10); //得到一个 [0, 10) 范围内的整数
            //方法二：
            //Math.random(); //得到一个 [0.0, 1.0) 范围内的double值，例如：0.2874378341644207

            //利用方法一：
            /*
             int random = new Random().nextInt(randomR-randomL+1); //得到一个 [0, randomR-randomL+1) 范围内的整数
             arr[i] = random + randomL; //得到一个 [randomL, randomR] 范围内的随机数
             */

            //利用方法二：
            double random = Math.random() * (randomR - randomL + 1); //得到一个 [0.0, randomR-randomL+1) 范围内的随机数
            arr[i] = (int) (random + randomL); //得到一个 [randomL, randomR] 范围内的随机数
        }

        return arr;
    }


    //交换数组中的两个数
    public static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    //打印数组
    public static void printArray(Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i]);
        }
        System.out.println();
    }


    //测试数组是否有序
    public static boolean isSorted(Comparable[] arr) {
        for (int i = 0; i < arr.length-1; i++) {
            if (arr[i].compareTo(arr[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }


    //测试算法的有效性和运算时间
    public static void testSort(String sortClassName, Comparable[] arr) {
        try {
            Class sortClass = Class.forName(sortClassName);
            Method sortMethod = sortClass.getMethod("sort", new Class[] { Comparable[].class });

            long startTimeMillis = System.currentTimeMillis();
            sortMethod.invoke( null , new Object[]{ arr } );
            long endTimeMillis = System.currentTimeMillis();

            //assert isSorted(arr);
            if(!isSorted(arr)){
                throw new RuntimeException(sortClassName + "is not valid !");
            }
            // 打印时间
            System.out.println(sortClassName + ": " + (endTimeMillis - startTimeMillis) + " ms");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    //生成一个接近有序的数组
    public static Integer[] generateNearlyOrderedArray(int n, int swapTimes){
        Integer[] arr = new Integer[n];
        for(int i=0; i<n; i++){
            arr[i] = i;
        }

        for(int i=0; i<swapTimes; i++){
            int a = new Random().nextInt(n);
            int b = new Random().nextInt(n);
            swap(arr, a, b);
        }

        return arr;
    }
}
