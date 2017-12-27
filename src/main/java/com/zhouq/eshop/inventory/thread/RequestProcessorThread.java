package com.zhouq.eshop.inventory.thread;

import com.zhouq.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.zhouq.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.zhouq.eshop.inventory.request.Request;
import com.zhouq.eshop.inventory.request.RequestQueue;

import java.util.Map;
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
        try {
            while (true){
                //ArrayBlockingQueue
                // Blocking 如果队列满了，获取是空了 那么都会在执行操作的时候，阻塞住
                Request request = queue.take();
                //是否强制刷新
                boolean forceRefresh = request.isForceRefresh();

                //先做读请求的去重
                if (!forceRefresh){
                    RequestQueue requestQueue = RequestQueue.getInstance();
                    Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();
                    if (request instanceof ProductInventoryDBUpdateRequest){
                        //如果是一个更新数据库的请求,那么久将那个productId 对应的表示设置为 true
                        flagMap.put(request.getProductId(),true);
                    }else if (request instanceof ProductInventoryCacheRefreshRequest){
                        //如果是缓存刷新请求,那么就判断如果表示不为空 而且是true 就说明前面已经有一个次商品id  的数据库更新请求.
                        Boolean flag = flagMap.get(request.getProductId());
                        if (flag != null && flag){
                            flagMap.put(request.getProductId(),false);
                        }

                        //如果是刷新缓存,而且表示不为空,但是标识是false
                        //说明前面已经有一个数据更新 + 一个缓存刷新请求了.
                        if (flag != null && !flag){
                            //对应这种请求,就直接过滤,不需要再放到内存队列里面去
                            return true;
                        }

                    }
                }

                System.out.println("=========日志=========工作线程处理请求,商品id="+request.getProductId());
                // 执行request 操作
                request.process();

                // 假如说，执行完了一个读请求之后，假设数据已经刷新到redis中了
                // 但是后面可能redis中的数据会因为内存满了，被自动清理掉
                // 如果说数据从redis中被自动清理掉了以后
                // 然后后面又来一个读请求，此时如果进来，发现标志位是false，就不会去执行这个刷新的操作了
                // 所以在执行完这个读请求之后，实际上这个标志位是停留在false的
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
