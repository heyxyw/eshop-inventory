package com.zhouq.eshop.inventory.thread;

import com.zhouq.eshop.inventory.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * todo
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
            break;
        }

        return null;
    }
}
