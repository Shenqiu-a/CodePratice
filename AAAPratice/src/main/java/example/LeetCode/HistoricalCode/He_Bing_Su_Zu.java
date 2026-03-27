package example.LeetCode.HistoricalCode;

/**
 */

class He_Bing_Su_Zu {

    public void merge(int [] nums1,int m,int [] nums2,int n){
        int p1 = 0, p2 = 0;
        int [] ans = new int[m+n+1];
        int cur;
        while(p1 < m || p2 < n){
            if(p1 == m){
                cur = nums2[p2++];
            }else if (p2 == n){
                cur = nums1[p1++];
            }else if(nums1[p1] < nums2[p2]){
                cur = nums1[p1++];
            }else {
                cur = nums2[p2++];
            }
            ans[p1+p2-1] = cur;
        }

    }

}
