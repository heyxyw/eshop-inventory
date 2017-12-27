package com.zhouq.eshop.inventory.request;

import com.zhouq.eshop.inventory.model.ProductInventory;
import com.zhouq.eshop.inventory.service.ProductInventoryService;

/**
 * 数据更新请求
 *
 * cache aside pattern
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/1 0:01
 */
public class ProductInventoryDBUpdateRequest implements Request{

    /**
     * 商品库存
     */
    private ProductInventory productInventory;

    /**
     * 商品库存 service
     */
    private ProductInventoryService productInventoryService;

    public ProductInventoryDBUpdateRequest() {

    }

    public ProductInventoryDBUpdateRequest(ProductInventory inventoryCnt, ProductInventoryService productInventoryService) {
        this.productInventory = inventoryCnt;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        //删除 redis 缓存
        productInventoryService.removeProductInventoryCache(productInventory);
        // 修改数据库数据
        productInventoryService.updateProductInventory(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }
}
