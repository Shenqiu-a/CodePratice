package example.LeetCode;

/**
 * 功能：验证回文串
 * 作者：yml
 * 日期：2025/3/2414:16
 */
class Solution{
    public boolean isPalindrome(String s) {
        String str = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String reverseStr = new StringBuilder(str).reverse().toString();

        return str.matches(reverseStr);
    }
}

public class PalindromicString {
    Solution solution = new Solution();
    public void test(){
        String s = "A man, a plan, a canal: Panama";
        System.out.println(solution.isPalindrome(s));
    }
}
