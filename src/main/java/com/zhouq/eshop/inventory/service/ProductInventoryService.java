package com.zhouq.eshop.inventory.service;

import com.zhouq.eshop.inventory.model.ProductInventory;

/**
 * 商品库存 service 接口
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/4 23:23
 */
public interface ProductInventoryService {

    /**
     * 更新商品库存
     * @param productInventory 商品库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 移除 商品库存缓存
     * @param productInventory 商品库存
     */
    void removeProductInventoryCache(ProductInventory productInventory);

    /**
     * 获取商品库存
     * @param productId 商品ID
     * @return
     */
    ProductInventory findProductInventory(Integer productId);

    /**
     * 设置商品缓存
     * @param productInventory
     */
    void setProductInventroyCache(ProductInventory productInventory);
}
