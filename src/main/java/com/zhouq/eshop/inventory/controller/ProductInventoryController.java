package com.zhouq.eshop.inventory.controller;

import com.zhouq.eshop.inventory.model.ProductInventory;
import com.zhouq.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.zhouq.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.zhouq.eshop.inventory.request.Request;
import com.zhouq.eshop.inventory.service.ProductInventoryService;
import com.zhouq.eshop.inventory.service.RequertAsyncProcessService;
import com.zhouq.eshop.inventory.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品库存 控制器
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/6 0:18
 */
@RestController
public class ProductInventoryController {


    @Autowired
    private RequertAsyncProcessService requertAsyncProcessService;

    @Autowired
    private ProductInventoryService productInventoryService;

    /**
     * 更新商品库存
     *
     * @param productInventory
     * @return
     */
    @PutMapping("/updateProductInventory")
    public Response updateProductInventory(ProductInventory productInventory) {
        try {
            Request request = new ProductInventoryDBUpdateRequest(productInventory, productInventoryService);
            requertAsyncProcessService.process(request);
            return new Response(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response(Response.FAILURE);
    }

    /**
     * 获取商品库存
     *
     * @param productId
     * @return
     */
    @PutMapping("/getProductInventory")
    public ProductInventory getProductInventory(Integer productId) {

        ProductInventory productInventory = null;

        try {
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService,false);
            requertAsyncProcessService.process(request);
            //将请求扔给service 异步去处理以后，就需要while(true) 一会儿，在这里等待
            //尝试等待前面商品库存的更新操作,同时缓存刷新的操作.将最新的数据刷新到缓存中,
            long startTime = System.currentTimeMillis();
            long waitTime = 0L;
            long endTime = 0L;
            //等待时间操作操过 200 ms
            while (true){
                if (waitTime > 200){
                    break;
                }
                //尝试从redis 中读取商品的缓存数据
                productInventory = productInventoryService.getProductInventoryCache(productId);
                if (productInventory != null){
                    return productInventory;
                }
                //如果没有读取到结果
                else {
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }

            //尝试 从数据库中读取
            productInventory = productInventoryService.findProductInventory(productId);
            if (productInventory != null){

                //刷新缓存
                // 这个过程中,实际上还是一个读取操作的过程,但是没有放到队列中去串行处理,还是会存在不一致问题.
                // 当代码执行到这里,存在三种情况
                // 1.上一次是读请求,数据刷入到redis 中,但是redis LRU 算法 给清空掉了,标志位还是false
                // 所以此时 下一个读请求 在缓存中拿不到数据,再放一个request 队列进去,让数据刷新一下.
                // 2.可能在200 ms 内,读请求在队列中一直积压,没有等待到它执行.
                // 所以就直接查一次库,然后给队列塞进去一盒刷新缓存的请求.
                // 3.数据库本身就没有,存在缓存穿透.请求到达 mysql

                //构造一个强制刷新缓存的请求
                request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService,true);
                requertAsyncProcessService.process(request);

                return productInventory;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //表示为未查询到数据
        return new ProductInventory(productId,-1L);
    }

}
