package example.ThreadPool;

import ch.qos.logback.core.html.NOPThrowableRenderer;

import java.util.concurrent.Callable;

/**
 * 功能：交替卖票 wait和 notify实现
 * 作者：yml
 * 日期：2025-01-1616:57
 */

public class ThreadClass {
    private int nums = 100;
    //多个线程的话，可以使用int类型进行判断
    private int currentThread = 0;

    public synchronized void sell(int isThread1){
        while (isThread1 != currentThread){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (nums>0){
            System.out.println(Thread.currentThread().getName()+"卖出了第"+nums+"张票");
            nums--;
        }else {
            System.out.println("票卖完了");
            return;
        }
        currentThread = (currentThread+1) % 3;
        notifyAll();
    }

    public static void main(String[] args) {
        ThreadClass threadClass = new ThreadClass();
        Thread thread1 = new Thread(()->{
            for (int i = 0; i < 50; i++) {
                threadClass.sell(0);
            }
        },"售票窗口1");

        Thread thread2 = new Thread(()->{
            for (int i = 0; i < 50; i++) {
                threadClass.sell(1);
            }
        },"售票窗口2");

        Thread thread3 = new Thread(()->{
            for (int i = 0; i < 50; i++) {
                threadClass.sell(2);
            }
        },"售票窗口3");

        thread1.start();
        thread2.start();
        thread3.start();
    }



}
