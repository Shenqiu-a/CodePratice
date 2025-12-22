package example.LeetCode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/4/1611:45
 */

public class Top_K_Dui {

/*    public static void main(String[] args) {
        int nums1[] = new int[]{1,7,11};
        int nums2[] = new int[]{2,4,6};
        int k = 3;
        List<List<Integer>> lists = kSmallestPairs(nums1, nums2, k);
        System.out.println(lists);
    }*/
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(k,(o1, o2) -> {
            return nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]];
        });
        List<List<Integer>> ans = new ArrayList<>();

        int m = nums1.length, n = nums2.length;

        for(int i = 0; i < Math.min(m,k);++i){
            pq.offer(new int[]{i,0});
        }

        while (k-- > 0 && !pq.isEmpty()){
            int [] idxPair = pq.poll();
            List<Integer> ls = new ArrayList<>();
            ls.add(nums1[idxPair[0]]);
            ls.add(nums2[idxPair[1]]);
            ans.add(ls);
            if (idxPair[1] + 1 < n){
                pq.offer(new int[]{idxPair[0],idxPair[1]+1});
            }
        }
        return ans;
    }

}
