package com.zhouq.eshop.inventory.thread;

import com.zhouq.eshop.inventory.request.Request;
import com.zhouq.eshop.inventory.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求处理线程池：单例
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/11/30 0:21
 */
public class RequestProcessorThreadPool {

    /**
     * TODO
     * 在实际项目中，你设置线程池大小是多少，每个线程监控的那个内存队列的大小是多少
     * 都可以做到一个外部的配置文件中
     */
    /**
     * 线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public RequestProcessorThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();
        //初始化10 个内存队列
        for (int i = 0; i < 10; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(100);
            requestQueue.addQueue(queue);
            //每个队列绑定一个线程
            threadPool.submit(new RequestProcessorThread(queue));
        }

    }

    /**
     * 使用静态内部类实现 单例
     */
    private static class Singleton {

        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }
    }

    /**
     * jvm的机制去保证多线程并发安全
     * 内部类的初始化，一定只会发生一次，不管多少个线程并发去初始化
     *
     * @return
     */
    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化的便捷方法
     */
    public static void init() {
        getInstance();
    }
}
