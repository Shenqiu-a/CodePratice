package example.LeetCode.HistoricalCode;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/4/1611:28
 */

public class Top_K {
    public int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        bulidMaxHeap(nums, heapSize);
        for(int i = nums.length - 1;i >= nums.length - k + 1; --i){
            swap(nums, 0 , i);
            --heapSize;
            maxHeap(nums,heapSize,0);
        }
        return nums[0];
    }

    public void bulidMaxHeap(int n[], int heapSize){
        for(int i = heapSize/2 - 1; i >= 0; --i){
            maxHeap(n, heapSize, i);
        }
    }

    public void maxHeap(int nums[], int heapSize, int i){
        int l = i * 2 + 1, r = i * 2 + 2, largest = i;
        if(l < heapSize && nums[l] > nums[largest]){
            largest = l;
        }
        if(r < heapSize && nums[r] > nums[largest]){
            largest = r;
        }
        if(largest != i){
            swap(nums, i ,largest);
            maxHeap(nums, heapSize, largest);
        }
    }

    public void swap(int []nums,int i ,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

