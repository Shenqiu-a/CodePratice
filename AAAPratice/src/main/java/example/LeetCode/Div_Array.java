package example.LeetCode;

/**
 * 功能： 找出字符串的可整除数组
 * 作者：yml
 * 日期：2025/4/1615:01
 */

public class Div_Array {
    public static void main(String[] args) {
        String word = "998244353";
        int m = 3;
        int[] array = divisibilityArray(word, m);
        System.out.println(array);
    }
    // 3 6 9 12 15 18 21 24 27 30
    public static int[] divisibilityArray(String word, int m) {
        int []ans = new int[word.length()];
        //余数 一位一位的*10
        long cur = 0;
        for(int i = 0; i < word.length();i++){
            char c = word.charAt(i);
            cur = (cur * 10 + (c - '0')) % m;
            ans[i] = (cur == 0) ? 1 : 0;
        }
        return ans;
    }
}
