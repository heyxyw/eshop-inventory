package com.zhouq.eshop.inventory.service.impl;

import com.zhouq.eshop.inventory.request.Request;
import com.zhouq.eshop.inventory.request.RequestQueue;
import com.zhouq.eshop.inventory.service.RequertAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求异步处理的service 实现
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/5 23:48
 */
@Service
public class RequertAsyncProcessServiceImpl implements RequertAsyncProcessService {
    @Override
    public void process(Request request) {
        try {
            // 做请求的路由 ，根据每个请求的商品ID 路由到对呀的队列中去
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getProductId());
            //将请求放入对应的 队列中 完成路由操作
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取路由到的内存队列
     * @param productId 商品ID
     * @return 内存队列
     */
    private ArrayBlockingQueue<Request> getRoutingQueue(Integer productId){

        RequestQueue requestQueue = RequestQueue.getInstance();

        //获取productId 的hash 值
        String key = String.valueOf(productId);
        int h ;
        int hash = (key == null) ? 0: (h = key.hashCode()) ^ (h >>> 16);

        //对hash 值进行取模，将hash 值路由到指定的 内存队列中去
        int index = (requestQueue.queueSize() - 1) & hash;

        System.out.println("=============日志===========: 路由内存队列,商品id="+productId+",队列索引="+index);

        return requestQueue.getQueue(index);
    }
}
