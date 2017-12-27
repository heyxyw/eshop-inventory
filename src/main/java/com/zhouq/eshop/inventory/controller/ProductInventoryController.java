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
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService);
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
                return productInventory;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //表示为未查询到数据
        return new ProductInventory(productId,-1L);
    }

}
