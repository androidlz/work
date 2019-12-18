package study.sorrt;

import java.lang.reflect.Array;
import java.util.*;

public class TwoSumArray implements KySort {

    @Override
    public void kySort(int[] attr, int len) {
        twoSum(78, len, attr);
    }

    private void twoSum(int sum, int len, int[] attr) {
        Arrays.sort(attr);
        Map<Integer, Integer> result = new HashMap();
        int i = 0;
        int j = len - 1;
        while (i < j) {
            if (attr[i] + attr[j] > sum) {
                j--;
            } else if (attr[i] + attr[j] < sum) {
                i++;
            } else {
                result.put(attr[i], attr[j]);
                i++;
                j--;
            }
        }
        System.out.println(result);

    }
}
