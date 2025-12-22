package example.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 功能：
 * 作者：yml
 * 日期：2025-01-1016:05
 */

public class ThreadPool {
    public static void main(String[] args) {
        //都是通过Executors 工厂方法创建线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.shutdown();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.shutdown();
    }
}
