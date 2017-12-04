package com.zhouq.eshop.inventory.request;

import com.zhouq.eshop.inventory.model.ProductInventory;
import com.zhouq.eshop.inventory.service.ProductInventoryService;

/**
 * 商品库存缓存刷新 request
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/4 23:53
 */
public class ProductInventoryCacheRefreshRequest implements Request {

    /**
     * 商品库存
     */
    private Integer  productId;

    /**
     * 商品库存 service
     */
    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheRefreshRequest() {

    }

    public ProductInventoryCacheRefreshRequest( Integer productId,ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        //从数据库中查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);

        // 讲最新的商品库存数量，刷新到redis 缓存中去
        productInventoryService.setProductInventroyCache(productInventory);

    }
}
