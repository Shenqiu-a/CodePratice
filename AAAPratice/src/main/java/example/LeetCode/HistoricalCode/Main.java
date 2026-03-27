package example.LeetCode.HistoricalCode;

import java.io.BufferedReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //想法是不断比较,那么就可以写一个比较方法，来不断调用
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
        String []temp = reader.readLine().split(",");
        if(temp.length == 0){
            System.out.println("-");
            return;
        }

        String result = temp[0];
        for (int i = 1; i < temp.length; i++) {
            result = findResult(result,temp[i]);
            if(result.isEmpty()){
                System.out.println("-");
                return;
            }
        }
        System.out.println(result);
    }

    private static String findResult(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int [][] dp = new int[m+1][n+1];
        int maxLength = 0;
        int endIndes = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                    if(dp[i][j] > maxLength){
                        maxLength = dp[i][j];
                        endIndes = i;
                    }
                }
            }
        }
        if(maxLength == 0){
            return "";
        }
        return str1.substring(endIndes-maxLength,endIndes);
    }
}
