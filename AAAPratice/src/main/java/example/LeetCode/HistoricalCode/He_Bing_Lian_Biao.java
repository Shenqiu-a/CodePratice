package example.LeetCode.HistoricalCode;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/4/1415:05
 */


class He_Bing_Lian_Biao {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode merge(ListNode l1, ListNode l2){
        if(l1 == null){
            return l2;
        }else if (l2 == null){
            return l1;
        }else if (l1.val < l2.val){
            l1.next = merge(l1.next,l2);
            return l1;
        }else {
            l2.next = merge(l1,l2.next);
            return l2;
        }
    }

}
