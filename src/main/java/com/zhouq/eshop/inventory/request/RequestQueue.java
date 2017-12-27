package com.zhouq.eshop.inventory.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * todo
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/11/30 23:50
 */
public class RequestQueue {

    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<ArrayBlockingQueue<Request>>();

    private Map<Integer,Boolean> flagMap = new HashMap<>();

    private static class Singleton{
        private static RequestQueue instance;
        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance(){
            return instance;
        }
    }

    public static RequestQueue getInstance(){
        return Singleton.getInstance();
    }

    public void addQueue(ArrayBlockingQueue<Request> queue){
        queues.add(queue);
    }

    /**
     * 获取内存队列数量
     * @return
     */
    public int queueSize(){
        return queues.size();
    }

    /**
     * 获取内存队列
     * @param index
     * @return
     */
    public ArrayBlockingQueue<Request> getQueue(int index){
        return queues.get(index);
    }

    public Map<Integer, Boolean> getFlagMap() {
        return flagMap;
    }

    public void setFlagMap(Map<Integer, Boolean> flagMap) {
        this.flagMap = flagMap;
    }
}
