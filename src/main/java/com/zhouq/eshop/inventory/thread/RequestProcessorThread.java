package com.zhouq.eshop.inventory.thread;

import com.zhouq.eshop.inventory.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 执行请求的工作线程
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/11/30 23:40
 */
public class RequestProcessorThread implements Callable<Boolean> {

    /**
     * 自己监控的内存队列
     */
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue){
        this.queue = queue;
    }

    public RequestProcessorThread(){

    }

    @Override
    public Boolean call() throws Exception {
        while (true){
            //ArrayBlockingQueue
            // Blocking 如果队列满了，获取是空了 那么都会在执行操作的时候，阻塞住
            Request request = queue.take();
            // 执行request 操作
            request.process();
            break;
        }
        return null;
    }
}
