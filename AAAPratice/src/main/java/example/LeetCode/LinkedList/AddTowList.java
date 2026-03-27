package example.LeetCode.LinkedList;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 * 作者：yml
 * 日期：2026/3/2715:06
 */

@Slf4j
public class AddTowList {
    static class myListNode {
        int val;
        myListNode next;
        myListNode() {}
        myListNode(int val) {
            this.val = val;
        }
        myListNode(int val, myListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        myListNode a = new myListNode(2);
        myListNode b = new myListNode(3);
        myListNode l1 = new myListNode(4);
        b.next = a;
        l1.next = b;
        myListNode c = new myListNode(5);
        myListNode l2 = new myListNode(6);
        l2.next = c;
        myListNode result = addTwoList(l1, l2);
        while (result != null) {
            log.info("{}",result.val);
            result = result.next;
        }
    }

    private static myListNode addTwoList(myListNode l1, myListNode l2) {
        myListNode result = new myListNode(0);
        myListNode pre = result;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = carry + x + y;

            carry = sum / 10;
            sum = sum % 10;
            result.next = new myListNode(sum);
            result = result.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            result.next = new myListNode(carry);
        }
        return pre.next;
    }
}
