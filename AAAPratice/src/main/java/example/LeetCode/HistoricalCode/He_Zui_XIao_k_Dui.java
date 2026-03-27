package example.LeetCode.HistoricalCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/4/1415:28
 */

class He_Zui_XIao_k_Dui {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();

        return ans;
    }


    public boolean canJump(int nums[]){
        int maxpoint = 0;
        int len = nums.length;
        for(int i = 0; i < len;i++){
            if(i <= maxpoint){
                maxpoint = Math.max(maxpoint,i+nums[i]);
                if(maxpoint >= len - 1)
                    return true;
            }
        }
        return  false;
    }

    // 从后，确定一个位置，这个位置是最优的位置，每次从前面找 到这个位置的另一个最优解
    public int jump(int nums[]){
        int position = nums.length -1;
        int time = 0;
        while (position > 0){
            for(int i = 0;i < position;i++){
                if(nums[i] + i >= position){
                    time++;
                    position = i;
                    break;
                }
            }
        }
        return time;
    }
}
