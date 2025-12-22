package example;

import java.util.LinkedList;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/3/214:33
 */

public class ClassTest {
    private final int capacity;
    private final LinkedList<String> list;

    public ClassTest(int capacity) {
        this.capacity = capacity;
        this.list = new LinkedList<>();

    }

    public void put(String str){

        if (list.size() >= capacity){
            list.removeLast();
        }else if (list.contains(str)){
            list.remove(str);
        }
        list.addLast(str);
    }

    public void get(String str){
        if(!list.contains(str)){
            System.out.println("没有这个元素");
            return;
        }
        list.remove(str);
        list.addFirst(str);
    }

    public void print(){
        System.out.println(list);
    }


    /**
     * 正则表达式匹配
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[][] f = new boolean[m + 1][n + 1];
        f[0][0] = true;
        for (int i = 0; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (p.charAt(j - 1) == '*') {
                    f[i][j] = f[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        f[i][j] = f[i][j] || f[i - 1][j];
                    }
                } else {
                    if (matches(s, p, i, j)) {
                        f[i][j] = f[i - 1][j - 1];
                    }
                }
            }
        }
        return f[m][n];
    }

    public boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    public static void main(String[] args) {
        String a = "aa";
        String b = "a*";
        ClassTest classTest = new ClassTest(3);
        classTest.isMatch(a,b);
    }

    /**
     * 单例
     */
    private static ClassTest instance;
    private void ClassTest(){

    }

    public static ClassTest getInstance(){
        if(instance==null){
            synchronized (ClassTest.class){
                if(instance==null){
                    instance = new ClassTest(3);
                }
            }
        }
        return instance;
    }



}
