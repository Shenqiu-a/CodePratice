package example.LeetCode.HistoricalCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/4/1613:52
 */

public class Er_Cha_Shu {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length == 0 || postorder == null) {
            return null;
        }
        //根节点
        TreeNode root = new TreeNode(postorder[postorder.length - 1]);
        //栈
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        stack.push(root);
        //中序遍历的索引
        int inorderIndex = inorder.length - 1;
        //遍历后续
        for (int i = postorder.length - 2; i >= 0; --i) {
            //拿到后序的值
            int postorderVal = postorder[i];
            //建立联系，拿出需要建立联系的节点
            TreeNode node = stack.peek();
            //与中序对比
            //不同则为右子树，因为是从后向前
            if (node.val != inorder[inorderIndex]) {
                node.right = new TreeNode(postorderVal);
                stack.push(node.right);
            } else {
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex--;
                }
                node.left = new TreeNode(postorderVal);
                stack.push(node.left);
            }
        }
        return root;
    }

    public TreeNode preBuildTree(int[] preorder, int[] inorder) {
        if(preorder == null || inorder.length == 0){
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        stack.push(root);
        int inorderIndex = 0;
        for(int i = 1; i <= preorder.length - 1; ++i){
            TreeNode node = stack.peek();
            int preOrderVal = preorder[i];
            if (node.val != inorder[inorderIndex]){
                node.left = new TreeNode(preOrderVal);
                stack.push(node.left);
            } else {
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]){
                    node = stack.pop();
                    inorderIndex++;
                }
                node.right = new TreeNode(preOrderVal);
                stack.push(node.right);
            }
        }


        return root;
    }
}
