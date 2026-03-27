package example.LeetCode.HistoricalCode;

/**
 * 功能：盛水最多的容器
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 *
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 返回容器可以储存的最大水量。
 * 作者：yml
 * 日期：2025/3/2414:41
 */
class container_Water {
    public int maxArea(int[] height) {
        int i = 0;
        int j = height.length-1;
        int max = 0;
        while (i < j){
            int cur = Math.min(height[i],height[j])*(j-i);
            if(cur  > max){
                max = cur;
            }
            if(height[i] < height[j]){
                i++;
            }else if (height[i] > height[j]){
                j--;;
            }else {
                i++;
                j--;
            }
        }
        return max;
    }
}
