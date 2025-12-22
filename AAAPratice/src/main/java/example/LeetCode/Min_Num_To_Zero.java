package example.LeetCode;

/**
 * 功能：
 * 在一步操作中，你需要从范围 [0, 60] 中选出一个整数 i ，并从 num1 减去 2i + num2 。
 *
 * 请你计算，要想使 num1 等于 0 需要执行的最少操作数，并以整数形式返回。
 *
 * 如果无法使 num1 等于 0 ，返回 -1 。
 * 作者：yml
 * 日期：2025/4/1615:24
 */

public class Min_Num_To_Zero {
    // n1 - k * n2 - k * 2'i  (0 <= i <= 60)
    public int makeTheIntegerZero(int num1, int num2) {
        boolean flag = true;
        int ans = 0;
        while (flag){
            // (n1 - n2) % 2  ->  i  -> 1/-1
            int cur = num1 - num2;

        }
        return ans;
    }
}
